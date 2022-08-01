package top.plutomc.plutocore.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.scheduler.BukkitRunnable
import top.plutomc.plutocore.CorePlugin
import top.plutomc.plutocore.utils.MessageUtil
import top.plutomc.plutocore.utils.NmsRefUtil

class TpsCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        object : BukkitRunnable() {
            override fun run() {
                MessageUtil.send(sender, "<green>当前 TPS 是：<yellow>${NmsRefUtil.getRecentTps()}。")
            }
        }.runTaskAsynchronously(CorePlugin.instance)
        return true
    }
}