package top.plutomc.plutocore

import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import top.plutomc.plutocore.listeners.PlayerListener
import java.io.File

class CorePlugin : JavaPlugin(){
    companion object {
        private var instance : JavaPlugin? = null
        private var bukkitAudiences : BukkitAudiences? = null
        fun instance() = instance
        fun bukkitAudiences() = bukkitAudiences
    }

    override fun onEnable() {
        logger.info("Enabling...")
        instance = this
        bukkitAudiences = BukkitAudiences.create(this)
        val file = File(dataFolder, "config.yml")
        if (file.exists().not()) saveDefaultConfig()
        server.pluginManager.registerEvents(PlayerListener(), this)
        val tabListHeaderTask = object : BukkitRunnable(){
            override fun run() {
                val stringBuilder = StringBuilder()
                val list = config.getStringList("tablist.header")
                list.forEach { stringBuilder.append(it).append("<br>") }

            }
        }.runTaskTimerAsynchronously(this, 0L, 20L)
        logger.info("Done.")
    }

    override fun onDisable() {
        logger.info("Disabling...")
        bukkitAudiences!!.close()
        logger.info("Done.")
    }
}