package me.icoding.pvpengine.commands.gamemode;

import me.icoding.pvpengine.utils.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GMSCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("pvpengine.gamemode.survival") || player.isOp()) {
                try {
                    if (args.length != 1) {
                        player.setGameMode(GameMode.SURVIVAL);
                        player.sendMessage(String.format(ConfigUtil.getFormattedString("messages.gamemode-change"), GameMode.SURVIVAL.name()));
                    } else {
                        Player target = Bukkit.getPlayer(args[0]);
                        target.setGameMode(GameMode.SURVIVAL);
                        player.sendMessage(String.format(ConfigUtil.getFormattedString("messages.gamemode-change-other"), target.getName(), GameMode.SURVIVAL.name()));
                    }
                } catch(NullPointerException ignored) {
                    player.sendMessage(ConfigUtil.getFormattedString("messages.player-not-found"));
                }
            } else {
                player.sendMessage(ConfigUtil.getFormattedString("messages.no-permission"));
            }
        } else {
            sender.sendMessage(ConfigUtil.getFormattedString("messages.executed-by-console"));
        }

        return false;
    }
}
