package top.plutomc.plutocore

import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import top.plutomc.plutocore.framework.menu.MenuFramework
import top.plutomc.plutocore.modules.armormenu.ArmorMenu
import top.plutomc.plutocore.modules.chat.Chat
import top.plutomc.plutocore.modules.joinquitmessage.JoinQuitMessage
import top.plutomc.plutocore.modules.localecache.LocaleCache
import top.plutomc.plutocore.modules.motd.Motd
import top.plutomc.plutocore.modules.playerprofile.PlayerProfile
import top.plutomc.plutocore.modules.tablist.TabList
import top.plutomc.plutocore.modules.tickwarn.TickWarn
import java.io.File

class CorePlugin : JavaPlugin() {

    companion object {
        lateinit var instance: JavaPlugin
            private set
        lateinit var bukkitAudiences: BukkitAudiences
            private set
        lateinit var modules: HashMap<String, Module>
            private set

        fun reloadPlugin() {
            instance.reloadConfig()
            HandlerList.unregisterAll(instance)
            modules.keys.forEach {
                Module.hardReload(modules[it]!!)
            }
        }

        fun registerModule(module: Module) {
            modules[module.name] = module
        }

        fun unregisterModule(name: String) {
            modules[name]!!.info("Unloading ${modules[name]!!.name}...")
            try {
                Module.hardUnload(modules[name]!!)
                modules[name]!!.info("Unloaded ${modules[name]!!.name}.")
            } catch (e: Exception) {
                modules.remove(name)
                modules[name]!!.severe("Failed to unload module: ${modules[name]!!.name}.", e)
            }
            modules.remove(name)
        }

        fun unregisterAllModules() {
            val waitingToRemove = HashSet<String>()
            modules.keys.forEach {
                modules[it]!!.info("Unloading ${modules[it]!!.name}...")
                try {
                    Module.hardUnload(modules[it]!!)
                    modules[it]!!.info("Unloaded ${modules[it]!!.name}.")
                } catch (e: Exception) {
                    waitingToRemove.add(it)
                    modules[it]!!.severe("Failed to unload module: ${modules[it]!!.name}.", e)
                }
                waitingToRemove.add(it)
            }
            for (s in waitingToRemove) {
                modules.remove(s)
            }
        }
    }


    override fun onEnable() {
        logger.info("Enabling...")

        instance = this

        modules = HashMap()

        MenuFramework(this)

        bukkitAudiences = BukkitAudiences.create(this)

        val file = File(dataFolder, "config.yml")
        if (file.exists().not()) saveDefaultConfig()

        registerModule(PlayerProfile())
        registerModule(LocaleCache())
        registerModule(JoinQuitMessage())
        registerModule(TickWarn())
        registerModule(TabList())
        registerModule(Chat())
        registerModule(ArmorMenu())
        registerModule(Motd())

        modules.keys.forEach {
            modules[it]!!.info("Loading ${modules[it]!!.name}...")
            try {
                modules[it]!!.load()
                modules[it]!!.info("Loaded ${modules[it]!!.name}.")
            } catch (e: Exception) {
                modules[it]!!.severe("Failed to load module: ${modules[it]!!.name}.", e)
            }

        }

        server.getPluginCommand("plutocore")?.setExecutor(CoreCommand())
        server.getPluginCommand("plutocore")?.tabCompleter = CoreCommand()

        logger.info("Done.")
    }

    override fun onDisable() {
        logger.info("Disabling...")

        bukkitAudiences.close()

        server.scheduler.cancelTasks(this)

        unregisterAllModules()

        logger.info("Done.")
    }
}