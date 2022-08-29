package me.icoding.pvpengine.commands;

import me.icoding.pvpengine.gui.TrashGUI;
import me.icoding.pvpengine.utils.ConfigUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrashCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            new TrashGUI().open(player);
        } else {
            sender.sendMessage(ConfigUtil.getFormattedString("messages.executed-by-console"));
        }

        return false;
    }
}
