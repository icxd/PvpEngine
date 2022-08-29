package me.icoding.pvpengine.managers;

import me.icoding.pvpengine.PvpEngine;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class KitsManager {

    private File file;
    private YamlConfiguration config;

    public KitsManager() {
        file = new File(PvpEngine.INSTANCE.getDataFolder(), "kits.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException ex) { ex.printStackTrace(); }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public boolean kitExists(String name) {
        for (String kit : config.getKeys(false))
            if (kit.equals(name))
                return true;
        return false;
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
    }
}
