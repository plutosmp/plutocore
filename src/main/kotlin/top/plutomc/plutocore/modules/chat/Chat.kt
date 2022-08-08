package top.plutomc.plutocore.modules.chat

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import top.plutomc.plutocore.Module
import top.plutomc.plutocore.modules.localecache.LocaleCache
import top.plutomc.plutocore.utils.LinkParser
import top.plutomc.plutocore.utils.MessageUtil

class Chat : Module("chat"), Listener {
    override fun load() {
        if (!config.contains("format")) {
            config.set("format", "<gradient:#a6c0fe:#f68084><player></gradient>: <white><message>")
            saveConfig()
        }

        // zh_cn
        addLocaleContent("zh_cn", "hover", "<aqua>点击打开！")
        saveLocaleContent("zh_cn")

        // zh_tw
        addLocaleContent("zh_tw", "hover", "<aqua>點擊打開！")
        saveLocaleContent("zh_tw")

        // zh_hk
        addLocaleContent("zh_hk", "hover", "<aqua>點擊打開！")
        saveLocaleContent("zh_hk")

        // en_us
        addLocaleContent("en_us", "hover", "<aqua>Click to open!")
        saveLocaleContent("en_us")

        // register listeners
        registerEvents("chat", this)
    }

    override fun unload() {
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun asyncPlayerChatEvent(event: AsyncPlayerChatEvent) {
        if (event.isCancelled.not()) {
            val chatFormat = config.getString("format")
            if (chatFormat != null) {
                main.server.onlinePlayers.forEach {
                    MessageUtil.send(
                        it,
                        chatFormat,
                        Placeholder.parsed("player", event.player.displayName),
                        Placeholder.parsed(
                            "message", LinkParser.parseUrl(
                                event.message,
                                getLocaleContentAsString(LocaleCache.getLocale(it.uniqueId), "hover")
                            )
                        )
                    )
                }
                MessageUtil.send(
                    main.server.consoleSender,
                    chatFormat,
                    Placeholder.parsed("player", event.player.displayName),
                    Placeholder.parsed(
                        "message", LinkParser.parseUrl(
                            event.message,
                            getLocaleContentAsString("zh_cn", "hover")
                        )
                    )
                )
                event.isCancelled = true
            }
        }
    }
}