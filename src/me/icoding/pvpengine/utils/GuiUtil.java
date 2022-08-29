package me.icoding.pvpengine.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.UUID;

public class GuiUtil {

    public static ItemStack createGlassItem(Material material, int color, String name) {
        ItemStack item = new ItemStack(material, 1, (short) color);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ColorUtil.c(name));

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createGlassItem(Material material, int color, String name, int amount) {
        ItemStack item = new ItemStack(material, amount, (short) color);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ColorUtil.c(name));

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItemWithLore(Material material, String name, int amount, String... lore) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_DESTROYS);
        meta.setDisplayName(ColorUtil.c(name));
        meta.setLore(ColorUtil.c(lore));

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItemWithLore(Material material, String name, int data, int amount, String... lore) {
        ItemStack item = new ItemStack(material, amount, (short) data);
        ItemMeta meta = item.getItemMeta();

        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_DESTROYS);
        meta.setDisplayName(ColorUtil.c(name));
        meta.setLore(ColorUtil.c(lore));

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItem(Material material, String name, int amount) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_DESTROYS);
        meta.setDisplayName(ColorUtil.c(name));

        item.setItemMeta(meta);
        return item;
    }

    public static void setSkullItem(String url, ItemStack stack) {
        if (url.isEmpty())
            return;
        SkullMeta headMeta = (SkullMeta) stack.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.getEncoder().encode((String.format("{textures:{SKIN:{url:\"%s\"}}}", url)).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }

        stack.setItemMeta(headMeta);
    }

    public static Property getSkinValueAndSignatureFromURL(String url) {
        if (url.isEmpty())
            return null;
        byte[] encodedData = Base64.getEncoder().encode((String.format("{textures:{SKIN:{url:\"%s\"}}}", url)).getBytes());
        return new Property("textures", new String(encodedData));
    }

}
