package top.plutomc.plutocore.modules.armormenu

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent
import top.plutomc.plutocore.Module
import top.plutomc.plutocore.menus.ArmorMenu
import top.plutomc.plutocore.utils.TextUtil

class ArmorMenu : Module("armorMenu"), Listener {
    override fun load() {
        // zh_cn
        addLocaleContent("zh_cn", "placeholder", "<gray>占位符")
        addLocaleContent("zh_cn", "noWeapon", "<red>这个位置没有装备")
        addLocaleContent("zh_cn", "close", "<red>关闭")
        saveLocaleContent("zh_cn")

        // zh_tw
        addLocaleContent("zh_tw", "placeholder", "<gray>佔位符")
        addLocaleContent("zh_tw", "noWeapon", "<red>這個位置沒有裝備")
        addLocaleContent("zh_tw", "close", "<red>關閉")
        saveLocaleContent("zh_tw")

        // zh_hk
        addLocaleContent("zh_hk", "placeholder", "<gray>佔位符")
        addLocaleContent("zh_hk", "noWeapon", "<red>這個位置沒有裝備")
        addLocaleContent("zh_hk", "close", "<red>關閉")
        saveLocaleContent("zh_hk")

        // en_us
        addLocaleContent("en_us", "placeholder", "<gray>This is a placeholder")
        addLocaleContent("en_us", "noWeapon", "<red>No weapon is in this slot")
        addLocaleContent("en_us", "close", "<red>Close")
        saveLocaleContent("en_us")

        registerEvents("armorMenu", this)
    }

    override fun unload() {
    }

    @EventHandler
    fun playerInteractEntityEvent(event: PlayerInteractEntityEvent) {
        val entity = event.rightClicked
        val player = event.player
        if (player.isSneaking) {
            if (entity is Player) {
                val target: Player = entity
                ArmorMenu(
                    player,
                    target,
                    TextUtil.toLegacy(
                        MiniMessage.miniMessage().deserialize(getLocaleContentAsString(player.locale, "placeholder"))
                    ),
                    TextUtil.toLegacy(
                        MiniMessage.miniMessage().deserialize(getLocaleContentAsString(player.locale, "noWeapon"))
                    ),
                    TextUtil.toLegacy(
                        MiniMessage.miniMessage().deserialize(getLocaleContentAsString(player.locale, "close"))
                    )
                )
            }
        }
    }
}