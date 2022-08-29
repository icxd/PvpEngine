package me.icoding.pvpengine.commands;

import me.icoding.pvpengine.PvpEngine;
import me.icoding.pvpengine.utils.ConfigUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("pvpengine.spy") || player.isOp()) {
                boolean active = PvpEngine.playerManager.getSpy(player);

                if (!active) {
                    player.sendMessage(ConfigUtil.getFormattedString("messages.spy-enabled"));
                } else {
                    player.sendMessage(ConfigUtil.getFormattedString("messages.spy-disabled"));
                }

                PvpEngine.playerManager.setSpy(player, !PvpEngine.playerManager.getSpy(player));
            } else {
                player.sendMessage(ConfigUtil.getFormattedString("messages.no-permission"));
            }
        } else {
            sender.sendMessage(ConfigUtil.getFormattedString("messages.executed-by-console"));
        }

        return false;
    }
}
