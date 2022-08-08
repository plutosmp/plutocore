package top.plutomc.plutocore.modules.motd

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerListPingEvent
import top.plutomc.plutocore.Module
import top.plutomc.plutocore.utils.TextUtil

class Motd : Module("motd"), Listener {
    override fun load() {
        if (!config.contains("line1")) {
            config.set("line1", "<yellow>PlutoMC <gray>| <white>生存服务器。")
            saveConfig()
        }

        if (!config.contains("line2")) {
            config.set("line2", "<green>正在 1.19 运行。")
            saveConfig()
        }

        registerEvents("motd", this)
    }

    override fun unload() {
    }

    @EventHandler
    fun serverPingEvent(event: ServerListPingEvent) {
        event.motd = TextUtil.toLegacy(
            MiniMessage.miniMessage().deserialize("${config.getString("line1")}<newline>${config.getString("line2")}")
        )
    }
}