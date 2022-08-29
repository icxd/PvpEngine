package me.icoding.pvpengine.utils;

import me.icoding.pvpengine.PvpEngine;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ColorUtil {

    public static String c(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static String colorWithPlaceholders(String str) {
        return ChatColor.translateAlternateColorCodes('&', str
                .replace("%prefix%", ConfigUtil.getFormattedString("prefix")));
    }

    public static List<String> c(String... strs) {
        List<String> s = new ArrayList<>();
        for (String str : strs) s.add(c(str));
        return s;
    }

    private static String[] COLORS = new String[] {
            "&c", "&6", "&e", "&a", "&b", "&9", "&d"
    };

    public static String rainbowify(String str) {
        String[] parts = str.split("");
        StringBuffer buffer = new StringBuffer();
        int i = 0;
        for(String s : parts){
            String color = COLORS[i];
            buffer.append(color + "&l" + s);
            i++;
            if (i >= COLORS.length) i = 0;
        }
        return buffer.toString();
    }

}
