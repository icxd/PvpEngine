package me.icoding.pvpengine.commands;

import me.icoding.pvpengine.PvpEngine;
import me.icoding.pvpengine.utils.ConfigUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KitCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                YamlConfiguration config = PvpEngine.kitsManager.getConfig();
                if (config.contains(args[0])) {
                    String kit = args[0];
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
                    player.sendMessage(String.format(ConfigUtil.getFormattedString("messages.kit-given"), kit.toUpperCase()));
                } else {
                    player.sendMessage(ConfigUtil.getFormattedString("messages.kit-not-found"));
                }
            } else {
                player.sendMessage(ConfigUtil.getFormattedString("messages.no-args"));
            }
        } else {
            sender.sendMessage(ConfigUtil.getFormattedString("messages.executed-by-console"));
        }

        return false;
    }
}
