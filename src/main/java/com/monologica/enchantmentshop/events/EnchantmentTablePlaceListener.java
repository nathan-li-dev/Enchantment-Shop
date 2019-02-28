package com.monologica.enchantmentshop.events;

import com.monologica.enchantmentshop.EnchantmentShopPlugin;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.Material;

public class EnchantmentTablePlaceListener implements Listener {
    private final EnchantmentShopPlugin plugin;

    public EnchantmentTablePlaceListener(EnchantmentShopPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (!e.getBlock().getType().equals(Material.ENCHANTING_TABLE))
            return;

        String formattedMessage = ChatColor.translateAlternateColorCodes('&', plugin.getMessageConfig().onPlace());
        e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(formattedMessage));
        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 100, 1);
    }
}
