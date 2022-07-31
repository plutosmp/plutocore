package top.plutomc.plutocore.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.entity.Player
import top.plutomc.plutocore.CorePlugin

object TablistUtil {
    fun updateHeader(content: String?) {
        CorePlugin.instance()!!.server.onlinePlayers.forEach {
            updateHeaderFor(it, content)
        }
    }

    private fun updateHeaderFor(player: Player, content: String?, vararg tagResolver: TagResolver) {
        val playerAudience = CorePlugin.bukkitAudiences()!!.player(player)
        content?.let { MiniMessage.miniMessage().deserialize(it, *tagResolver) }?.let { playerAudience.sendPlayerListHeader(it) }
    }

    fun updateFooter(content: String?) {
        CorePlugin.instance()!!.server.onlinePlayers.forEach {
            updateFooterFor(it, content)
        }
    }

    private fun updateFooterFor(player: Player, content: String?, vararg tagResolver: TagResolver) {
        val playerAudience = CorePlugin.bukkitAudiences()!!.player(player)
        content?.let { MiniMessage.miniMessage().deserialize(it, *tagResolver) }?.let { playerAudience.sendPlayerListFooter(it) }
    }
}