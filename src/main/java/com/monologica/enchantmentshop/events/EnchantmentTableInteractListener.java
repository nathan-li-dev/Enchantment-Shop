package com.monologica.enchantmentshop.events;

import com.monologica.enchantmentshop.EnchantmentShopPlugin;
import com.monologica.enchantmentshop.shop.EnchantmentShop;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.Material;

public class EnchantmentTableInteractListener implements Listener {

    private final EnchantmentShopPlugin plugin;

    public EnchantmentTableInteractListener(EnchantmentShopPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            return;
        if (!e.getClickedBlock().getType().equals(Material.ENCHANTING_TABLE))
            return;

        e.setCancelled(true);

        EnchantmentShop s = new EnchantmentShop(e.getPlayer(), e.getPlayer().getInventory().getItemInMainHand(), plugin);
        s.openToClient();

    }
}
