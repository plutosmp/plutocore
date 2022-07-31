package top.plutomc.plutocore.utils

import me.clip.placeholderapi.PlaceholderAPI
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import top.plutomc.plutocore.CorePlugin

object MessageUtil {
    fun send(sender: CommandSender, message: String?, vararg tagResolver: TagResolver) {
        var s = message
        if (sender is Player) {
            s = message?.let { PlaceholderAPI.setPlaceholders(sender, it) }
        }
        val playerAudience = CorePlugin.bukkitAudiences()!!.sender(sender)
        s!!.let { MiniMessage.miniMessage().deserialize(it, *tagResolver) }.let { playerAudience.sendMessage(it) }
    }
}