package top.plutomc.plutocore

import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import top.plutomc.plutocore.utils.MessageUtil
import java.io.File
import java.util.*
import java.util.logging.Level

abstract class Module {

    val main = CorePlugin.instance
    var name: String
        private set
    var dataFolder: File
        private set
    var localeFolder: File
        private set
    var configFile: File
        private set
    var config: FileConfiguration
        private set
    var debuggers: MutableSet<UUID>
        private set

    constructor(name: String) {
        this.name = name
        debuggers = HashSet()
        this.dataFolder = File(CorePlugin.instance.dataFolder, "${File.separator}$name${File.separator}")
        this.localeFolder = File(dataFolder, "${File.separator}locale${File.separator}")
        this.configFile = File(dataFolder, "$name.yml")
        if (configFile.exists().not()) configFile.createNewFile()
        config = YamlConfiguration.loadConfiguration(configFile)
    }

    abstract fun load()

    abstract fun unload()

    abstract fun reload()

    fun saveConfig() = config.save(configFile)

    fun debug(msg: String) = info("[DEBUG] $msg")

    fun debugWithDebuggers(msg: String) {
        debug(msg)
        debuggers.forEach {
            val player = CorePlugin.instance.server.getPlayer(it)
            if (player != null) {
                MessageUtil.send(player, "<gray>[$name] [DEBUG] $msg</gray>")
            }
        }
    }

    fun info(msg: String) = CorePlugin.instance.logger.log(Level.FINE, "[$name] $msg")

    fun warn(msg: String, throwable: Throwable? = null) =
        if (throwable == null) CorePlugin.instance.logger.log(Level.WARNING, "[$name] $msg")
        else CorePlugin.instance.logger.log(Level.WARNING, "[$name] $msg", throwable)

    fun severe(msg: String, throwable: Throwable? = null) =
        if (throwable == null) CorePlugin.instance.logger.log(Level.SEVERE, "[$name] $msg")
        else CorePlugin.instance.logger.log(Level.SEVERE, "[$name] $msg", throwable)

    fun addDebugger(player: Player?) {
        if (player != null) {
            debuggers.add(player.uniqueId)
        }
    }

    fun getLocaleFile(lang: String) = File(localeFolder, "$lang.yml")

    fun getLocaleConfig(lang: String): FileConfiguration {
        if (getLocaleFile(lang).exists().not()) createLocaleFile(lang)
        return YamlConfiguration.loadConfiguration(getLocaleFile(lang))
    }

    fun createLocaleFile(lang: String) = getLocaleFile(lang).createNewFile()

    fun addLocaleContent(lang: String, key: String, vararg content: String) {
        val locale = getLocaleConfig(lang)
        if (!locale.contains(key)) {
            if (content.size > 1) {
                locale.addDefault(key, listOf(content))
            } else {
                locale.addDefault(key, content[0])
            }
        } else {
            if (content.size > 1 && locale.get(key) !is List<*>) {
                locale.set(key, listOf(content)); return
            }
            if (content.size == 1 && locale.get(key) !is String) {
                locale.set(key, listOf(content[0])); return
            }
        }
    }

    fun getLocaleContent(lang: String, key: String): Any? =
        if (getLocaleFile(lang).exists() && getLocaleConfig(lang).get(key) != null)
            getLocaleConfig(lang).get(key)
        else getLocaleConfig("zh_cn").get(key)


    fun locale(target: CommandSender, key: String, vararg tagResolver: TagResolver) {
        if (target is Player) {
            val player: Player = target
            val lang = player.locale
            val localeContent = getLocaleContent(lang, key)
            if (localeContent != null) {
                when (localeContent) {
                    is String -> {
                        MessageUtil.send(target, localeContent, *tagResolver)
                    }

                    is List<*> -> {
                        localeContent.forEach {
                            val content = it.toString()
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
    }
}