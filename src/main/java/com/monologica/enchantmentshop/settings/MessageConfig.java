package com.monologica.enchantmentshop.settings;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class MessageConfig {

    private ConfigurationSection yml;

    public MessageConfig(FileConfiguration yml) {
        this.yml = yml.getConfigurationSection("messages");
    }

    public String notEnoughMoney() {
        return yml.getString("not_enough_money");
    }

    public String alreadyMaxLevel() {
        return yml.getString("already_max_level");
    }

    public String missingItem() {
        return yml.getString("missing_item");
    }

    public String onPlace() {
        return yml.getString("on_place");
    }
}
