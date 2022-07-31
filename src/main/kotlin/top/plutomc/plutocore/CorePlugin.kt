package top.plutomc.plutocore

import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import top.plutomc.plutocore.commands.MainCommand
import top.plutomc.plutocore.listeners.PlayerListener
import top.plutomc.plutocore.utils.TablistUtil
import java.io.File

class CorePlugin : JavaPlugin() {
    companion object {
        private var instance: JavaPlugin? = null
        private var bukkitAudiences: BukkitAudiences? = null
        fun instance() = instance
        fun bukkitAudiences() = bukkitAudiences
        fun reloadPlugin() {
            instance?.reloadConfig()
        }
    }

    private var tabListHeaderTask: BukkitTask? = null
    private var tabListFooterTask: BukkitTask? = null

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
        server.getPluginCommand("plutocore")?.setTabCompleter(MainCommand())

        // init tasks
        tabListHeaderTask = object : BukkitRunnable() {
            override fun run() {
                val stringBuilder = StringBuilder()
                val list = config.getStringList("tablist.header")
                list.forEach { stringBuilder.append(it).append("<br>") }
                TablistUtil.updateHeader(stringBuilder.toString())
            }
        }.runTaskTimerAsynchronously(this, 0L, 20L)
        tabListFooterTask = object : BukkitRunnable() {
            override fun run() {
                val stringBuilder = StringBuilder()
                val list = config.getStringList("tablist.header")
                list.forEach { stringBuilder.append(it).append("<br>") }
                TablistUtil.updateFooter(stringBuilder.toString())
            }
        }.runTaskTimerAsynchronously(this, 0L, 20L)

        logger.info("Done.")
    }

    override fun onDisable() {
        logger.info("Disabling...")

        // close bukkitAudiences, to avoid unsafe disable
        bukkitAudiences!!.close()

        //close tasks
        server.scheduler.cancelTasks(this)

        logger.info("Done.")
    }
}