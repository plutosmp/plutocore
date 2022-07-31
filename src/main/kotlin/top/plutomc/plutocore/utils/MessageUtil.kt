package top.plutomc.plutocore.utils

import me.clip.placeholderapi.PlaceholderAPI
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.entity.Player
import top.plutomc.plutocore.CorePlugin

object MessageUtil {
    fun send(player: Player, message: String?, vararg tagResolver: TagResolver) {
        val s = message?.let { PlaceholderAPI.setPlaceholders(player, it) }
        val playerAudience = CorePlugin.bukkitAudiences()!!.player(player)
        s?.let { MiniMessage.miniMessage().deserialize(it, *tagResolver) }?.let { playerAudience.sendMessage(it) }
    }
}