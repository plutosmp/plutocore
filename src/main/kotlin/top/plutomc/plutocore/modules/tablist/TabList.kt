package top.plutomc.plutocore.modules.tablist

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.scheduler.BukkitRunnable
import top.plutomc.plutocore.CorePlugin
import top.plutomc.plutocore.Module

class TabList : Module("tabList") {
    override fun load() {
        // zh_cn
        addLocaleContent(
            "zh_cn",
            "header",
            "<newline><yellow>PlutoMC <gray>| <white>生存服务器。<newline><aqua>感谢你的游玩。<newline>"
        )
        addLocaleContent("zh_cn", "footer", "<newline><gray>官网（建设中）<white>plutomc.top<newline>")
        saveLocaleContent("zh_cn")

        // zh_tw
        addLocaleContent(
            "zh_tw",
            "header",
            "<newline><yellow>PlutoMC <gray>| <white>生存伺服器。 <newline><aqua>感謝你的遊玩。 <newline>"
        )
        addLocaleContent("zh_tw", "footer", "<newline><gray>官網（建設中）<white>plutomc.top<newline>")
        saveLocaleContent("zh_tw")

        // zh_hk
        addLocaleContent(
            "zh_hk",
            "header",
            "<newline><yellow>PlutoMC <gray>| <white>生存伺服器。 <newline><aqua>感謝你的遊玩。 <newline>"
        )
        addLocaleContent("zh_hk", "footer", "<newline><gray>官網（建設中）<white>plutomc.top<newline>")
        saveLocaleContent("zh_hk")

        // en_us
        addLocaleContent(
            "en_us",
            "header",
            "<newline><yellow>PlutoMC <gray>| <white>SMP server. <newline><aqua>Thank you for playing. <newline>"
        )
        addLocaleContent("en_us", "footer", "<newline><gray>Website (WIP) <white>plutomc.top<newline>")
        saveLocaleContent("en_us")

        registerTask("tabListHeader", object : BukkitRunnable() {
            override fun run() {
                main.server.onlinePlayers.forEach {
                    CorePlugin.bukkitAudiences.player(it)
                        .sendPlayerListHeader(
                            MiniMessage.miniMessage().deserialize(getLocaleContentAsString(it.locale, "header"))
                        )
                }
            }
        }.runTaskTimerAsynchronously(main, 0L, 20L))

        registerTask("tabListFooter", object : BukkitRunnable() {
            override fun run() {
                main.server.onlinePlayers.forEach {
                    CorePlugin.bukkitAudiences.player(it)
                        .sendPlayerListFooter(
                            MiniMessage.miniMessage().deserialize(getLocaleContentAsString(it.locale, "footer"))
                        )
                }
            }
        }.runTaskTimerAsynchronously(main, 0L, 20L))
    }

    override fun unload() {
    }
}