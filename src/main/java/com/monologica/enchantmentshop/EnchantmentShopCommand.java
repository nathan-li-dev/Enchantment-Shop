package com.monologica.enchantmentshop;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EnchantmentShopCommand implements CommandExecutor {

    private final EnchantmentShopPlugin plugin;

    public EnchantmentShopCommand(EnchantmentShopPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command command, String s, String[] args) {
        if(cs.hasPermission("enchantmentshop.reload")) {
            plugin.reloadConfig();
            cs.sendMessage("Reloaded enchantment shop plugin.");
        } else {
            cs.sendMessage("ó‿ó");
        }
        return true;
    }
}
