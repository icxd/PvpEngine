package me.icoding.pvpengine.utils;

import me.icoding.pvpengine.PvpEngine;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.List;

public class PermissionsUtil {

    public static void setupPermissions(Player player) {
        PermissionAttachment attachment = player.addAttachment(PvpEngine.INSTANCE);
        PvpEngine.playerPermissions.put(player.getUniqueId(), attachment);
        setPermissions(player);
    }

    public static void setPermissions(Player player) {
        PermissionAttachment attachment = PvpEngine.playerPermissions.get(player.getUniqueId());

        List<String> permissions = PvpEngine.rankManager.getPermissions(PvpEngine.playerManager.getRank(player));
        for (String permission : permissions) {
            System.out.println(permission);
            attachment.setPermission(permission, true);
        }
    }

}
