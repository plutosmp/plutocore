package top.plutomc.plutocore.listeners

import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import top.plutomc.plutocore.CorePlugin
import top.plutomc.plutocore.MessageUtil

class PlayerListener : Listener{
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        val msg = CorePlugin.instance()!!.config.getString("joinAndQuitMessage.join")
        msg?.let { MiniMessage.miniMessage().deserialize(it, Placeholder.parsed("player", player.name)) }?.let { MessageUtil.send(player, it) }
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player
        val msg = CorePlugin.instance()!!.config.getString("joinAndQuitMessage.quit")
        msg?.let { MiniMessage.miniMessage().deserialize(it, Placeholder.parsed("player", player.name)) }?.let { MessageUtil.send(player, it) }
    }
}