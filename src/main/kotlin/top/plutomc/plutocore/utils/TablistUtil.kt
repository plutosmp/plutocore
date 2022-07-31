package top.plutomc.plutocore.utils

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import top.plutomc.plutocore.CorePlugin

object TablistUtil {
    fun updateHeader(component: Component) {
        CorePlugin.instance()!!.server.onlinePlayers.forEach {
            updateHeaderFor(it, component)
        }
    }

    private fun updateHeaderFor(player: Player, component: Component) {
        val playerAudience = CorePlugin.bukkitAudiences()!!.player(player)
        playerAudience.sendPlayerListHeader(component)
    }

    fun updateFooter(component: Component) {
        CorePlugin.instance()!!.server.onlinePlayers.forEach {
            updateFooterFor(it, component)
        }
    }

    private fun updateFooterFor(player: Player, component: Component) {
        val playerAudience = CorePlugin.bukkitAudiences()!!.player(player)
        playerAudience.sendPlayerListFooter(component)
    }
}