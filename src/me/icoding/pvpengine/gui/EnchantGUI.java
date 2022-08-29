package me.icoding.pvpengine.gui;

import me.icoding.pvpengine.PvpEngine;
import me.icoding.pvpengine.utils.ConfigUtil;
import me.icoding.pvpengine.utils.GuiUtil;
import me.icoding.pvpengine.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.text.NumberFormat;

public class EnchantGUI extends GUI implements Listener {
    public EnchantGUI() {
        super("Enchant Item", Size.SIZE_36);

        fill(GuiUtil.createGlassItem(Material.STAINED_GLASS_PANE, 15, " "));

        NumberFormat format = NumberFormat.getCurrencyInstance();
        setItem(11, GuiUtil.createItemWithLore(
                Material.ENCHANTED_BOOK,
                "&bSharpness",
                1,
                "&7Click to add &b1 &7Sharpness",
                "&7level to your weapon.",
                "",
                "&7Cost: &6" + format.format(10000)
        ));
        setItem(13, GuiUtil.createItemWithLore(
                Material.ENCHANTED_BOOK,
                "&bPower",
                1,
                "&7Click to add &b1 &7Power",
                "&7level to your bow.",
                "",
                "&7Cost: &6" + format.format(15000)
        ));
        setItem(15, GuiUtil.createItemWithLore(
                Material.ENCHANTED_BOOK,
                "&bProtection",
                1,
                "&7Click to add &b1 &7Protection",
                "&7level to your armor.",
                "",
                "&7Cost: &6" + format.format(7500)
        ));
        setItem(22, GuiUtil.createItemWithLore(
                Material.ENCHANTED_BOOK,
                "&bInfinity",
                1,
                "&7Click to add &b1 &7Infinity",
                "&7level to your bow.",
                "",
                "&7Cost: &6" + format.format(20000)
        ));
        setItem(20, GuiUtil.createItemWithLore(
                Material.ENCHANTED_BOOK,
                "&bFire Aspect",
                1,
                "&7Click to add &b1 &7Fire Aspect",
                "&7level to your weapon.",
                "",
                "&7Cost: &6" + format.format(50000)
        ));
        setItem(24, GuiUtil.createItemWithLore(
                Material.ENCHANTED_BOOK,
                "&bThorns",
                1,
                "&7Click to add &b1 &7Thorns",
                "&7level to your armor.",
                "",
                "&7Cost: &6" + format.format(75000)
        ));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        try {
            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getClickedInventory();

            if (!inventory.getTitle().equals(getTitle())) return;

            event.setCancelled(true);

            if (event.getSlot() == 11) {
                purchaseEnchantment(player, Enchantment.DAMAGE_ALL, ItemUtil.SWORDS, 5, 10000);
            } else if (event.getSlot() == 13) {
                purchaseEnchantment(player, Enchantment.ARROW_DAMAGE, ItemUtil.BOWS, 5, 15000);
            } else if (event.getSlot() == 15) {
                purchaseEnchantment(player, Enchantment.PROTECTION_ENVIRONMENTAL, ItemUtil.ARMORS, 5, 7500);
            } else if (event.getSlot() == 22) {
                purchaseEnchantment(player, Enchantment.ARROW_INFINITE, ItemUtil.BOWS, 1, 20000);
            } else if (event.getSlot() == 20) {
                purchaseEnchantment(player, Enchantment.FIRE_ASPECT, ItemUtil.SWORDS, 1, 50000);
            } else if (event.getSlot() == 24) {
                purchaseEnchantment(player, Enchantment.THORNS, ItemUtil.ARMORS, 3, 75000);
            }
        } catch(NullPointerException ignored) {}
    }

    private void purchaseEnchantment(Player player, Enchantment type, Material[] items, int maxLevel, int price) {
        for (Material material : items) {
            if (player.getInventory().getItemInHand().getType() != material)
                continue;
            else {
                if (PvpEngine.playerManager.getCoins(player) < price) {
                    player.sendMessage(ConfigUtil.getFormattedString("messages.not-enough-coinage"));
                    return;
                }

                int level = player.getInventory().getItemInHand().getEnchantmentLevel(type) + 1;
                if (level > maxLevel) {
                    player.sendMessage(String.format(ConfigUtil.getFormattedString("messages.too-high-enchant-level"), maxLevel));
                    return;
                }
                player.getInventory().getItemInHand().addUnsafeEnchantment(type, level);
                PvpEngine.playerManager.setCoins(player, PvpEngine.playerManager.getCoins(player) - price);
                NumberFormat format = NumberFormat.getCurrencyInstance();
                player.sendMessage(
                        String.format(ConfigUtil.getFormattedString("messages.enchanted-item"),
                                player.getInventory().getItemInHand().getType().name(),
                                type.getName(),
                                format.format(price))
                );
                return;
            }
        }
    }
}
