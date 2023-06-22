package com.davidout.customenchants.gui;

import com.davidout.api.custom.enchantment.CustomEnchantment;
import com.davidout.api.custom.enchantment.EnchantmentManager;
import com.davidout.api.custom.gui.GUI;
import com.davidout.api.enums.EnchantmentTarget;
import com.davidout.api.utillity.item.ItemCreator;
import com.davidout.api.utillity.text.RomanNumber;
import com.davidout.customenchants.Main;
import com.davidout.customenchants.enchantments.Enchanter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class EnchantmentsGUI extends GUI {
    @Override
    public String getTitle() {
        return "&5&lEnchantments";
    }

    @Override
    public int getRows() {
        return 3;
    }

    @Override
    public void createInventory(Object... strings) {
        List<CustomEnchantment> possibleEnchantments = EnchantmentManager.getCustomEnchants();

        possibleEnchantments.forEach(customEnchantment -> {
            List<String> lore = new ArrayList<>();
            lore.add("&7" + customEnchantment.getDescription());
            lore.add(" ");
            if (Enchanter.getCommonEnchantments().containsKey(customEnchantment)) lore.add("&a&lCommon: &7" + Enchanter.getCommonEnchantments().get(customEnchantment));
            if (Enchanter.getEpicEnchantments().containsKey(customEnchantment)) lore.add("&9&lEpic: &7" + Enchanter.getEpicEnchantments().get(customEnchantment));
            if (Enchanter.getLegendaryEnchantments().containsKey(customEnchantment)) lore.add("&6&lLegendary: &7" + Enchanter.getLegendaryEnchantments().get(customEnchantment));
            lore.add(" ");
            lore.add("&dTarget: &f" + Enchanter.enchantmentTargets(customEnchantment));
            lore.add("&dMax level: &f" + RomanNumber.toRoman(customEnchantment.getMaxLevel()));


            ItemStack itemStack = ItemCreator.createItem(
                    Material.ENCHANTED_BOOK,
                    "&5&l" + customEnchantment.getDisplayName(),
                   lore
            );

            addItem(itemStack);
        });

        for (int i = 0; i < (getRows() * 9); i++) {
            if(getItem(i) != null && getItem(i).getType() == Material.ENCHANTED_BOOK) continue;
            setItem(i, ItemCreator.createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " "));
        }
    }

    @Override
    public void onClick(InventoryClickEvent inventoryClickEvent, Player player) {
        inventoryClickEvent.setCancelled(true);
    }


}
