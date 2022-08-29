package me.icoding.pvpengine.commands;

import me.icoding.pvpengine.PvpEngine;
import me.icoding.pvpengine.utils.ConfigUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PvpEngineCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("pvpengine.pvpengine") || player.isOp()) {
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        if (args[1].equalsIgnoreCase("all")) {
                            PvpEngine.INSTANCE.reloadConfig();
                            PvpEngine.playerManager.reloadConfig();
                            PvpEngine.rankManager.reloadConfig();
                            PvpEngine.kitsManager.reloadConfig();
                            PvpEngine.vaultManager.reloadConfig();
                            player.sendMessage(String.format(ConfigUtil.getFormattedString("messages.reload"), "all"));
                        } else if (args[1].equalsIgnoreCase("config")) {
                            PvpEngine.INSTANCE.reloadConfig();
                            player.sendMessage(String.format(ConfigUtil.getFormattedString("messages.reload"), "config"));
                        } else if (args[1].equalsIgnoreCase("players")) {
                            PvpEngine.playerManager.reloadConfig();
                            player.sendMessage(String.format(ConfigUtil.getFormattedString("messages.reload"), "players"));
                        } else if (args[1].equalsIgnoreCase("ranks")) {
                            PvpEngine.rankManager.reloadConfig();
                            player.sendMessage(String.format(ConfigUtil.getFormattedString("messages.reload"), "ranks"));
                        } else if (args[1].equalsIgnoreCase("kits")) {
                            PvpEngine.kitsManager.reloadConfig();
                            player.sendMessage(String.format(ConfigUtil.getFormattedString("messages.reload"), "kits"));
                        } else if (args[1].equalsIgnoreCase("vault")) {
                            PvpEngine.vaultManager.reloadConfig();
                            player.sendMessage(String.format(ConfigUtil.getFormattedString("messages.reload"), "vaults"));
                        }
                    }
                } else {
                    player.sendMessage(ConfigUtil.getFormattedString("messages.no-args"));
                }
            } else {
                player.sendMessage(ConfigUtil.getFormattedString("messages.no-permission"));
            }
        } else {
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (args[1].equalsIgnoreCase("all")) {
                        PvpEngine.INSTANCE.reloadConfig();
                        PvpEngine.playerManager.reloadConfig();
                        PvpEngine.rankManager.reloadConfig();
                        PvpEngine.kitsManager.reloadConfig();
                        PvpEngine.vaultManager.reloadConfig();
                        sender.sendMessage(String.format(ConfigUtil.getFormattedString("messages.reload"), "all"));
                    } else if (args[1].equalsIgnoreCase("config")) {
                        PvpEngine.INSTANCE.reloadConfig();
                        sender.sendMessage(String.format(ConfigUtil.getFormattedString("messages.reload"), "config"));
                    } else if (args[1].equalsIgnoreCase("players")) {
                        PvpEngine.playerManager.reloadConfig();
                        sender.sendMessage(String.format(ConfigUtil.getFormattedString("messages.reload"), "players"));
                    } else if (args[1].equalsIgnoreCase("ranks")) {
                        PvpEngine.rankManager.reloadConfig();
                        sender.sendMessage(String.format(ConfigUtil.getFormattedString("messages.reload"), "ranks"));
                    } else if (args[1].equalsIgnoreCase("kits")) {
                        PvpEngine.kitsManager.reloadConfig();
                        sender.sendMessage(String.format(ConfigUtil.getFormattedString("messages.reload"), "kits"));
                    } else if (args[1].equalsIgnoreCase("vault")) {
                        PvpEngine.vaultManager.reloadConfig();
                        sender.sendMessage(String.format(ConfigUtil.getFormattedString("messages.reload"), "vaults"));
                    }
                }
            } else {
                sender.sendMessage(ConfigUtil.getFormattedString("messages.no-args"));
            }
        }

        return false;
    }
}
