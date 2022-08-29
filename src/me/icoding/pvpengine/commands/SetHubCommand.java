package me.icoding.pvpengine.commands;

import me.icoding.pvpengine.PvpEngine;
import me.icoding.pvpengine.utils.ConfigUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetHubCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("pvpengine.hub.set") || player.isOp()) {
                FileConfiguration config = PvpEngine.INSTANCE.getConfig();

                config.set("hub-location.world", player.getWorld().getName());
                config.set("hub-location.x", player.getLocation().getX());
                config.set("hub-location.y", player.getLocation().getY());
                config.set("hub-location.z", player.getLocation().getZ());
                config.set("hub-location.yaw", player.getLocation().getYaw());
                config.set("hub-location.pitch", player.getLocation().getPitch());

                PvpEngine.INSTANCE.saveConfig();

                player.sendMessage(ConfigUtil.getFormattedString("messages.hub-set"));
            } else {
                player.sendMessage(ConfigUtil.getFormattedString("messages.no-permission"));
            }
        } else {
            sender.sendMessage(ConfigUtil.getFormattedString("messages.executed-by-console"));
        }

        return false;
    }
}
