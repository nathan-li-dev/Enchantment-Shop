package com.monologica.enchantmentshop.shop;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EnchantableTypeList {
    private List<EnchantableType> enchantableTypes;

    public EnchantableTypeList(FileConfiguration yml) {

        enchantableTypes = new ArrayList<>();

        // Loop through the defined enchantableTypes
        Set<String> classSection = yml.getConfigurationSection("classes").getKeys(false);
        for(String enchantableTypeName: classSection) {

            // Get allowed materials for an enchantableType
            Set<Material> allowedMaterials = new HashSet<>();
            List<String> materialSection = yml.getStringList("classes." + enchantableTypeName + ".allowed_materials");
            for(String materialName: materialSection) {
                Material m = Material.valueOf(materialName);
                allowedMaterials.add(m);
            }

            // Get allowed enchantments for an enchantableType
            List<BuyableEnchantment> buyableEnchantments = new ArrayList<>();
            // Loop through the defined enchants
            Set<String> enchantmentSection = yml.getConfigurationSection("classes." + enchantableTypeName + ".enchants").getKeys(false);
            for(String enchantmentName: enchantmentSection) {
                Enchantment enchantment = EnchantmentWrapper.getByKey(NamespacedKey.minecraft(enchantmentName));

                String enchantmentPath = "classes." + enchantableTypeName + ".enchants." + enchantmentName;

                Cost.Modifier costMod = Cost.Modifier.valueOf(yml.getString(enchantmentPath + ".cost.type"));
                double initialCost    = yml.getDouble(enchantmentPath + ".cost.initial");
                double modifiervalue  = yml.getDouble(enchantmentPath + ".cost.modifier");

                int maxLevel = yml.getInt( enchantmentPath + ".max_level");
                Cost cost    = new Cost(initialCost, modifiervalue, costMod);

                buyableEnchantments.add(new BuyableEnchantment(enchantment, maxLevel, cost));
            }

            enchantableTypes.add(new EnchantableType(buyableEnchantments, allowedMaterials));
        }
    }

    public List<EnchantableType> getTypes(Material m) {
        List<EnchantableType> l = new ArrayList(2);

        for(EnchantableType type: enchantableTypes) {
            if(type.allows(m))
                l.add(type);
        }

        return l;
    }

    public void print() {
        for(EnchantableType et: enchantableTypes) {
            et.print();
        }
    }


}
