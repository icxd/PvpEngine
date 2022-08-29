package me.icoding.pvpengine.commands;

import me.icoding.pvpengine.PvpEngine;
import me.icoding.pvpengine.utils.ColorUtil;
import me.icoding.pvpengine.utils.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length == 3) {
                try {
                    Player target = Bukkit.getPlayer(args[0]);

                    if (args[1].equalsIgnoreCase("level")) {
                        int newLevel = Integer.parseInt(args[2]);
                        PvpEngine.playerManager.setLevel(target, newLevel);
                        target.setLevel(PvpEngine.playerManager.getLevel(target));
                    } else if (args[1].equalsIgnoreCase("kills")) {
                        int newKills = Integer.parseInt(args[2]);
                        PvpEngine.playerManager.setKills(target, newKills);
                    } else if (args[1].equalsIgnoreCase("deaths")) {
                        int newDeaths = Integer.parseInt(args[2]);
                        PvpEngine.playerManager.setDeaths(target, newDeaths);
                    } else if (args[1].equalsIgnoreCase("coins")) {
                        int newCoins = Integer.parseInt(args[2]);
                        PvpEngine.playerManager.setCoins(target, newCoins);
                    } else if (args[1].equalsIgnoreCase("rank")) {
                        if (PvpEngine.rankManager.rankExists(args[2].toLowerCase())) {
                            PvpEngine.playerManager.setRank(target, args[2].toLowerCase());
                            target.kickPlayer(ConfigUtil.getFormattedString("messages.rank-kick"));
                        } else {
                            sender.sendMessage(ConfigUtil.getFormattedString("messages.no-rank"));
                            return false;
                        }
                    } else if (args[1].equalsIgnoreCase("name")) {
                        PvpEngine.playerManager.setName(target, args[2]);
                    }

                    sender.sendMessage(ColorUtil.colorWithPlaceholders("%prefix% &bSet value &9'" + args[1].toLowerCase() + "'&b to &9'" + args[2] + "'&b for player &9" + target.getName() + "&b."));
                    return true;
                } catch (NullPointerException ignored) {
                    sender.sendMessage(ConfigUtil.getFormattedString("messages.player-not-found"));
                }
            } else {
                sender.sendMessage(ConfigUtil.getFormattedString("messages.no-args"));
            }
        }

            Player player = (Player) sender;

        if (player.hasPermission("pvpengine.set") || player.isOp()) {
            if (args.length == 3) {
                try {
                    Player target = Bukkit.getPlayer(args[0]);

                    if (args[1].equalsIgnoreCase("level")) {
                        int newLevel = Integer.parseInt(args[2]);
                        PvpEngine.playerManager.setLevel(target, newLevel);
                        target.setLevel(PvpEngine.playerManager.getLevel(target));
                    } else if (args[1].equalsIgnoreCase("kills")) {
                        int newKills = Integer.parseInt(args[2]);
                        PvpEngine.playerManager.setKills(target, newKills);
                    } else if (args[1].equalsIgnoreCase("deaths")) {
                        int newDeaths = Integer.parseInt(args[2]);
                        PvpEngine.playerManager.setDeaths(target, newDeaths);
                    } else if (args[1].equalsIgnoreCase("coins")) {
                        int newCoins = Integer.parseInt(args[2]);
                        PvpEngine.playerManager.setCoins(target, newCoins);
                    } else if (args[1].equalsIgnoreCase("rank")) {
                        if (PvpEngine.rankManager.rankExists(args[2].toLowerCase())) {
                            PvpEngine.playerManager.setRank(target, args[2].toLowerCase());
                            target.kickPlayer(ConfigUtil.getFormattedString("messages.rank-kick"));
                        } else {
                            player.sendMessage(ConfigUtil.getFormattedString("messages.no-rank"));
                            return false;
                        }
                    } else if (args[1].equalsIgnoreCase("name")) {
                        PvpEngine.playerManager.setName(target, args[2]);
                    }

                    player.sendMessage(ColorUtil.colorWithPlaceholders("%prefix% &bSet value &9'" + args[1].toLowerCase() + "'&b to &9'" + args[2] + "'&b for player &9" + target.getName() + "&b."));
                    return true;
                } catch (NullPointerException ignored) {
                    player.sendMessage(ConfigUtil.getFormattedString("messages.player-not-found"));
                }
            } else {
                player.sendMessage(ConfigUtil.getFormattedString("messages.no-args"));
            }
        } else {
            player.sendMessage(ConfigUtil.getFormattedString("messages.no-permission"));
        }


        return false;
    }
}
