package me.icoding.pvpengine.commands;

import me.icoding.pvpengine.PvpEngine;
import me.icoding.pvpengine.utils.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class HubCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("pvpengine.hub") || player.isOp()) {
                FileConfiguration config = PvpEngine.INSTANCE.getConfig();

                player.teleport(new Location(
                        Bukkit.getWorld(config.getString("hub-location.world")),
                        config.getDouble("hub-location.x"),
                        config.getDouble("hub-location.y"),
                        config.getDouble("hub-location.z"),
                        (float) config.getDouble("hub-location.yaw"),
                        (float) config.getDouble("hub-location.pitch")
                ));

                player.sendMessage(ConfigUtil.getFormattedString("messages.teleporting"));
            } else {
                player.sendMessage(ConfigUtil.getFormattedString("messages.no-permission"));
            }
        } else {
            sender.sendMessage(ConfigUtil.getFormattedString("messages.executed-by-console"));
        }

        return false;
    }
}
