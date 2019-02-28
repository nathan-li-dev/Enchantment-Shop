package com.monologica.enchantmentshop.shop;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;

public class TransactionFormatter implements TextFormatter {
    private Transaction transaction;

    public TransactionFormatter(Transaction t) {
        this.transaction = t;
    }

    public String format(String s) {
        String formatted = ChatColor.translateAlternateColorCodes('&', s);
        formatted = fastReplace(formatted, "{ENCHANTMENT_NAME}", formatEnchantment(transaction.getBuyableEnchantment().getEnchantment()));
        formatted = fastReplace(formatted, "{CURRENT_LEVEL}", String.valueOf(transaction.getCurrentLevel()));
        formatted = fastReplace(formatted, "{NEXT_LEVEL}", String.valueOf(transaction.getNextLevel()));
        formatted = fastReplace(formatted, "{COST}", String.format("%,.2f", transaction.calculateCost()));
        formatted = fastReplace(formatted, "{ENOUGH_MONEY}", transaction.playerHasEnoughMoney() ? "do" : "do not");
        formatted = fastReplace(formatted, "{MAX_LEVEL_BUYABLE}", transaction.isBuyable() ? "Click to purchase" : "Max level already achieved");

        return formatted;
    }

    private String formatEnchantment(Enchantment e) {
        String namespacedKey = e.getKey().toString();
        int colonIndex = namespacedKey.indexOf(':');
        String formatted = namespacedKey.substring(colonIndex + 1).replace('_', ' ');
        return WordUtils.capitalizeFully(formatted);
    }

    // Horcrux7 @ https://stackoverflow.com/questions/1010928/faster-alternatives-to-replace-method-in-a-java-string
    static String fastReplace( String str, String target, String replacement ) {
        int targetLength = target.length();
        if( targetLength == 0 ) {
            return str;
        }
        int idx2 = str.indexOf( target );
        if( idx2 < 0 ) {
            return str;
        }
        StringBuilder buffer = new StringBuilder( targetLength > replacement.length() ? str.length() : str.length() * 2 );
        int idx1 = 0;
        do {
            buffer.append( str, idx1, idx2 );
            buffer.append( replacement );
            idx1 = idx2 + targetLength;
            idx2 = str.indexOf( target, idx1 );
        } while( idx2 > 0 );
        buffer.append( str, idx1, str.length() );
        return buffer.toString();
    }
}
