package top.plutomc.plutocore

import me.clip.placeholderapi.PlaceholderAPI
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask
import top.plutomc.plutocore.utils.MessageUtil
import java.io.File
import java.util.*
import java.util.logging.Level

abstract class Module(name: String) {

    companion object {
        fun hardUnload(module: Module) {
            module.unregisterAllEvents()
            module.unregisterAllTasks()
            module.unregisterAllCommands()
            module.unload()
        }

        fun hardReload(module: Module) {
            hardUnload(module)
            module.load()
        }
    }

    var main: JavaPlugin
        private set
    var name: String = name
        private set
    var dataFolder: File
        private set
    var localeFolder: File
        private set
    var locales: MutableMap<String, FileConfiguration>
        private set
    var configFile: File
        private set
    var config: FileConfiguration
        private set
    var debuggers: MutableSet<UUID>
        private set
    var listeners: MutableMap<String, Listener>
        private set
    var tasks: MutableMap<String, BukkitTask>
        private set
    var commands: MutableMap<String, CommandExecutor>
        private set

    init {
        this.main = CorePlugin.instance
        this.listeners = HashMap()
        this.tasks = HashMap()
        this.commands = HashMap()
        this.debuggers = HashSet()
        this.dataFolder = File(main.dataFolder, "${File.separator}$name${File.separator}")
        if (dataFolder.exists().not()) dataFolder.mkdirs()
        this.localeFolder = File(dataFolder, "${File.separator}locales${File.separator}")
        if (localeFolder.exists().not()) localeFolder.mkdirs()
        this.locales = HashMap()
        this.configFile = File(dataFolder, "config.yml")
        if (configFile.exists().not()) configFile.createNewFile()
        config = YamlConfiguration.loadConfiguration(configFile)
    }

    abstract fun load()

    abstract fun unload()

    fun registerEvents(name: String, listener: Listener) {
        main.server.pluginManager.registerEvents(listener, main)
        listeners[name] = listener
    }

    fun unregisterEvents(name: String) {
        val listener = listeners[name]
        if (listener != null) {
            HandlerList.unregisterAll(listener)
            listeners.remove(name)
        }
    }

    fun unregisterAllEvents() {
        val waitingToRemove = HashSet<String>()
        listeners.keys.forEach {
            val listener = listeners[name]
            if (listener != null) {
                HandlerList.unregisterAll(listener)
                waitingToRemove.add(it)
            }
        }
        for (s in waitingToRemove) {
            listeners.remove(s)
        }
    }

    fun registerTask(name: String, task: BukkitTask) {
        tasks[name] = task
    }

    fun unregisterTask(name: String) {
        val task = tasks[name]
        if (task != null) {
            main.server.scheduler.cancelTask(tasks[name]!!.taskId)
            tasks.remove(name)
        }
    }

    fun unregisterAllTasks() {
        val waitingToRemove = HashSet<String>()
        tasks.keys.forEach {
            val task = tasks[name]
            if (task != null) {
                main.server.scheduler.cancelTask(tasks[name]!!.taskId)
                waitingToRemove.add(it)
            }
        }
        for (s in waitingToRemove) {
            tasks.remove(s)
        }
    }

    fun registerCommand(name: String, command: CommandExecutor) {
        main.server.getPluginCommand(name)!!.setExecutor(command)
        if (command is TabExecutor) {
            main.server.getPluginCommand(name)!!.tabCompleter = command
        }
        commands[name] = command
    }

    fun unregisterCommand(name: String) {
        val command = commands[name]
        if (command != null) {
            main.server.getPluginCommand(name)!!.setExecutor(null)
            commands.remove(name)
        }
    }

    fun unregisterAllCommands() {
        val waitingToRemove = HashSet<String>()
        commands.keys.forEach {
            val command = commands[name]
            if (command != null) {
                main.server.getPluginCommand(name)!!.setExecutor(null)
                waitingToRemove.add(it)
            }
        }
        for (s in waitingToRemove) {
            commands.remove(s)
        }
    }

    fun saveConfig() = config.save(configFile)

    fun debug(msg: String) = info("[DEBUG] $msg")

    fun debugWithDebuggers(msg: String) {
        debug(msg)
        debuggers.forEach {
            val player = main.server.getPlayer(it)
            if (player != null) {
                MessageUtil.send(player, "<gray>[$name] [DEBUG] $msg</gray>")
            }
        }
    }

    fun info(msg: String) = main.logger.info("[$name] $msg")

    fun warn(msg: String, throwable: Throwable? = null) =
        if (throwable == null) main.logger.log(Level.WARNING, "[$name] $msg")
        else main.logger.log(Level.WARNING, "[$name] $msg", throwable)

    fun severe(msg: String, throwable: Throwable? = null) =
        if (throwable == null) main.logger.log(Level.SEVERE, "[$name] $msg")
        else main.logger.log(Level.SEVERE, "[$name] $msg", throwable)

    fun addDebugger(player: Player?) {
        if (player != null) {
            debuggers.add(player.uniqueId)
        }
    }

    fun getLocaleFile(lang: String): File {
        if (File(localeFolder, "$lang.yml").exists().not()) createLocaleFile(lang)
        return File(localeFolder, "$lang.yml")
    }

    fun getLocaleConfig(lang: String): FileConfiguration {
        return if (locales.containsKey(lang).not()) {
            val locale: FileConfiguration
            locale = YamlConfiguration.loadConfiguration(getLocaleFile(lang))
            locales[lang] = locale
            locale
        } else {
            val locale1 = locales[lang]
            locale1!!
        }
    }

    fun createLocaleFile(lang: String) = File(localeFolder, "$lang.yml").createNewFile()

    fun addLocaleContent(lang: String, key: String, vararg content: String) {
        val locale = getLocaleConfig(lang)
        if (!locale.contains(key)) {
            if (content.size > 1) {
                locale.set(key, arrayListOf(*content))
            } else {
                locale.set(key, content[0])
            }
        } else {
            if (content.size > 1 && locale.get(key) !is List<*>) {
                locale.set(key, arrayListOf(*content)); return
            }
            if (content.size == 1 && locale.get(key) !is String) {
                locale.set(key, arrayListOf(content[0])); return
            }
        }
    }

    fun saveLocaleContent(lang: String) {
        getLocaleConfig(lang).save(getLocaleFile(lang))
    }

    fun getLocaleContent(lang: String, key: String): Any? {
        return if (getLocaleFile(lang).exists() && getLocaleConfig(lang).get(key) != null) {
            getLocaleConfig(lang).get(key)
        } else {
            getLocaleConfig("zh_cn").get(key)
        }
    }

    fun getLocaleContentAsString(lang: String, key: String): String {
        val content = getLocaleContent(lang, key)
        return if (content is String) {
            content
        } else {
            content.toString()
        }
    }

    fun getLocaleContentAsList(lang: String, key: String): MutableList<*> {
        val content = getLocaleContent(lang, key)
        return if (content is List<*>) {
            content.toMutableList()
        } else {
            mutableListOf(content.toString())
        }
    }

    fun locale(target: CommandSender, key: String, vararg tagResolver: TagResolver) {
        val lang: String = if (target is Player) {
            target.locale
        } else {
            "zh_cn"
        }
        val localeContent = getLocaleContent(lang, key)
        if (localeContent != null) {
            when (localeContent) {
                is String -> {
                    MessageUtil.send(target, localeContent, *tagResolver)
                }

                is List<*> -> {
                    localeContent.forEach {
                        val content: String = if (it is String) {
                            it
                        } else {
                            it.toString()
                        }
                        MessageUtil.send(target, content, *tagResolver)
                    }
                }

                else -> {
                    val content = localeContent.toString()
                    MessageUtil.send(target, content, *tagResolver)
                }
            }
        }
    }

    fun locale(target: CommandSender, placeholderTarget: Player, key: String, vararg tagResolver: TagResolver) {
        val lang: String = if (target is Player) {
            target.locale
        } else {
            "zh_cn"
        }
        val localeContent = getLocaleContent(lang, key)
        if (localeContent != null) {
            when (localeContent) {
                is String -> {
                    MessageUtil.send(
                        target,
                        PlaceholderAPI.setPlaceholders(placeholderTarget, localeContent),
                        *tagResolver
                    )
                }

                is MutableList<*> -> {
                    localeContent.forEach {
                        val content = if (it is String) {
                            it
                        } else {
                            it.toString()
                        }
                        MessageUtil.send(
                            target,
                            PlaceholderAPI.setPlaceholders(placeholderTarget, content),
                            *tagResolver
                        )
                    }
                }

                else -> {
                    val content = localeContent.toString()
                    MessageUtil.send(target, PlaceholderAPI.setPlaceholders(placeholderTarget, content), *tagResolver)
                }
            }
        }
    }

    fun localeBroadcast(key: String, vararg tagResolver: TagResolver) {
        main.server.onlinePlayers.forEach {
            locale(it, key, *tagResolver)
        }
        locale(main.server.consoleSender, key, *tagResolver)
    }

    fun localeBroadcast(key: String, placeholderTarget: Player, vararg tagResolver: TagResolver) {
        main.server.onlinePlayers.forEach {
            locale(it, placeholderTarget, key, *tagResolver)
        }
        locale(main.server.consoleSender, placeholderTarget, key, *tagResolver)
    }
}