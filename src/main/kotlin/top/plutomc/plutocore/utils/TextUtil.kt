package top.plutomc.plutocore.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer

object TextUtil {
    fun toLegacy(component: Component): String {
        return LegacyComponentSerializer.legacySection().serialize(component)
    }
}