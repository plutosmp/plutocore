package top.plutomc.plutocore

import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.GameMode
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import top.plutomc.plutocore.commands.GameModeCommand
import top.plutomc.plutocore.commands.MainCommand
import top.plutomc.plutocore.listeners.PlayerListener
import top.plutomc.plutocore.utils.LocaleUtil
import top.plutomc.plutocore.utils.TabListUtil
import java.io.File
import java.util.*

class CorePlugin : JavaPlugin() {
    companion object {
        lateinit var instance: JavaPlugin
            private set
        lateinit var bukkitAudiences: BukkitAudiences
            private set
        lateinit var gameModeCache: MutableMap<UUID, GameMode>
            private set

        fun reloadPlugin() {
            instance.reloadConfig()
        }
    }

    private lateinit var tabListHeaderTask: BukkitTask
    private lateinit var tabListFooterTask: BukkitTask
    private lateinit var gameModeProtectTask: BukkitTask

    override fun onEnable() {
        logger.info("Enabling...")
        instance = this

        // init plugin cache system
        gameModeCache = HashMap()

        // init adventure bukkitAudiences
        bukkitAudiences = BukkitAudiences.create(this)

        // check if config file was created
        val file = File(dataFolder, "config.yml")
        if (file.exists().not()) saveDefaultConfig()

        // register listeners
        server.pluginManager.registerEvents(PlayerListener(), this)

        // register command - main command
        server.getPluginCommand("plutocore")?.setExecutor(MainCommand())
        server.getPluginCommand("plutocore")?.tabCompleter = MainCommand()

        //register command - gamemode command
        server.getPluginCommand("gm")?.setExecutor(GameModeCommand())
        server.getPluginCommand("gm")?.tabCompleter = GameModeCommand()

        // init tasks - TabList
        tabListHeaderTask = object : BukkitRunnable() {
            override fun run() {
                val s = config.getString("tablist.header")
                TabListUtil.updateHeader(s)
            }
        }.runTaskTimerAsynchronously(this, 0L, 20L)
        tabListFooterTask = object : BukkitRunnable() {
            override fun run() {
                val s = config.getString("tablist.footer")
                TabListUtil.updateFooter(s)
            }
        }.runTaskTimerAsynchronously(this, 0L, 20L)

        // init tasks - GameMode protect
        gameModeProtectTask = object : BukkitRunnable() {
            override fun run() {
                server.onlinePlayers.forEach {
                    if (gameModeCache.containsKey(it.uniqueId)) {
                        if ((gameModeCache[it.uniqueId] == it.gameMode).not()) {
                            it.gameMode = GameMode.SURVIVAL
                            gameModeCache[it.uniqueId] = GameMode.SURVIVAL
                            LocaleUtil.send(it, "gameModeWarn")
                        }
                    }
                }
            }
        }.runTaskTimer(this, 0L, 1L)

        // avoid issues cause by reloading
        server.onlinePlayers.forEach {
            gameModeCache[it.uniqueId] = GameMode.SURVIVAL
        }

        logger.info("Done.")
    }

    override fun onDisable() {
        logger.info("Disabling...")

        // close bukkitAudiences, to avoid unsafe disable
        bukkitAudiences.close()

        // close tasks
        server.scheduler.cancelTasks(this)

        logger.info("Done.")
    }
}