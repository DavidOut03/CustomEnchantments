package com.davidout.customenchants.gui;

import com.davidout.api.custom.enchantment.CustomEnchantment;
import com.davidout.api.custom.enchantment.EnchantmentManager;
import com.davidout.api.custom.gui.GUI;
import com.davidout.api.enums.EnchantmentTarget;
import com.davidout.api.utillity.RomanNumber;
import com.davidout.api.utillity.item.ItemCreator;
import com.davidout.customenchants.Main;
import com.davidout.customenchants.enchantments.Enchanter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

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
    public void createInventory(String... strings) {
        List<CustomEnchantment> possibleEnchantments = EnchantmentManager.getCustomEnchants();

        possibleEnchantments.forEach(customEnchantment -> {

            ItemStack itemStack = ItemCreator.createItem(
                    Material.ENCHANTED_BOOK,
                    "&5&l" + customEnchantment.getDisplayName(),
                    Arrays.asList(
                            "&dTarget: &f" + Enchanter.enchantmentTargets(customEnchantment),
                            "&dMax level: &f" + RomanNumber.toRoman(customEnchantment.getMaxLevel())
                    )
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
