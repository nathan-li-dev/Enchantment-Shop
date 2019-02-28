package com.monologica.enchantmentshop.utils;

import com.monologica.enchantmentshop.shop.TextFormatter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Items {
    public static ItemStack build(String name, List<String> lore, Material m, TextFormatter tf) {
        ItemStack is = new ItemStack(m);
        ItemMeta im = is.getItemMeta();
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.setDisplayName(tf.format(name));
        List<String> formattedLore = new ArrayList<>(lore.size());
        for(String s: lore) {
            formattedLore.add(tf.format(s));
        }
        im.setLore(formattedLore);
        is.setItemMeta(im);
        return is;
    }

    public static ItemStack build(String name, List<String> lore, Material m) {
        ItemStack is = new ItemStack(m);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(basicFormat(name));
        List<String> formattedLore = new ArrayList<>(lore.size());
        for(String s: lore) {
            formattedLore.add(basicFormat(s));
        }
        im.setLore(formattedLore);
        is.setItemMeta(im);
        return is;
    }

    private static String basicFormat(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }


}
