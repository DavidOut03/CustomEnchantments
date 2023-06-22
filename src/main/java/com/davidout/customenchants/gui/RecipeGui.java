package com.davidout.customenchants.gui;

import com.davidout.api.custom.gui.GUI;
import com.davidout.api.utillity.item.ItemCreator;
import com.davidout.customenchants.enchantments.Enchanter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class RecipeGui extends GUI {
    @Override
    public String getTitle() {
        return "&5Recipe";
    }

    @Override
    public int getRows() {
        return 5;
    }

    @Override
    public void createInventory(Object... strings) {

        setItem(11, ItemCreator.createItem(Material.PAPER));
        setItem(12, ItemCreator.createItem(Material.PAPER));
        setItem(13, ItemCreator.createItem(Material.PAPER));


        setItem(20, ItemCreator.createItem(Material.LAPIS_LAZULI));
        setItem(21, ItemCreator.createItem(Material.PAPER));
        setItem(22, ItemCreator.createItem(Material.LAPIS_LAZULI));

        setItem(29, ItemCreator.createItem(Material.LEATHER));
        setItem(30, ItemCreator.createItem(Material.LEATHER));
        setItem(31, ItemCreator.createItem(Material.LEATHER));

        setItem(24, Enchanter.getKeyItem());

        for (int i = 0; i < getSlots(); i++) {
            if(getItem(i) != null && getItem(i).getType() != Material.AIR) continue;
            setItem(i, ItemCreator.createItem(Material.GRAY_STAINED_GLASS_PANE, " "));
        }

    }

    @Override
    public void onClick(InventoryClickEvent inventoryClickEvent, Player player) {
        inventoryClickEvent.setCancelled(true);
    }
}
