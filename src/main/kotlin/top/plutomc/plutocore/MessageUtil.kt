package top.plutomc.plutocore

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

object MessageUtil {
    fun send(player: Player, component: Component) {
        val playerAudience = CorePlugin.bukkitAudiences!!.player(player)
        playerAudience.sendMessage(component)
    }
}