package com.monologica.enchantmentshop.settings;

import com.monologica.enchantmentshop.shop.Transaction;
import com.monologica.enchantmentshop.shop.TransactionFormatter;
import com.monologica.enchantmentshop.utils.Items;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopGuiConfig {
    private Map<Enchantment, Material> enchantmentToMaterial;
    private ConfigurationSection guiConfig;

    public ShopGuiConfig(FileConfiguration yml) {
        enchantmentToMaterial = new HashMap<>();
        guiConfig = yml.getConfigurationSection("gui");

        for (String enchantmentName: yml.getConfigurationSection("gui.items").getKeys(false)) {
            Enchantment e = EnchantmentWrapper.getByKey(NamespacedKey.minecraft(enchantmentName));
            Material m = Material.valueOf(yml.getString("gui.items." + enchantmentName));
            enchantmentToMaterial.put(e, m);
        }
    }

    public Material getMaterial(Enchantment e) {
        return enchantmentToMaterial.get(e);
    }

    public ItemStack placeholderItem() {
        String name = guiConfig.getString("placeholder.name");
        Material material = Material.valueOf(guiConfig.getString("placeholder.material"));
        List<String> lore = guiConfig.getStringList("placeholder.lore");

        return Items.build(name, lore, material);
    }

    public ItemStack getItemForTransaction(Transaction t) {
        String name = guiConfig.getString("enchantment.name");
        List<String> lore = guiConfig.getStringList("enchantment.lore");
        Material material = getMaterial(t.getBuyableEnchantment().getEnchantment());

        ItemStack i = Items.build(name, lore, material, new TransactionFormatter(t));
        i.addUnsafeEnchantment(t.getBuyableEnchantment().getEnchantment(), t.getNextLevel());
        return i;
    }

    public ItemStack noneItem() {
        String name = guiConfig.getString("none.name");
        Material material = Material.valueOf(guiConfig.getString("none.material"));
        List<String> lore = guiConfig.getStringList("none.lore");

        return Items.build(name, lore, material);
    }

    public String guiName() {
        return ChatColor.translateAlternateColorCodes('&', guiConfig.getString("name"));
    }
}
