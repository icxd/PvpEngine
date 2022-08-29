package me.icoding.pvpengine.utils;

import me.icoding.pvpengine.PvpEngine;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InventoryUtil {

    public static void clearInventory(Player player) {
        PlayerInventory inventory = player.getInventory();
        inventory.clear();
    }

    public static void giveKit(Player player, String kit) {
        YamlConfiguration config = PvpEngine.kitsManager.getConfig();
        if (config.contains(kit)) {
            PlayerInventory inventory = player.getInventory();
            ConfigurationSection section = config.getConfigurationSection(kit);
            if (section.contains("items")) {
                for (String items : section.getStringList("items")) {
                    List<String> itemIDs = new ArrayList<>();
                    itemIDs.addAll(Arrays.asList(items.split(" ")));

                    Material material = Material.matchMaterial(itemIDs.get(0).toUpperCase());
                    short data = (short) Integer.parseInt(itemIDs.get(1));
                    int amount = Integer.parseInt(itemIDs.get(2));
                    ItemStack item = new ItemStack(material, amount, data);
                    ItemMeta meta = item.getItemMeta();
                    meta.spigot().setUnbreakable(true);
                    item.setItemMeta(meta);
                    inventory.addItem(item);
                }
            }
            if (section.contains("armor")) {
                for (String items : section.getStringList("armor")) {
                    List<String> itemIDs = new ArrayList<>();
                    itemIDs.addAll(Arrays.asList(items.split(" ")));

                    Material material = Material.matchMaterial(itemIDs.get(0).toUpperCase());
                    short data = (short) Integer.parseInt(itemIDs.get(1));
                    int amount = Integer.parseInt(itemIDs.get(2));
                    ItemStack item = new ItemStack(material, amount, data);
                    ItemMeta meta = item.getItemMeta();
                    meta.spigot().setUnbreakable(true);
                    item.setItemMeta(meta);

                    if (inventory.getHelmet() == null)
                        inventory.setHelmet(item);
                    else if (inventory.getChestplate() == null)
                        inventory.setChestplate(item);
                    else if (inventory.getLeggings() == null)
                        inventory.setLeggings(item);
                    else
                        inventory.setBoots(item);
                }
            }
        }
    }

}
