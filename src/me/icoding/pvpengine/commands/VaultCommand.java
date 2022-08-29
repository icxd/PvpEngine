package me.icoding.pvpengine.commands;

import me.icoding.pvpengine.gui.VaultGUI;
import me.icoding.pvpengine.gui.VaultOthersGUI;
import me.icoding.pvpengine.utils.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VaultCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length <= 0) {
                new VaultGUI(player).open(player);
            } else if (args.length == 1 && player.hasPermission("pvpengine.vault.others")) {
                try {
                    new VaultOthersGUI(Bukkit.getPlayer(args[0])).open(player);
                } catch(NullPointerException ignored) {
                    player.sendMessage(ConfigUtil.getFormattedString("messages.player-not-found"));
                }
            }
        } else {
            sender.sendMessage(ConfigUtil.getFormattedString("messages.executed-by-console"));
        }

        return false;
    }
}
