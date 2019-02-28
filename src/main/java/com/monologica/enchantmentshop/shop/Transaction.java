package com.monologica.enchantmentshop.shop;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static com.monologica.enchantmentshop.EnchantmentShopPlugin.economy;

public class Transaction {

    public enum Status {
        OK, NOT_ENOUGH_MONEY, MISSING_ITEM, MAX_LEVEL_ALREADY_REACHED
    }

    private ItemStack item;
    private BuyableEnchantment buyableEnchantment;
    private Player player;

    public Transaction(ItemStack is, BuyableEnchantment be, Player p) {
        this.item = is;
        this.buyableEnchantment = be;
        this.player = p;
    }

    public Status execute() {
        if(!buyableEnchantment.isBuyableFor(player, getCurrentLevel()))
            return Status.MAX_LEVEL_ALREADY_REACHED;

        if(!playerHasEnoughMoney())
            return Status.NOT_ENOUGH_MONEY;

        if (!playerHasItem())
            return Status.MISSING_ITEM;

        takeMoney();
        upgradeItem();

        return Status.OK;
    }

    public int getCurrentLevel()  {
        return item.getEnchantmentLevel(buyableEnchantment.getEnchantment());
    }

    public int getNextLevel() {
        if (buyableEnchantment.isBuyableFor(player, getCurrentLevel() +1))
            return getCurrentLevel() + 1;
        else
            return getCurrentLevel();

    }

    public boolean isBuyable() {
        return buyableEnchantment.isBuyableFor(player, getCurrentLevel());
    }

    public double calculateCost() {
        return buyableEnchantment.calculateCostFor(player, getCurrentLevel());
    }

    public BuyableEnchantment getBuyableEnchantment() {
        return buyableEnchantment;
    }

    public boolean playerHasEnoughMoney() {
        return economy.getBalance(player) >= calculateCost();
    }

    private boolean playerHasItem() {
        return player.getInventory().contains(item);
    }

    private void takeMoney() {
        economy.withdrawPlayer(player, calculateCost());
    }

    private void upgradeItem() {
        ItemMeta im = item.getItemMeta();
        im.addEnchant(buyableEnchantment.getEnchantment(), getCurrentLevel() + 1, true);
        item.setItemMeta(im);

    }
}
