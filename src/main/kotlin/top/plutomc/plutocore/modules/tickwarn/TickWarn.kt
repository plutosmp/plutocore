package top.plutomc.plutocore.modules.tickwarn

import org.bukkit.scheduler.BukkitRunnable
import top.plutomc.plutocore.Module
import top.plutomc.plutocore.utils.NmsRefUtil

class TickWarn : Module("tickWarn") {
    override fun load() {
        // zh_cn
        addLocaleContent(
            "zh_cn",
            "warn",
            "<red>服务器每秒执行的刻数已经小于 18。<newline>请不要长时间呆在具有大量实体和开启卡服务器的机器的区块。<newline>谢谢配合。</red>"
        )
        saveLocaleContent("zh_cn")

        // zh_tw
        addLocaleContent(
            "zh_tw",
            "warn",
            "<red>伺服器每秒執行的刻數已經小於 18。<newline>请不要长时间呆在具有大量实体和开启卡伺服器的机器的区块。<newline>謝謝配合。</red>"
        )
        saveLocaleContent("zh_tw")

        // zh_hk
        addLocaleContent(
            "zh_hk",
            "warn",
            "<red>伺服器每秒執行的刻數已經小於 18。<newline>请不要长时间呆在具有大量实体和开启卡伺服器的机器的区块。<newline>謝謝配合。</red>"
        )
        saveLocaleContent("zh_hk")

        // en_us
        addLocaleContent(
            "en_us",
            "warn",
            "<red>The TPS of this server is lower than 18.<newline>Please don't stay in chunks with a tons of entities and machines that make server laggy for long.<newline>Thank you for your cooperation."
        )
        saveLocaleContent("en_us")

        registerTask("tickWarn", object : BukkitRunnable() {
            override fun run() {
                if (NmsRefUtil.getRecentTps() < 18) {
                    localeBroadcast("warn")
                }
            }
        }.runTaskTimerAsynchronously(main, 0L, 60L * 10L * 20L))
    }

    override fun unload() {
    }
}