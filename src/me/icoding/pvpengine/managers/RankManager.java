package me.icoding.pvpengine.managers;

import me.icoding.pvpengine.PvpEngine;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RankManager {

    private File file;
    private YamlConfiguration config;

    public RankManager() {
        file = new File(PvpEngine.INSTANCE.getDataFolder(), "ranks.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException ex) { ex.printStackTrace(); }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public String getDefaultRank() {
        for (String rank : config.getKeys(false))
            if (config.getBoolean(rank + ".default"))
                return rank;
        return null;
    }
    public String getPrefix(String rankName) {
        for (String rank : config.getKeys(false))
            if (rank.equals(rankName))
                return config.getString(rank + ".prefix");
        return null;
    }
    public String getNameColor(String rankName) {
        for (String rank : config.getKeys(false))
            if (rank.equals(rankName))
                return config.getString(rank + ".name-color");
        return null;
    }
    public boolean rankExists(String rankName) {
        for (String rank : config.getKeys(false))
            if (rank.equals(rankName))
                return true;
        return false;
    }
    public List<String> getPermissions(String rankName) {
        for (String rank : config.getKeys(false))
            if (rank.equals(rankName))
                return config.getStringList(rank + ".permissions");
        return null;
    }
    public int getWeight(String rankName) {
        for (String rank : config.getKeys(false))
            if (rank.equals(rankName))
                return config.getInt(rank + ".weight");
        return 0;
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
    }
}
