package me.icoding.pvpengine.listeners;

import me.icoding.pvpengine.PvpEngine;
import me.icoding.pvpengine.api.Particle;
import me.icoding.pvpengine.utils.ConfigUtil;
import me.icoding.pvpengine.utils.PacketUtil;
import me.icoding.pvpengine.utils.PermissionsUtil;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!PvpEngine.playerManager.playerExists(player))
            PvpEngine.playerManager.registerPlayer(player);

        PermissionsUtil.setupPermissions(player);

        PvpEngine.vaultManager.register(player);

        player.setHealth(20);
        player.setFoodLevel(20);
        player.setLevel(PvpEngine.playerManager.getLevel(player));
        player.setGameMode(GameMode.ADVENTURE);

        if (PvpEngine.playerManager.getVanish(player)) {
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (pl.hasPermission("pvpengine.vanish.bypass")) {
                    pl.sendMessage(String.format(ConfigUtil.getFormattedString("messages.vanish-message-staff-join"), player.getName()));
                    continue;
                }
                pl.hidePlayer(player);
            }
        }

        if (!ConfigUtil.getBoolean("join-message.display-default"))
            event.setJoinMessage(null);

        if (ConfigUtil.getBoolean("join-message.enabled")) {
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (!ConfigUtil.getBoolean("join-message.display-if-vanished") && !PvpEngine.playerManager.getVanish(player))
                    pl.sendMessage(ConfigUtil.getFormattedString("join-message.message", player));
            }
        }

        if (ConfigUtil.getBoolean("join-particle.enabled")) {
            Particle particle = new Particle(
                    EnumParticle.valueOf(ConfigUtil.getString("join-particle.particle-type")),
                    player.getLocation(),
                    ConfigUtil.getFloat("join-particle.offset.x"),
                    ConfigUtil.getFloat("join-particle.offset.y"),
                    ConfigUtil.getFloat("join-particle.offset.z"),
                    ConfigUtil.getFloat("join-particle.speed"),
                    ConfigUtil.getInt("join-particle.count")
            );
            PacketUtil.sendToAllPlayers(particle.getPacket());
        }

        if (ConfigUtil.getBoolean("random.send-player-to-hub-on-join")) {
            player.teleport(new Location(
                    Bukkit.getWorld(ConfigUtil.getString("hub-location.world")),
                    ConfigUtil.getDouble("hub-location.x"),
                    ConfigUtil.getDouble("hub-location.y"),
                    ConfigUtil.getDouble("hub-location.z"),
                    (float) ConfigUtil.getDouble("hub-location.yaw"),
                    (float) ConfigUtil.getDouble("hub-location.pitch")
            ));
        }
    }

}
