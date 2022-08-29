package me.icoding.pvpengine.managers;

import me.icoding.pvpengine.PvpEngine;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class VaultManager {

    private File file;
    private YamlConfiguration config;

    public VaultManager() {
        file = new File(PvpEngine.INSTANCE.getDataFolder(), "vaults.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException ex) { ex.printStackTrace(); }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public void register(Player player) {
        if (!playerExists(player))
            config.set("vaults." + player.getUniqueId(), "");
        saveConfig();
    }

    public boolean playerExists(Player player) {
        return config.contains("vaults." + player.getUniqueId());
    }

    public List<String> getVault(Player player) {
        return config.getStringList("vaults." + player.getUniqueId());
    }

    public void storeItem(Player player, ItemStack item, short data, int count) {
        String format = "%s %s %s %s";

        StringBuilder builder = new StringBuilder();
        for (Enchantment enchantment : item.getEnchantments().keySet())
            builder.append(String.format("%s-%s,",
                    enchantment.getName().toUpperCase(),
                    item.getEnchantmentLevel(enchantment)));

        List<String> brr = getVault(player);
        brr.add(String.format(format, item.getType(), data, count, builder.toString()));
        config.set("vaults." + player.getUniqueId(), brr);


        saveConfig();
    }

    public void removePlayer(Player player) {
        config.set("vaults." + player.getUniqueId(), "");
        saveConfig();
    }

    private void saveConfig() {
        try { config.save(file); }
        catch (IOException e) { e.printStackTrace(); }
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
    }

}
