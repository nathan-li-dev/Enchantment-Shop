package com.monologica.enchantmentshop.shop;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class EnchantableType {
    private List<BuyableEnchantment> allowedEnchants;
    private Set<Material> allowedMaterials;

    public List<BuyableEnchantment> getEnchants() { return allowedEnchants; }

    public EnchantableType(List<BuyableEnchantment> allowedEnchants, Set<Material> allowedMaterials) {
        this.allowedEnchants = allowedEnchants;
        this.allowedMaterials = allowedMaterials;
    }

    public boolean allows(Material m) {
        return allowedMaterials.contains(m);
    }

    public void print() {
        Logger l = Bukkit.getLogger();
        l.info("ENCHANTABLE TYPE");
        l.info("Allowed materials:");
        for(Material m: allowedMaterials) {
            l.info(m.toString());
        }
        l.info("Allowed enchants");
        for(BuyableEnchantment be: allowedEnchants) {
            l.info(be.toString());
        }
        l.info("");
    }
}
