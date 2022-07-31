package top.plutomc.plutocore.commands

import org.bukkit.GameMode
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import top.plutomc.plutocore.CorePlugin
import top.plutomc.plutocore.utils.MessageUtil

class GameModeCommand : TabExecutor {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String> {
        if (args!!.size == 1) return mutableListOf("creative", "adventure", "survival", "spectator")
        if (args.size == 2) {
            val list = ArrayList<String>()
            CorePlugin.instance.server.onlinePlayers.forEach {
                list.add(it.name)
            }
            return list
        }
        return mutableListOf()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (args!!.isNotEmpty()) {
            if (listOf("creative", "adventure", "survival", "spectator").contains(args[0].lowercase())
                && CorePlugin.instance.server.getPlayer(args[1]) != null
            ) {
                val player = CorePlugin.instance.server.getPlayer(args[1])
                player!!.gameMode = GameMode.valueOf(args[0].uppercase())
                CorePlugin.gameModeCache[player.uniqueId] = GameMode.valueOf(args[0].uppercase())
                MessageUtil.send(sender, "<green>成功将 <yellow>${args[1]} <green>的游戏模式修改为 <yellow>${args[0]}。")
            }
        }
        return true
    }
}