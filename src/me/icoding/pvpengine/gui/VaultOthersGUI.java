package me.icoding.pvpengine.gui;

import me.icoding.pvpengine.PvpEngine;
import me.icoding.pvpengine.managers.VaultManager;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class VaultOthersGUI extends GUI implements Listener {
    public VaultOthersGUI(Player player) {
        super(player.getName() + "'s Vault", Size.SIZE_36);

        VaultManager manager = PvpEngine.vaultManager;

        for (String in : manager.getVault(player)) {
            String[] s = in.split(" ");

            Material material = Material.matchMaterial(s[0]);
            short data = (short) Integer.parseInt(s[1]);
            int amount = Integer.parseInt(s[2]);

            ItemStack item = new ItemStack(material, amount, data);

            try {
                String[] enchantments = s[3].split(",");
                for (String value : enchantments) {
                    Enchantment enchantment = Enchantment.getByName(value.split("-")[0].toUpperCase());
                    int level = Integer.parseInt(value.split("-")[1]);

                    item.addUnsafeEnchantment(enchantment, level);
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }

            addItem(item);
        }
    }

    public VaultOthersGUI() {
        super("Vault", Size.SIZE_36);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (!event.getInventory().getTitle().equals(getTitle())) return;

        event.setCancelled(true);
    }
}
