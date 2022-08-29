package me.icoding.pvpengine.listeners;

import me.icoding.pvpengine.PvpEngine;
import me.icoding.pvpengine.utils.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("pvpengine.spy") && PvpEngine.playerManager.getSpy(player)) {
                player.sendMessage(String.format(ConfigUtil.getFormattedString("messages.spy-format"), event.getPlayer().getDisplayName(), event.getMessage()));
            }
        }
    }

}
