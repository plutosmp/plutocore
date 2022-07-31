package top.plutomc.plutocore

import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import top.plutomc.plutocore.commands.MainCommand
import top.plutomc.plutocore.listeners.PlayerListener
import top.plutomc.plutocore.utils.TabListUtil
import java.io.File

class CorePlugin : JavaPlugin() {
    companion object {
        lateinit var instance: JavaPlugin
            private set
        lateinit var bukkitAudiences: BukkitAudiences
            private set

        fun reloadPlugin() {
            instance.reloadConfig()
        }
    }

    private lateinit var tabListHeaderTask: BukkitTask
    private lateinit var tabListFooterTask: BukkitTask

    override fun onEnable() {
        logger.info("Enabling...")
        instance = this

        // init adventure bukkitAudiences
        bukkitAudiences = BukkitAudiences.create(this)

        // check if config file was created
        val file = File(dataFolder, "config.yml")
        if (file.exists().not()) saveDefaultConfig()

        // register listeners
        server.pluginManager.registerEvents(PlayerListener(), this)

        // register commands
        server.getPluginCommand("plutocore")?.setExecutor(MainCommand())
        server.getPluginCommand("plutocore")?.tabCompleter = MainCommand()

        // init tasks
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

        logger.info("Done.")
    }

    override fun onDisable() {
        logger.info("Disabling...")

        // close bukkitAudiences, to avoid unsafe disable
        bukkitAudiences.close()

        //close tasks
        server.scheduler.cancelTasks(this)

        logger.info("Done.")
    }
}