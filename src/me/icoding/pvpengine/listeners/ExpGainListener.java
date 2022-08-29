package me.icoding.pvpengine.listeners;

import me.icoding.pvpengine.PvpEngine;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;

public class ExpGainListener implements Listener {

    @EventHandler
    public void onExpGain(PlayerExpChangeEvent event) {
        event.setAmount(0);
        event.getPlayer().setLevel(PvpEngine.playerManager.getLevel(event.getPlayer()));
    }

    @EventHandler
    public void onLevelChange(PlayerLevelChangeEvent event) {
        event.getPlayer().setLevel(PvpEngine.playerManager.getLevel(event.getPlayer()));
    }

}
