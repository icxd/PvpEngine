package me.icoding.pvpengine.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.PrimitiveIterator;

public class GUI {

    private Inventory inventory;

    private String title;
    private Size size;
    private InventoryType type;

    public GUI(String title, Size size) {
        this.title = title;
        this.size = size;

        inventory = Bukkit.createInventory(null, size.getSize(), title);
    }

    public GUI(String title, InventoryType type) {
        this.title = title;
        this.type = type;

        inventory = Bukkit.createInventory(null, type, title);
    }

    public void setItem(int slot, ItemStack item) {
        inventory.setItem(slot, item);
    }

    public void setItem(int slot, Material material) {
        inventory.setItem(slot, new ItemStack(material));
    }

    public void fill(ItemStack item) {
        if (size == null) return;
        for (int i = 0; i < size.getSize(); i++) {
            setItem(i, item);
        }
    }

    public void fill(Material material) {
        if (size == null) return;
        for (int i = 0; i < size.getSize(); i++) {
            setItem(i, material);
        }
    }

    public void addItemToSlots(ItemStack stack, int... slots){
        for(PrimitiveIterator.OfInt it = java.util.Arrays.stream(slots).iterator(); it.hasNext();){
            int slot = it.next();
            inventory.setItem(slot,stack);
        }
    }

    public void addItem(ItemStack item) {
        inventory.addItem(item);
    }

    public void ringInventory(ItemStack item) {
        if (size == null) return;

        switch (size) {
            case SIZE_9:
            case SIZE_18:
                break;
            case SIZE_27:
                fill(item);
                for (int i = 10; i <= size.getSize() - 11; i++) {
                    setItem(i, Material.AIR);
                }
                break;
            case SIZE_36:
                fill(item);
                for (int i = 10; i <= size.getSize() - 11; i++) {
                    setItem(i, Material.AIR);
                }
                addItemToSlots(item, 17, 18);
                break;
            case SIZE_45:
                fill(item);
                for (int i = 10; i <= size.getSize() - 11; i++) {
                    setItem(i, Material.AIR);
                }
                addItemToSlots(item, 17, 18, 26, 27);
                break;
            case SIZE_54:
                fill(item);
                for (int i = 10; i <= size.getSize() - 11; i++) {
                    setItem(i, Material.AIR);
                }
                addItemToSlots(item, 17, 18, 26, 27, 35, 36);
                break;
            default:
                break;
        }
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }













    public String getTitle() {
        return title;
    }

    public InventoryType getType() {
        return type;
    }

    public Size getSize() {
        return size;
    }

    public Inventory getInventory() {
        return inventory;
    }

    enum Size {
        SIZE_9,
        SIZE_18,
        SIZE_27,
        SIZE_36,
        SIZE_45,
        SIZE_54,
        ;
        public int getSize() {
            return Integer.parseInt(name().replace("SIZE_", ""));
        }
    }

}
