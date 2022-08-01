package top.plutomc.plutocore.listeners

import de.tr7zw.nbtapi.NBTItem
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack
import top.plutomc.plutocore.CorePlugin
import top.plutomc.plutocore.menus.ArmorMenu
import top.plutomc.plutocore.utils.LinkParser
import top.plutomc.plutocore.utils.MessageUtil

class PlayerListener : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        // switch gamemode to survival & add cache
        event.player.gameMode = GameMode.SURVIVAL
        CorePlugin.gameModeCache[event.player.uniqueId] = GameMode.SURVIVAL

        // modify join message
        val msg = CorePlugin.instance.config.getString("joinAndQuitMessage.join")
        event.joinMessage = null
        MessageUtil.broadcast(msg, Placeholder.parsed("player", event.player.name))
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        // remove gamemode cache
        CorePlugin.gameModeCache.remove(event.player.uniqueId)

        // modify quit message
        val msg = CorePlugin.instance.config.getString("joinAndQuitMessage.quit")
        event.quitMessage = null
        MessageUtil.broadcast(msg, Placeholder.parsed("player", event.player.name))
    }

    @EventHandler
    fun onPlayerInteractEntity(event: PlayerInteractEntityEvent) {
        val entity = event.rightClicked
        val player = event.player
        if (player.isSneaking) {
            if (entity is Player) {
                val target: Player = entity
                ArmorMenu(player, target)
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun onAsyncPlayerChat(event: AsyncPlayerChatEvent) {
        if (event.isCancelled.not()) {
            val s: String? = if (event.player.inventory.itemInMainHand.type != Material.AIR) {
                val nbtString: String = NBTItem(event.player.inventory.itemInMainHand).asNBTString()
                CorePlugin.instance.config.getString("chatFormat.itemShow.placeholder")
                    ?.let { event.message.replace(it, "<hover:show_item:${event.player.inventory.itemInMainHand.type.toString().lowercase()}:1:'$nbtString'><aqua><u>查看物品</u></aqua></hover>") }
            }else {
                event.message
            }
            MessageUtil.broadcast(CorePlugin.instance.config.getString("chatFormat.format"),
                Placeholder.parsed("player", event.player.displayName),
                Placeholder.parsed("message", LinkParser.parseUrl(s,
                CorePlugin.instance.config.getString("chatFormat.linkHighlight.hover"))))
            event.isCancelled = true
        }
    }

}