package me.icoding.pvpengine.gui;

import me.icoding.pvpengine.PvpEngine;
import me.icoding.pvpengine.utils.ConfigUtil;
import me.icoding.pvpengine.utils.GuiUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;

public class ShopExtendedGUI extends GUI implements Listener {

    public ShopExtendedGUI(Material material, String name, int price) {
        super("Shop", InventoryType.HOPPER);

        NumberFormat format = NumberFormat.getCurrencyInstance();

        addItem(createShopItem(material, name, price, 4));
        addItem(createShopItem(material, name, price, 8));
        addItem(createShopItem(material, name, price, 16));
        addItem(createShopItem(material, name, price, 32));
        addItem(createShopItem(material, name, price, 64));
    }

    public ShopExtendedGUI(Material material, String name, int price, int data) {
        super("Shop", InventoryType.HOPPER);

        addItem(createShopItem(material, name, price, 4, data));
        addItem(createShopItem(material, name, price, 8, data));
        addItem(createShopItem(material, name, price, 16, data));
        addItem(createShopItem(material, name, price, 32, data));
        addItem(createShopItem(material, name, price, 64, data));
    }

    public ShopExtendedGUI() {
        super("Shop", InventoryType.HOPPER);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        try {
            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getClickedInventory();

            if (!inventory.getTitle().equals(getTitle())) return;

            event.setCancelled(true);
        } catch (NullPointerException ignored) {}
    }

    private void purchaseItem(Player player, Material material, int price, int count) {
        if (PvpEngine.playerManager.getCoins(player) < price*count) {
            player.sendMessage(ConfigUtil.getFormattedString("messages.not-enough-coinage"));
            return;
        }

        if (player.getInventory().firstEmpty() != -1) {
            ItemStack item = new ItemStack(material, count);
            ItemMeta meta = item.getItemMeta();
            meta.spigot().setUnbreakable(true);
            item.setItemMeta(meta);
            player.getInventory().addItem(item);
            PvpEngine.playerManager.setCoins(player, PvpEngine.playerManager.getCoins(player) - price*count);
            NumberFormat format = NumberFormat.getCurrencyInstance();
            player.sendMessage(String.format(ConfigUtil.getFormattedString("messages.purchase-item"), material.name(), format.format(price*count)));
        } else {
            player.sendMessage(ConfigUtil.getFormattedString("messages.not-enough-room"));
        }
    }

    private void purchaseItem(Player player, Material material, int price, int count, int data) {
        if (PvpEngine.playerManager.getCoins(player) < price*count) {
            player.sendMessage(ConfigUtil.getFormattedString("messages.not-enough-coinage"));
            return;
        }

        if (player.getInventory().firstEmpty() != -1) {
            ItemStack item = new ItemStack(material, count, (short) data);
            ItemMeta meta = item.getItemMeta();
            meta.spigot().setUnbreakable(true);
            item.setItemMeta(meta);
            player.getInventory().addItem(item);
            PvpEngine.playerManager.setCoins(player, PvpEngine.playerManager.getCoins(player) - price*count);
            NumberFormat format = NumberFormat.getCurrencyInstance();
            player.sendMessage(String.format(ConfigUtil.getFormattedString("messages.purchase-item"), material.name(), format.format(price*count)));
        } else {
            player.sendMessage(ConfigUtil.getFormattedString("messages.not-enough-room"));
        }
    }

    private ItemStack createShopItem(Material material, String name, int price, int count) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        return GuiUtil.createItemWithLore(
                material,
                "&b" + name,
                count,
                "&7Cost: &6" + format.format(price*count),
                "",
                "&9Click to purchase!");
    }

    private ItemStack createShopItem(Material material, String name, int price, int count, int data) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        return GuiUtil.createItemWithLore(
                material,
                "&b" + name,
                data,
                count,
                "&7Cost: &6" + format.format(price*count),
                "",
                "&9Click to purchase!");
    }
}
