package top.plutomc.plutocore.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import top.plutomc.plutocore.CorePlugin
import top.plutomc.plutocore.utils.MessageUtil

class PlayerListener : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        // modify join message
        val msg = CorePlugin.instance.config.getString("joinAndQuitMessage.join")
        event.joinMessage = null
        MessageUtil.boardcast(msg)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        // modify quit message
        val msg = CorePlugin.instance.config.getString("joinAndQuitMessage.quit")
        event.quitMessage = null
        MessageUtil.boardcast(msg)
    }
}