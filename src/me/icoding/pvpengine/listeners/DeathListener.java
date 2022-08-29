package me.icoding.pvpengine.listeners;

import me.icoding.pvpengine.PvpEngine;
import me.icoding.pvpengine.utils.ColorUtil;
import me.icoding.pvpengine.utils.ConfigUtil;
import me.icoding.pvpengine.utils.InventoryUtil;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.projectiles.ProjectileSource;

import java.text.NumberFormat;
import java.util.Random;

public class DeathListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        if (PvpEngine.playerManager.getVanish((Player) event.getDamager())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (!(player.getKiller() instanceof Player)) return;

        Player killer = (Player) player.getKiller();

        killer.playSound(killer.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);

        InventoryUtil.clearInventory(player);
        InventoryUtil.giveKit(player, "pvp");

        player.setHealth(player.getMaxHealth());
        player.setFireTicks(0);

        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }

        player.teleport(new Location(
                Bukkit.getWorld(ConfigUtil.getString("hub-location.world")),
                ConfigUtil.getDouble("hub-location.x"),
                ConfigUtil.getDouble("hub-location.y"),
                ConfigUtil.getDouble("hub-location.z"),
                (float) ConfigUtil.getDouble("hub-location.yaw"),
                (float) ConfigUtil.getDouble("hub-location.pitch")
        ));
        player.setLevel(PvpEngine.playerManager.getLevel(player));

        PvpEngine.playerManager.addKill(killer);
        PvpEngine.playerManager.addCoins(killer, random(100, 1000));

        if (PvpEngine.playerManager.getKills(killer) % 10 == 0) {
            PvpEngine.playerManager.gainLevel(killer);

            NumberFormat format = NumberFormat.getInstance();

            for (String line : PvpEngine.INSTANCE.getConfig().getStringList("level-gain-message")) {
                killer.sendMessage(ColorUtil.c(line
                        .replace("%kills%", ""+PvpEngine.playerManager.getKills(killer))
                        .replace("%deaths%", ""+PvpEngine.playerManager.getDeaths(killer))
                        .replace("%rank%", ""+PvpEngine.playerManager.getRank(killer).toUpperCase())
                        .replace("%level%", ""+format.format(PvpEngine.playerManager.getLevel(killer)))
                        .replace("%level_color%", ""+PvpEngine.playerManager.getLevelColor(PvpEngine.playerManager.getLevel(killer)))
                ));
            }
            (killer).playSound(killer.getLocation(), Sound.LEVEL_UP, 1, 1);
            Firework fw = (Firework) killer.getWorld().spawnEntity(killer.getLocation(), EntityType.FIREWORK);
            FireworkMeta meta = fw.getFireworkMeta();
            meta.addEffect(FireworkEffect.builder().flicker(false).withColor(Color.RED).withFade(Color.WHITE).trail(true).build());
            meta.setPower(3);
            fw.setFireworkMeta(meta);

        }

        PvpEngine.playerManager.addDeath(player);
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
            event.setCancelled(true);
        }
    }

    public static int random(int min, int max)
    {
        return new Random().nextInt((max - min) + 1) + min;
    }

}
