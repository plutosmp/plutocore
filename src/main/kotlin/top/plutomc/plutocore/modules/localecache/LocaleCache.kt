package top.plutomc.plutocore.modules.localecache

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerLocaleChangeEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.scheduler.BukkitRunnable
import top.plutomc.plutocore.Module
import top.plutomc.plutocore.modules.playerprofile.PlayerProfile
import java.util.*

class LocaleCache : Module("localeCache"), Listener, TabExecutor {
    companion object {
        fun getLocale(uuid: UUID): String {
            if (PlayerProfile.getProfile(uuid).contains("localeCache.locale")) return PlayerProfile.getProfile(uuid)
                .getString("localeCache.locale")!!
            return "zh_cn"
        }

        fun setLocale(uuid: UUID, locale: String) {
            PlayerProfile.getProfile(uuid).set("localeCache.locale", locale)
        }
    }

    lateinit var checked: HashSet<UUID>

    override fun load() {
        // zh_cn
        addLocaleContent(
            "zh_cn", "localeSwitched",
            "<newline><gray>我们检测到你将游戏语言切换成了 <white><locale>。",
            "<gray>你可以选择使用 <white>/lang <locale> <gray>来将服务器语言切换为 <white><locale>。",
            "<gray>使用 <white>/langtip <gray>启用不再提示。<newline>"
        )
        addLocaleContent(
            "zh_cn",
            "tipsSwitchedOn",
            "<green>已打开提示。"
        )
        addLocaleContent(
            "zh_cn",
            "tipsSwitchedOff",
            "<red>已关闭提示。"
        )
        addLocaleContent(
            "zh_cn",
            "manualLocaleSwitched",
            "<gray>已经将你的语言切换为 <white><locale>。",
            "<gray>如果这个语言不受支持或不存在，那么你的语言将会是 <white>zh_cn。"

        )
        saveLocaleContent("zh_cn")

        // zh_tw
        addLocaleContent(
            "zh_tw", "localeSwitched",
            "<newline><gray>我們檢測到你將游戲語言切換成了 <white><locale>。",
            "<gray>你可以選擇使用 <white>/lang <locale> <gray>來將服務器語言切換為 <white><locale>。",
            "<gray>使用 <white>/langtip <gray>啟用不再提示。<newline>"
        )
        addLocaleContent(
            "zh_tw",
            "tipsSwitchedOn",
            "<green>已打開提示。"
        )
        addLocaleContent(
            "zh_tw",
            "tipsSwitchedOff",
            "<red>已關閉提示。"
        )
        addLocaleContent(
            "zh_tw",
            "manualLocaleSwitched",
            "<gray>已經將你的語言切換為 <white><locale>。",
            "<gray>如果這個語言不受支持或不存在，那麼你的語言將會是 <white>zh_cn。"

        )
        saveLocaleContent("zh_tw")

        // zh_hk
        addLocaleContent(
            "zh_hk", "localeSwitched",
            "<newline><gray>我們檢測到你將游戲語言切換成了 <white><locale>。",
            "<gray>你可以選擇使用 <white>/lang <locale> <gray>來將服務器語言切換為 <white><locale>。",
            "<gray>使用 <white>/langtip <gray>啟用不再提示。<newline>"
        )
        addLocaleContent(
            "zh_hk",
            "tipsSwitchedOn",
            "<green>已打開提示。"
        )
        addLocaleContent(
            "zh_hk",
            "tipsSwitchedOff",
            "<red>已關閉提示。"
        )
        addLocaleContent(
            "zh_hk",
            "manualLocaleSwitched",
            "<gray>已經將你的語言切換為 <white><locale>。",
            "<gray>如果這個語言不受支持或不存在，那麼你的語言將會是 <white>zh_cn。"

        )
        saveLocaleContent("zh_hk")

        // en_us
        addLocaleContent(
            "en_us", "localeSwitched",
            "<newline><gray>We have noticed that you switched your client language to <white><locale>.",
            "<gray>You can run <white>/lang <locale> <gray>to switch server language to <white><locale>。",
            "<gray>Run <white>/langtip <gray>to turn off prompting.<newline>"
        )
        addLocaleContent(
            "en_us",
            "tipsSwitchedOn",
            "<green>Prompt enabled."
        )
        addLocaleContent(
            "en_us",
            "tipsSwitchedOff",
            "<red>Prompt disabled."
        )
        addLocaleContent(
            "en_us",
            "manualLocaleSwitched",
            "<gray>You switched your server language to <white><locale>.",
            "<gray>If this language doesn't supported or exist, your server language will be <white>zh_cn."

        )
        saveLocaleContent("en_us")

        checked = HashSet()

        registerTask(
            "localeSwitch",
            object : BukkitRunnable() {
                override fun run() {
                    main.server.onlinePlayers.forEach {
                        doCheck(it)
                    }
                }
            }.runTaskTimerAsynchronously(main, 0L, 100L)
        )

        registerEvents("localeCache", this)

        registerCommand("langtip", this)
        registerCommand("lang", this)
    }

    private fun doCheck(player: Player) {
        if (checked.contains(player.uniqueId).not()) {
            if (PlayerProfile.getProfile(player.uniqueId).getBoolean("$name.doLocaleTips")) {
                if (player.locale != PlayerProfile.getProfile(player.uniqueId).getString("$name.locale")) {
                    locale(player, "localeSwitched", Placeholder.parsed("locale", player.locale))
                }
                checked.add(player.uniqueId)
            }
        }
    }

    private fun doTipsSwitch(player: Player) {
        val uuid = player.uniqueId
        val profile = PlayerProfile.getProfile(uuid)
        if (profile.getBoolean("$name.doLocaleTips")) {
            profile.set("$name.doLocaleTips", false)
            locale(player, "tipsSwitchedOff")
        } else {
            profile.set("$name.doLocaleTips", true)
            locale(player, "tipsSwitchedOn")
        }
        PlayerProfile.saveProfile(uuid)
    }

    override fun unload() {
    }

    @EventHandler
    fun playerJoinEvent(event: PlayerJoinEvent) {
        val uuid = event.player.uniqueId
        val profile = PlayerProfile.getProfile(uuid)
        if (profile.contains("$name.locale").not()) profile.set("$name.locale", "zh_cn")
        if (profile.contains("$name.doLocaleTips").not()) profile.set("$name.doLocaleTips", true)
        PlayerProfile.saveProfile(uuid)
    }

    @EventHandler
    fun playerLocaleChangeEvent(event: PlayerLocaleChangeEvent) {
        checked.remove(event.player.uniqueId)
    }

    @EventHandler
    fun playerQuitEvent(event: PlayerQuitEvent) {
        checked.remove(event.player.uniqueId)
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String> {
        if (label == "lang" || label == "langtip") return mutableListOf("")
        return mutableListOf("")
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (label == "langtip") {
            if (sender is Player) {
                doTipsSwitch(sender)
            }
        } else if (label == "lang") {
            if (sender is Player) {
                if (args!!.isNotEmpty()) {
                    setLocale(sender.uniqueId, args[0])
                    locale(sender, "manualLocaleSwitched", Placeholder.parsed("locale", args[0]))
                }
            }
        }
        return true
    }
}