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
        val player = event.player
        val msg = CorePlugin.instance.config.getString("joinAndQuitMessage.join")
        MessageUtil.send(player, msg)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        // modify quit message
        val player = event.player
        val msg = CorePlugin.instance.config.getString("joinAndQuitMessage.quit")
        MessageUtil.send(player, msg)
    }
}