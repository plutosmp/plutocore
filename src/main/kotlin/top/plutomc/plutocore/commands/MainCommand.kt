package top.plutomc.plutocore.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import top.plutomc.plutocore.CorePlugin
import top.plutomc.plutocore.utils.MessageUtil

class MainCommand : TabExecutor{
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String> {
        if (args!!.size == 1) return mutableListOf("reload")
        return mutableListOf()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (args!!.size == 1 && args[0] == "reload") {
            CorePlugin.reloadPlugin()
            MessageUtil.send(sender, "<green>Reload completed.")
        }
        return true
    }
}