package me.icoding.pvpengine.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class HungerListener implements Listener {

    @EventHandler
    public void onLoseHunger(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

}
