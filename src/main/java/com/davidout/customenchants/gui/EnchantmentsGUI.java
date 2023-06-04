package com.davidout.customenchants.gui;

import com.davidout.api.custom.gui.GUI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class EnchantmentsGUI extends GUI {
    @Override
    public String getTitle() {
        return "&5Enchantments";
    }

    @Override
    public int getRows() {
        return 3;
    }

    @Override
    public void createInventory(String... strings) {

    }

    @Override
    public void onClick(InventoryClickEvent inventoryClickEvent, Player player) {

    }
}
