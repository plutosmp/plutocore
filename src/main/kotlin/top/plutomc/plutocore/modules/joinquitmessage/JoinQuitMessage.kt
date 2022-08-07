package top.plutomc.plutocore.modules.joinquitmessage

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import top.plutomc.plutocore.Module

class JoinQuitMessage : Module("joinAndQuitMessage"), Listener {
    override fun load() {
        // zh_cn
        addLocaleContent("zh_cn", "join", "<gray>[<green>+<gray>] <white><player> <gray>加入了游戏。")
        addLocaleContent("zh_cn", "quit", "<gray>[<red>-<gray>] <white><player> <gray>退出了游戏。")
        addLocaleContent(
            "zh_cn", "display", "<gray>欢迎来到 <yellow>PlutoMC <gray>服务器！",
            "<gray>我们在 <u><aqua><hover:show_text:'<aqua>点击打开！'><click:open_url:'https://www.mcbbs.net/thread-1370761-1-1.html'>MCBBS</click></hover></aqua></u> <gray>和 <u><aqua><hover:show_text:'<aqua>点击打开！'><click:open_url:'https://play.mcmod.cn/sv20186605.html'>找服玩</click></hover></aqua></u> <gray>都有帖子哦！",
            "<gray>去这些地方给服务器一个评价或顶吧！",
            "<gray>我们已将优化非大陆地区的网络连接质量提上日程（开放专线）。"
        )
        saveLocaleContent("zh_cn")

        // zh_tw
        addLocaleContent("zh_tw", "join", "<gray>[<green>+<gray>] <white><player> <gray>加入了遊戲。")
        addLocaleContent("zh_tw", "quit", "<gray>[<green>-<gray>] <white><player> <gray>退出了遊戲。")
        addLocaleContent(
            "zh_tw", "display", "<gray>歡迎來到 <yellow>PlutoMC <gray>伺服器！",
            "<gray>我們在 <u><aqua><hover:show_text:'<aqua>點擊打開！'><click:open_url:'https://www.mcbbs.net/thread-1370761-1-1.html'>MCBBS</click></hover></aqua></u> <gray>和 <u><aqua><hover:show_text:'<aqua>點擊打開！'><click:open_url:'https://play.mcmod.cn/sv20186605.html'>找服玩</click></hover></aqua></u> <gray>都有帖子哦！",
            "<gray>去這些地方給伺服器一個評價或頂吧！",
            "<gray>我們已將優化非大陸地區的網路連接質量提上日程（開放專線）。"
        )
        saveLocaleContent("zh_tw")

        // zh_hk
        addLocaleContent("zh_hk", "join", "<gray>[<green>+<gray>] <white><player> <gray>加入了遊戲。")
        addLocaleContent("zh_hk", "quit", "<gray>[<green>-<gray>] <white><player> <gray>退出了遊戲。")
        addLocaleContent(
            "zh_hk", "display", "<gray>歡迎來到 <yellow>PlutoMC <gray>伺服器！",
            "<gray>我們在 <u><aqua><hover:show_text:'<aqua>點擊打開！'><click:open_url:'https://www.mcbbs.net/thread-1370761-1-1.html'>MCBBS</click></hover></aqua></u> <gray>和 <u><aqua><hover:show_text:'<aqua>點擊打開！'><click:open_url:'https://play.mcmod.cn/sv20186605.html'>找服玩</click></hover></aqua></u> <gray>都有帖子哦！",
            "<gray>去這些地方給伺服器一個評價或頂吧！",
            "<gray>我們已將優化非大陸地區的網絡連接質量提上日程（開放專線）。"
        )
        saveLocaleContent("zh_hk")

        // en_us
        addLocaleContent("en_us", "join", "<gray>[<green>+<gray>] <white><player> <gray>joined the game.")
        addLocaleContent("en_us", "quit", "<gray>[<green>-<gray>] <white><player> <gray>left the game.")
        addLocaleContent(
            "en_us", "display", "<gray>Welcome to <yellow>PlutoMC <gray>server!",
            "<gray>We have a post about this server on <u><aqua><hover:show_text:'<aqua>Click to open!'><click:open_url:'https://www.mcbbs.net/thread-1370761-1-1.html'>MCBBS</click></hover></aqua></u> <gray>and <u><aqua><hover:show_text:'<aqua>Click to open!'><click:open_url:'https://play.mcmod.cn/sv20186605.html'>Find a server to play</click></hover></aqua></u> <gray>.",
            "<gray>Go to give us a rate or smash a like!",
            "<gray>We have already planned to optimize network connection speed of areas outside China mainland (Open dedicated line)."
        )
        saveLocaleContent("en_us")

        // register listeners
        registerEvents("joinAndQuit", this)
    }

    override fun unload() {

    }

    @EventHandler
    fun playerJoinEvent(event: PlayerJoinEvent) {
        event.joinMessage = null
        localeBroadcast("join", event.player, Placeholder.parsed("player", event.player.name))
        locale(event.player, "display")
    }

    @EventHandler
    fun playerQuitEvent(event: PlayerQuitEvent) {
        event.quitMessage = null
        localeBroadcast("quit", event.player, Placeholder.parsed("player", event.player.name))
    }
}