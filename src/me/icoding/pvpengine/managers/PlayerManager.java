package me.icoding.pvpengine.managers;

import me.icoding.pvpengine.PvpEngine;
import me.icoding.pvpengine.utils.ColorUtil;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class PlayerManager {

    private File file;
    private YamlConfiguration config;

    public PlayerManager() {
        file = new File(PvpEngine.INSTANCE.getDataFolder(), "player-data.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException ex) { ex.printStackTrace(); }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public void registerPlayer(Player player) {
        config.set("players." + player.getUniqueId().toString() + ".name", player.getName());
        config.set("players." + player.getUniqueId().toString() + ".rank", PvpEngine.rankManager.getDefaultRank());
        config.set("players." + player.getUniqueId().toString() + ".level", 0);
        config.set("players." + player.getUniqueId().toString() + ".kills", 0);
        config.set("players." + player.getUniqueId().toString() + ".deaths", 0);
        config.set("players." + player.getUniqueId().toString() + ".coins", 0);
        config.set("players." + player.getUniqueId().toString() + ".spy", false);
        config.set("players." + player.getUniqueId().toString() + ".vanish", false);
        config.set("players." + player.getUniqueId().toString() + ".nicked", false);
        config.set("players." + player.getUniqueId().toString() + ".latest-msg", "");

        saveConfig();
    }

    public boolean playerExists(Player player) { return config.contains("players." + player.getUniqueId().toString()); }

    public String getName(Player player) { return config.getString("players." + player.getUniqueId().toString() + ".name"); }
    public String getRank(Player player) { return config.getString("players." + player.getUniqueId().toString() + ".rank"); }
    public int getLevel(Player player) { return config.getInt("players." + player.getUniqueId().toString() + ".level"); }
    public int getKills(Player player) { return config.getInt("players." + player.getUniqueId().toString() + ".kills"); }
    public int getDeaths(Player player) { return config.getInt("players." + player.getUniqueId().toString() + ".deaths"); }
    public int getCoins(Player player) { return config.getInt("players." + player.getUniqueId().toString() + ".coins"); }
    public boolean getSpy(Player player) { return config.getBoolean("players." + player.getUniqueId().toString() + ".spy"); }
    public boolean getVanish(Player player) { return config.getBoolean("players." + player.getUniqueId().toString() + ".vanish"); }
    public boolean getNicked(Player player) { return config.getBoolean("players." + player.getUniqueId().toString() + ".nicked"); }
    public boolean getLatest(Player player) { return config.getBoolean("players." + player.getUniqueId().toString() + ".latest-msg"); }

    public String getLevelColor(int level) {
        if (level < 25) {
            return "" + ChatColor.DARK_GRAY + level;
        } else if (level >= 25 && level < 50) {
            return "" + ChatColor.GRAY + level;
        } else if (level >= 50 && level < 75) {
            return "" + ChatColor.AQUA + level;
        } else if (level >= 75 && level < 100) {
            return "" + ChatColor.BLUE + level;
        } else if (level >= 100 && level < 1000) {
            return "" + ChatColor.GOLD + "" + ChatColor.BOLD + level;
        } else {
            return "" + ColorUtil.rainbowify("" + level);
        }
    }

    public void setName(Player player, String name) {
        config.set("players." + player.getUniqueId().toString() + ".name", name);
        saveConfig();
    }

    public void setRank(Player player, String rank) {
        config.set("players." + player.getUniqueId().toString() + ".rank", rank);
        saveConfig();
    }

    public void setLevel(Player player, int level) {
        config.set("players." + player.getUniqueId().toString() + ".level", level);
        saveConfig();
    }

    public void setCoins(Player player, int coins) {
        config.set("players." + player.getUniqueId().toString() + ".coins", coins);
        saveConfig();
    }

    public void addCoins(Player player, int coins) {
        config.set("players." + player.getUniqueId().toString() + ".coins", config.getInt("players." + player.getUniqueId().toString() + ".coins") + coins);
        saveConfig();
    }

    public void gainLevel(Player player) {
        config.set("players." + player.getUniqueId().toString() + ".level", config.getInt("players." + player.getUniqueId().toString() + ".level") + 1);
        saveConfig();
    }

    public void setKills(Player player, int kills) {
        config.set("players." + player.getUniqueId().toString() + ".kills", kills);
        saveConfig();
    }

    public void addKills(Player player, int kills) {
        config.set("players." + player.getUniqueId().toString() + ".kills", config.getInt("players." + player.getUniqueId().toString() + ".kills") + kills);
        saveConfig();
    }

    public void addKill(Player player) {
        config.set("players." + player.getUniqueId().toString() + ".kills", config.getInt("players." + player.getUniqueId().toString() + ".kills") + 1);
        saveConfig();
    }

    public void setDeaths(Player player, int deaths) {
        config.set("players." + player.getUniqueId().toString() + ".deaths", deaths);
        saveConfig();
    }

    public void setSpy(Player player, boolean spy) {
        config.set("players." + player.getUniqueId().toString() + ".spy", spy);
        saveConfig();
    }

    public void setVanish(Player player, boolean vanish) {
        config.set("players." + player.getUniqueId().toString() + ".vanish", vanish);
        saveConfig();
    }

    public void setNicked(Player player, boolean nicked) {
        config.set("players." + player.getUniqueId().toString() + ".nicked", nicked);
        saveConfig();
    }

    public void setLatest(Player player, Player msged) {
        config.set("players." + player.getUniqueId().toString() + ".latest-msg", msged);
        saveConfig();
    }

    public void addDeaths(Player player, int deaths) {
        config.set("players." + player.getUniqueId().toString() + ".deaths", config.getInt("players." + player.getUniqueId().toString() + ".deaths") + deaths);
        saveConfig();
    }

    public void addDeath(Player player) {
        config.set("players." + player.getUniqueId().toString() + ".deaths", config.getInt("players." + player.getUniqueId().toString() + ".deaths") + 1);
        saveConfig();
    }

    private void saveConfig() {
        try { config.save(file); }
        catch (IOException e) { e.printStackTrace(); }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
    }
}
