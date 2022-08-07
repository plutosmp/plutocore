package top.plutomc.plutocore.modules.armormenu

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEntityEvent
import top.plutomc.plutocore.Module
import top.plutomc.plutocore.menus.ArmorMenu

class ArmorMenu : Module("armorMenu") {
    override fun load() {
        // zh_cn
        addLocaleContent("zh_cn", "placeholder", "&7占位符")
        addLocaleContent("zh_cn", "noWeapon", "&c这个位置没有装备")
        addLocaleContent("zh_cn", "close", "&c关闭")
        saveLocaleContent("zh_cn")

        // zh_tw
        addLocaleContent("zh_tw", "placeholder", "&7佔位符")
        addLocaleContent("zh_tw", "noWeapon", "&c這個位置沒有裝備")
        addLocaleContent("zh_tw", "close", "&c關閉")
        saveLocaleContent("zh_tw")

        // zh_hk
        addLocaleContent("zh_hk", "placeholder", "&7佔位符")
        addLocaleContent("zh_hk", "noWeapon", "&c這個位置沒有裝備")
        addLocaleContent("zh_hk", "close", "&c關閉")
        saveLocaleContent("zh_hk")

        // en_us
        addLocaleContent("en_us", "placeholder", "&7This is a placeholder")
        addLocaleContent("en_us", "noWeapon", "&cNo weapon is in this slot")
        addLocaleContent("en_us", "close", "&cClose")
        saveLocaleContent("en_us")
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
                    player, target, getLocaleContentAsString(player.locale, "placeholder"),
                    getLocaleContentAsString(player.locale, "noWeapon"),
                    getLocaleContentAsString(player.locale, "close")
                )
            }
        }
    }
}