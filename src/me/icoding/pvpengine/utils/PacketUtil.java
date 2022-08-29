package me.icoding.pvpengine.utils;

import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketUtil {

    public static void sendToPlayer(Player player, Packet<?> packet) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public static void sendToAllPlayers(Packet<?> packet) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendToPlayer(player, packet);
        }
    }

    static void sendPacket(Packet<?> packet){
        for(Player player : Bukkit.getOnlinePlayers()){
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
        }
    }
}
