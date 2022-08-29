package me.icoding.pvpengine.gui;

import me.icoding.pvpengine.utils.GuiUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class TrashGUI extends GUI implements Listener {

    public TrashGUI() {
        super("Trashcan", Size.SIZE_36);

        ringInventory(GuiUtil.createGlassItem(Material.STAINED_GLASS_PANE, 15, " "));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        try {
            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getClickedInventory();

            if (!inventory.getTitle().equals(getTitle())) return;

            if (event.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
                event.setCancelled(true);
            } else {
                event.setCancelled(false);
            }
        } catch(NullPointerException ignored) {}
    }
}
