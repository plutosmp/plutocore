package top.plutomc.plutocore.utils

import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.command.CommandSender
import top.plutomc.plutocore.CorePlugin

object LocaleUtil {
    fun send(sender: CommandSender, localeKey: String = "error", vararg tagResolver: TagResolver) {
        MessageUtil.send(sender, CorePlugin.instance.config.getString("locale.$localeKey"), *tagResolver)
    }

    fun get(localeKey: String) : String {
        val string = CorePlugin.instance.config.getString("locale.$localeKey")
        if (string != null) {
            return string
        }
        return ""
    }
}