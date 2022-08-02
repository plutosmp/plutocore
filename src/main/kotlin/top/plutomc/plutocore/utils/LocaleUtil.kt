package top.plutomc.plutocore.utils

import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.command.CommandSender
import top.plutomc.plutocore.CorePlugin

object LocaleUtil {
    fun send(sender: CommandSender, localeKey: String, vararg tagResolver: TagResolver) {
        val locale = CorePlugin.instance.config.getString("locale.$localeKey")
        if (locale != null) {
            MessageUtil.send(sender, locale, *tagResolver)
        }
    }

    fun get(localeKey: String): String {
        val locale = CorePlugin.instance.config.getString("locale.$localeKey")
        if (locale != null) {
            return locale
        }
        return "ERROR"
    }
}