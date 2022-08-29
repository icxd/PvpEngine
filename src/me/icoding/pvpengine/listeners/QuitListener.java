package me.icoding.pvpengine.listeners;

import me.icoding.pvpengine.PvpEngine;
import me.icoding.pvpengine.utils.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (!ConfigUtil.getBoolean("leave-message.display-default"))
            event.setQuitMessage(null);

        if (ConfigUtil.getBoolean("leave-message.enabled")) {
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (!ConfigUtil.getBoolean("leave-message.display-if-vanished") && !PvpEngine.playerManager.getVanish(player))
                    pl.sendMessage(ConfigUtil.getFormattedString("leave-message.message", player));
            }
        }

        PvpEngine.playerPermissions.remove(player.getUniqueId());
    }

}
