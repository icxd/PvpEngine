package me.icoding.pvpengine.commands;

import me.icoding.pvpengine.PvpEngine;
import me.icoding.pvpengine.utils.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("pvpengine.vanish") || player.isOp()) {
                boolean active = PvpEngine.playerManager.getVanish(player);

                if (!active) {
                    player.sendMessage(ConfigUtil.getFormattedString("messages.vanish-enabled"));
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        if (pl.hasPermission("pvpengine.vanish.bypass")) {
                            pl.sendMessage(String.format(ConfigUtil.getFormattedString("messages.vanish-message-staff-enabled"), player.getName()));
                            continue;
                        }
                        pl.hidePlayer(player);
                    }
                } else {
                    player.sendMessage(ConfigUtil.getFormattedString("messages.vanish-disabled"));
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        if (pl.hasPermission("pvpengine.vanish.bypass")) {
                            pl.sendMessage(String.format(ConfigUtil.getFormattedString("messages.vanish-message-staff-disabled"), player.getName()));
                            continue;
                        }
                        pl.showPlayer(player);
                    }
                }

                PvpEngine.playerManager.setVanish(player, !PvpEngine.playerManager.getVanish(player));
            } else {
                player.sendMessage(ConfigUtil.getFormattedString("messages.no-permission"));
            }
        } else {
            sender.sendMessage(ConfigUtil.getFormattedString("messages.executed-by-console"));
        }

        return false;
    }
}
