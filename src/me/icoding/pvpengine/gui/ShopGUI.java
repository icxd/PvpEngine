package me.icoding.pvpengine.gui;

import me.icoding.pvpengine.PvpEngine;
import me.icoding.pvpengine.utils.ConfigUtil;
import me.icoding.pvpengine.utils.GuiUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;

public class ShopGUI extends GUI implements Listener {
    public ShopGUI() {
        super("Shop", Size.SIZE_54);

        ringInventory(GuiUtil.createGlassItem(Material.STAINED_GLASS_PANE, 15, " "));

        addItem(createShopItem(Material.DIAMOND_HELMET, "Diamond Helmet", 15000));
        addItem(createShopItem(Material.DIAMOND_CHESTPLATE, "Diamond Chestplate", 25000));
        addItem(createShopItem(Material.DIAMOND_LEGGINGS, "Diamond Leggings", 20000));
        addItem(createShopItem(Material.DIAMOND_BOOTS, "Diamond Boots", 10000));
        addItem(createShopItem(Material.DIAMOND_SWORD, "Diamond Sword", 50000));
        addItem(createShopItem(Material.GOLDEN_APPLE, "Golden Apple", 75000));
        addItem(createShopItem(Material.ENDER_PEARL, "Ender Pearl", 250000));
        addItem(createShopItem(Material.FISHING_ROD, "Fishing Rod", 100000));
        addItem(createShopItem(Material.SNOW_BALL, "Snow Balls", 5000));
        addItem(createShopItem(Material.GOLDEN_APPLE, "Enchanted Golden Apple", 500000, 1));
        addItem(createShopItem(Material.POTION, "Instant Health Potion", 1000, 16421));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        try {
            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getClickedInventory();

            if (!inventory.getTitle().equals(getTitle())) return;

            event.setCancelled(true);

            if (event.getAction().equals(InventoryAction.PICKUP_HALF)) {
                switch (event.getCurrentItem().getType()) {
                    case GOLDEN_APPLE:
                        if (event.getCurrentItem().getDurability() == 1)
                            new ShopExtendedGUI(Material.GOLDEN_APPLE, "Enchanted Golden Apple", 500000, 1).open(player);
                        else
                            new ShopExtendedGUI(Material.GOLDEN_APPLE, "Golden Apple", 75000).open(player);
                        break;
                    case ENDER_PEARL:
                        new ShopExtendedGUI(Material.ENDER_PEARL, "Ender Pearl", 250000).open(player);
                        break;
                    case SNOW_BALL:
                        new ShopExtendedGUI(Material.SNOW_BALL, "Snow Balls", 5000).open(player);
                        break;
                    default:
                        break;
                }
            } else {
                switch (event.getCurrentItem().getType()) {
                    case DIAMOND_HELMET:
                        purchaseItem(player, Material.DIAMOND_HELMET, event.getCurrentItem().getAmount(), 15000);
                        break;
                    case DIAMOND_CHESTPLATE:
                        purchaseItem(player, Material.DIAMOND_CHESTPLATE, event.getCurrentItem().getAmount(), 25000);
                        break;
                    case DIAMOND_LEGGINGS:
                        purchaseItem(player, Material.DIAMOND_LEGGINGS, event.getCurrentItem().getAmount(), 20000);
                        break;
                    case DIAMOND_BOOTS:
                        purchaseItem(player, Material.DIAMOND_BOOTS, event.getCurrentItem().getAmount(), 10000);
                        break;
                    case DIAMOND_SWORD:
                        purchaseItem(player, Material.DIAMOND_SWORD, event.getCurrentItem().getAmount(), 50000);
                        break;
                    case GOLDEN_APPLE:
                        if (event.getCurrentItem().getDurability() != 1)
                            purchaseItem(player, Material.GOLDEN_APPLE, event.getCurrentItem().getAmount(), 75000);
                        else
                            purchaseItem(player, Material.GOLDEN_APPLE, event.getCurrentItem().getAmount(), 500000, 1);
                        break;
                    case ENDER_PEARL:
                        purchaseItem(player, Material.ENDER_PEARL, event.getCurrentItem().getAmount(), 250000);
                        break;
                    case FISHING_ROD:
                        purchaseItem(player, Material.FISHING_ROD, event.getCurrentItem().getAmount(), 100000);
                        break;
                    case SNOW_BALL:
                        purchaseItem(player, Material.SNOW_BALL, event.getCurrentItem().getAmount(), 5000);
                        break;
                    case POTION:
                        if (event.getCurrentItem().getDurability() == 16421)
                            purchaseItem(player, Material.POTION, event.getCurrentItem().getAmount(), 1000, 16421);
                        break;
                    default:
                        break;
                }
            }
        } catch (NullPointerException ignored) {}
    }

    private ItemStack createShopItem(Material material, String name, int price) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        return GuiUtil.createItemWithLore(
                material,
                "&b" + name,
                1,
                "&7Cost: &6" + format.format(price),
                "",
                "&9Click to purchase!"
        );
    }

    private ItemStack createShopItem(Material material, String name, int price, int data) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        return GuiUtil.createItemWithLore(
                material,
                "&b" + name,
                data,
                1,
                "&7Cost: &6" + format.format(price),
                "",
                "&9Click to purchase!"
        );
    }

    private void purchaseItem(Player player, Material material, int count, int price) {
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

    private void purchaseItem(Player player, Material material, int count, int price, int data) {
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
}
