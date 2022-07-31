package top.plutomc.plutocore.utils

import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.command.CommandSender
import top.plutomc.plutocore.CorePlugin

object LocaleUtil {
    fun send(sender: CommandSender, localeKey : String = "error", vararg tagResolver: TagResolver) {
        MessageUtil.send(sender, CorePlugin.instance.config.getString("locale.$localeKey"), *tagResolver)
    }
}