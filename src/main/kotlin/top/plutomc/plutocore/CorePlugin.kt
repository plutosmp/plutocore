package top.plutomc.plutocore

import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import top.plutomc.plutocore.commands.MainCommand
import top.plutomc.plutocore.commands.TpsCommand
import top.plutomc.plutocore.framework.menu.MenuFramework
import top.plutomc.plutocore.listeners.PlayerListener
import top.plutomc.plutocore.utils.LocaleUtil
import top.plutomc.plutocore.utils.MessageUtil
import top.plutomc.plutocore.utils.NmsRefUtil
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

    override fun onEnable() {
        logger.info("Enabling...")
        // init instance
        instance = this

        // init menu framework
        MenuFramework(this)

        // init adventure bukkitAudiences
        bukkitAudiences = BukkitAudiences.create(this)

        // check if config file wasn't created
        val file = File(dataFolder, "config.yml")
        if (file.exists().not()) saveDefaultConfig()

        // register listeners
        server.pluginManager.registerEvents(PlayerListener(), this)

        // register commands
        // main command
        server.getPluginCommand("plutocore")?.setExecutor(MainCommand())
        server.getPluginCommand("plutocore")?.tabCompleter = MainCommand()

        // tps command
        server.getPluginCommand("tickpersecond")?.setExecutor(TpsCommand())

        config

        // init tasks
        // tablist
        object : BukkitRunnable() {
            override fun run() {
                val s = config.getString("tablist.header")
                TabListUtil.updateHeader(s)
            }
        }.runTaskTimerAsynchronously(this, 0L, 20L)
        object : BukkitRunnable() {
            override fun run() {
                val s = config.getString("tablist.footer")
                TabListUtil.updateFooter(s)
            }
        }.runTaskTimerAsynchronously(this, 0L, 20L)

        // tick monitor
        object : BukkitRunnable() {
            override fun run() {
                if (NmsRefUtil.getRecentTps() < 18) {
                    MessageUtil.broadcast(LocaleUtil.get("tpsWarn"))
                }
            }
        }.runTaskTimerAsynchronously(instance, 0L, 20L * 60L * 10L)

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