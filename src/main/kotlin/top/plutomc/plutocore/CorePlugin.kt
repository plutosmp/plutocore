package top.plutomc.plutocore

import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.plugin.java.JavaPlugin
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
        instance = this;
        bukkitAudiences = BukkitAudiences.create(this)
        val file = File(dataFolder, "config.yml");
        if (file.exists().not()) saveDefaultConfig()
        logger.info("Enabling...")
        server.pluginManager.registerEvents(PlayerListener(), this)
        logger.info("Done.")
    }

    override fun onDisable() {
        logger.info("Disabling...")
        bukkitAudiences!!.close()
        logger.info("Done.")
    }
}