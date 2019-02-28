package com.monologica.enchantmentshop;

import com.monologica.enchantmentshop.events.EnchantPrepareListener;
import com.monologica.enchantmentshop.events.EnchantmentTableInteractListener;
import com.monologica.enchantmentshop.events.EnchantmentTablePlaceListener;
import com.monologica.enchantmentshop.settings.MessageConfig;
import com.monologica.enchantmentshop.settings.ShopGuiConfig;
import com.monologica.enchantmentshop.shop.EnchantableTypeList;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class EnchantmentShopPlugin extends JavaPlugin {

    public static Economy economy;

    private EnchantableTypeList enchantableTypes;
    private ShopGuiConfig shopGuiConfig;
    private MessageConfig messageConfig;

    public EnchantableTypeList getEnchantableTypes() { return enchantableTypes; }
    public ShopGuiConfig getShopGuiConfig() { return shopGuiConfig; }
    public MessageConfig getMessageConfig() { return messageConfig; }


    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        setupEconomy();
        setupEvents();
        setupCommands();

        reloadConfig();

        //enchantableTypes.print();
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        this.enchantableTypes = new EnchantableTypeList(this.getConfig());
        this.shopGuiConfig = new ShopGuiConfig(this.getConfig());
        this.messageConfig = new MessageConfig(this.getConfig());
    }

    public void setupCommands() {
        getCommand("enchantmentshop").setExecutor(new EnchantmentShopCommand(this));
    }

    public void setupEvents() {
        getServer().getPluginManager().registerEvents(new EnchantPrepareListener(), this);
        getServer().getPluginManager().registerEvents(new EnchantmentTableInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new EnchantmentTablePlaceListener(this), this);
    }

    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        return (economy != null);
    }
}
