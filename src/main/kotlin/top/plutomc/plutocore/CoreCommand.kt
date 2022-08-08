package top.plutomc.plutocore

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import top.plutomc.plutocore.utils.MessageUtil

class CoreCommand : TabExecutor {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String> {
        if (args!!.size == 1) return mutableListOf("reload", "debug")
        return mutableListOf()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (args!!.size == 1 && args[0] == "reload") {
            CorePlugin.reloadPlugin()
            MessageUtil.send(sender, "<green>Reload completed.")
        }
        if (args.size >= 2 && args[0] == "debug" && CorePlugin.modules.keys.contains(args[0])) {
            val module = CorePlugin.modules[args[1]]
            if (sender is Player) {
                if (module!!.debuggers.contains(sender.uniqueId).not()) {
                    module.debuggers.add(sender.uniqueId)
                    MessageUtil.send(sender, "<green>Added.")
                } else {
                    module.debuggers.remove(sender.uniqueId)
                    MessageUtil.send(sender, "<red>Removed.")
                }
            }
        }
        return true
    }
}