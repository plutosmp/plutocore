package top.plutomc.plutocore.utils

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import top.plutomc.plutocore.CorePlugin

object MessageUtil {
    fun send(player: Player, component: Component) {
        val playerAudience = CorePlugin.bukkitAudiences()!!.player(player)
        playerAudience.sendMessage(component)
    }
}