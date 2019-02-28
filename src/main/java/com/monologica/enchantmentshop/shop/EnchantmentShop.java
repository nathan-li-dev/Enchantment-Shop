package com.monologica.enchantmentshop.shop;

import com.monologica.enchantmentshop.EnchantmentShopPlugin;
import fr.minuskube.inv.SmartInventory;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnchantmentShop {

    SmartInventory inv;
    ItemStack item;
    Player client;

    public EnchantmentShop(Player client, ItemStack item, EnchantmentShopPlugin p) {
        inv = SmartInventory.builder()
                .provider(new ShopProvider(client, item, p))
                .size(1,9)
                .title(p.getShopGuiConfig().guiName())
                .build();

        this.client = client;
        this.item = item;
    }

    public void openToClient() {
        inv.open(client);
    }
}
