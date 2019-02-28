package com.monologica.enchantmentshop.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

public class EnchantPrepareListener implements Listener {
    @EventHandler
    public void onEnchantPrepare(PrepareItemEnchantEvent e) {
        Bukkit.broadcastMessage(e.getItem().toString());
        e.setCancelled(true);
    }
}
