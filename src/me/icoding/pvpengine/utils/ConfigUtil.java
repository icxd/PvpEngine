package me.icoding.pvpengine.utils;

import me.icoding.pvpengine.PvpEngine;
import org.bukkit.entity.Player;

public class ConfigUtil {

    public static String getFormattedString(String path) {
        String str = PvpEngine.INSTANCE.getConfig().getString(path);
        String prefix = PvpEngine.INSTANCE.getConfig().getString("prefix");
        return ColorUtil.c(str
                .replace("%prefix%", prefix)
        );
    }

    public static String getFormattedString(String path, Player player) {
        String str = PvpEngine.INSTANCE.getConfig().getString(path);
        String prefix = PvpEngine.INSTANCE.getConfig().getString("prefix");
        return ColorUtil.c(str
                .replace("%prefix%", prefix)
                .replace("%player%", PvpEngine.playerManager.getName(player))
                .replace("%rank%", PvpEngine.rankManager.getPrefix(PvpEngine.playerManager.getRank(player)))
                .replace("%name_color%", PvpEngine.rankManager.getNameColor(PvpEngine.playerManager.getRank(player)))
                .replace("%level%", "" + PvpEngine.playerManager.getLevel(player))
                .replace("%level_color%", "" + PvpEngine.playerManager.getLevelColor(PvpEngine.playerManager.getLevel(player)))
        );
    }

    public static String getFormattedString(String path, Player player, String message) {
        String str = PvpEngine.INSTANCE.getConfig().getString(path);
        String prefix = PvpEngine.INSTANCE.getConfig().getString("prefix");
        return ColorUtil.c(str
                .replace("%prefix%", prefix)
                .replace("%player%", PvpEngine.playerManager.getName(player))
                .replace("%rank%", PvpEngine.rankManager.getPrefix(PvpEngine.playerManager.getRank(player)))
                .replace("%name_color%", PvpEngine.rankManager.getNameColor(PvpEngine.playerManager.getRank(player)))
                .replace("%level%", "" + PvpEngine.playerManager.getLevel(player))
                .replace("%level_color%", "" + PvpEngine.playerManager.getLevelColor(PvpEngine.playerManager.getLevel(player)))
                .replace("%message%", message)
        );
    }

    public static boolean getBoolean(String path) {
        return PvpEngine.INSTANCE.getConfig().getBoolean(path);
    }
    public static String getString(String path) {
        return PvpEngine.INSTANCE.getConfig().getString(path);
    }
    public static int getInt(String path) {
        return PvpEngine.INSTANCE.getConfig().getInt(path);
    }
    public static double getDouble(String path) {
        return PvpEngine.INSTANCE.getConfig().getDouble(path);
    }
    public static float getFloat(String path) {
        return (float) PvpEngine.INSTANCE.getConfig().getDouble(path);
    }

}
