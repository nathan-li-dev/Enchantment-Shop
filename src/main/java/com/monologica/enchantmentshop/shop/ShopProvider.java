package com.monologica.enchantmentshop.shop;

import com.monologica.enchantmentshop.*;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ShopProvider implements InventoryProvider {

    private final EnchantmentShopPlugin plugin;
    Player player;
    ItemStack item;

    public ShopProvider(Player p, ItemStack item, EnchantmentShopPlugin plugin) {
        this.player = p;
        this.item = item;
        this.plugin = plugin;
    }

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        int currentSlot = -1;

        inventoryContents.set(0,++currentSlot, ClickableItem.empty(item));
        inventoryContents.set(0,++currentSlot, ClickableItem.empty(plugin.getShopGuiConfig().placeholderItem()));

        List<EnchantableType> types = plugin.getEnchantableTypes().getTypes(item.getType());

        if(types.isEmpty())
            inventoryContents.fillRect(0,2,0,8, ClickableItem.empty(plugin.getShopGuiConfig().noneItem()));

        for(EnchantableType type: types) {
            for(BuyableEnchantment buyableEnchantment : type.getEnchants()) {

                Transaction transaction = new Transaction(item, buyableEnchantment, player);
                ItemStack transactionItem = plugin.getShopGuiConfig().getItemForTransaction(transaction);

                inventoryContents.set(0, ++currentSlot, ClickableItem.of(transactionItem, e -> {
                    Transaction.Status status = transaction.execute();
                    sendResponseSound(player, status);
                    sendResponseMessage(player, status);
                    init(player, inventoryContents);
                }));

            }
        }
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }

    private void sendResponseSound(Player player, Transaction.Status status) {
        switch (status) {
            case MISSING_ITEM:
            case NOT_ENOUGH_MONEY:
            case MAX_LEVEL_ALREADY_REACHED:
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 100, 0);
                break;
            case OK:
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1.5f);
                break;
        }
    }

    private void sendResponseMessage(Player player, Transaction.Status status) {
        switch (status) {
            case MISSING_ITEM:
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(plugin.getMessageConfig().missingItem()));
                break;
            case NOT_ENOUGH_MONEY:
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(plugin.getMessageConfig().notEnoughMoney()));
                break;
            case MAX_LEVEL_ALREADY_REACHED:
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(plugin.getMessageConfig().alreadyMaxLevel()));
                break;
        }
    }
}
