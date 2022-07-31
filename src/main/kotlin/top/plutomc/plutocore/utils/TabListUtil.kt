package top.plutomc.plutocore.utils

import me.clip.placeholderapi.PlaceholderAPI
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.entity.Player
import top.plutomc.plutocore.CorePlugin

object TabListUtil {
    fun updateHeader(content: String?) {
        CorePlugin.instance.server.onlinePlayers.forEach {
            updateHeaderFor(it, content)
        }
    }

    private fun updateHeaderFor(player: Player, content: String?, vararg tagResolver: TagResolver) {
        val playerAudience = CorePlugin.bukkitAudiences.player(player)
        content?.let { MiniMessage.miniMessage().deserialize(PlaceholderAPI.setPlaceholders(player, it), *tagResolver) }
            ?.let { playerAudience.sendPlayerListHeader(it) }
    }

    fun updateFooter(content: String?) {
        CorePlugin.instance.server.onlinePlayers.forEach {
            updateFooterFor(it, content)
        }
    }

    private fun updateFooterFor(player: Player, content: String?, vararg tagResolver: TagResolver) {
        val playerAudience = CorePlugin.bukkitAudiences.player(player)
        content?.let { MiniMessage.miniMessage().deserialize(PlaceholderAPI.setPlaceholders(player, it), *tagResolver) }
            ?.let { playerAudience.sendPlayerListFooter(it) }
    }
}