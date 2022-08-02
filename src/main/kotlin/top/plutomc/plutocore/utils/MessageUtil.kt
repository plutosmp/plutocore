package top.plutomc.plutocore.utils

import me.clip.placeholderapi.PlaceholderAPI
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.md_5.bungee.api.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import top.plutomc.plutocore.CorePlugin

object MessageUtil {
    fun send(sender: CommandSender, message: String, vararg tagResolver: TagResolver) {
        val s = if (sender is Player) {
            PlaceholderAPI.setPlaceholders(sender, message)
        } else {
            message
        }
        val senderAudience = CorePlugin.bukkitAudiences.sender(sender)
        senderAudience.sendMessage(MiniMessage.miniMessage().deserialize(s, *tagResolver))
    }

    fun send(sender: CommandSender, message: String, placeholderTarget: Player, vararg tagResolver: TagResolver) {
        val s = if (sender is Player) {
            PlaceholderAPI.setPlaceholders(placeholderTarget, message)
        } else {
            message
        }
        val senderAudience = CorePlugin.bukkitAudiences.sender(sender)
        senderAudience.sendMessage(MiniMessage.miniMessage().deserialize(s, *tagResolver))
    }

    fun broadcast(message: String, vararg tagResolver: TagResolver) {
        CorePlugin.instance.server.onlinePlayers.forEach { send(it, message, *tagResolver) }
        send(CorePlugin.instance.server.consoleSender, message, *tagResolver)
    }

    fun broadcast(message: String, placeholderTarget: Player, vararg tagResolver: TagResolver) {
        CorePlugin.instance.server.onlinePlayers.forEach { send(it, message, *tagResolver) }
        send(CorePlugin.instance.server.consoleSender, message, placeholderTarget, *tagResolver)
    }

    fun parseLegacyColor(string: String): String = ChatColor.translateAlternateColorCodes('&', string)
}