package com.monologica.enchantmentshop.shop;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class BuyableEnchantment {
    private Enchantment enchantment;
    private int maxLevel;
    private Cost cost;

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public BuyableEnchantment(Enchantment e, int m, Cost c) {
        this.enchantment = e;
        this.maxLevel = m;
        this.cost = c;
    }

    public boolean isBuyableFor(Player player, int currentLevel) {
        String enchantmentName = enchantment.getKey().toString().substring(enchantment.getKey().toString().indexOf(':') + 1);
        String nextLevelPermission = "enchantment.override." + enchantmentName + "." + currentLevel + 1;

        if (player.hasPermission(nextLevelPermission))
            return true;
        return currentLevel < maxLevel;

    }
    public double calculateCostFor(Player player, int currentLevel) {
        return isBuyableFor(player, currentLevel) ? cost.calculate(currentLevel) : 0;
    }

    @Override
    public String toString() {
        return String.format("Enchantment: %s | Max Level: %d | Cost: %s", enchantment.toString(), maxLevel, cost.toString());
    }
}
