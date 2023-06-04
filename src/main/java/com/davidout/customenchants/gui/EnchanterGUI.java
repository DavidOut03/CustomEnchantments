package com.davidout.customenchants.gui;

import com.davidout.api.custom.gui.GUI;
import com.davidout.api.utillity.item.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class EnchanterGUI extends GUI {

    @Override
    public String getTitle() {
        return "&5Enchanter";
    }

    @Override
    public int getRows() {
        return 6;
    }

    @Override
    public void createInventory(String... strings) {
        int shelveCount = (int) Integer.parseInt(strings[0]);
        Player player = Bukkit.getPlayer(strings[1]);

        setItem(13, ItemCreator.createItem(Material.BOOKSHELF, "&dEnchantment list", Arrays.asList("&7See all the possible enchantments.")));

        ItemStack epic = (shelveCount >= 7) ? (player.getLevel() >= 20 || player.getGameMode().equals(GameMode.CREATIVE)) ? ItemCreator.createItem(Material.BLUE_STAINED_GLASS_PANE, "&9&lEpic Enchantment", Arrays.asList("&eEXP Cost: 20 levels")) : ItemCreator.createItem(Material.GRAY_STAINED_GLASS_PANE, "&9&lEpic Enchantment", Arrays.asList("&cYou don't have enough experience to enchant.")) : ItemCreator.createItem(Material.BARRIER, "");
        ItemStack legendary = (shelveCount >= 16) ? (player.getLevel() >= 30 || player.getGameMode().equals(GameMode.CREATIVE)) ? ItemCreator.createItem(Material.ORANGE_STAINED_GLASS_PANE, "&6&lLegendary Enchantment", Arrays.asList("&eEXP Cost: 30 levels")) : ItemCreator.createItem(Material.GRAY_STAINED_GLASS_PANE, "&9&lEpic Enchantment", Arrays.asList("&cYou don't have enough experience to enchant.")) : ItemCreator.createItem(Material.BARRIER, "");

        setItem(21, (player.getLevel() >= 10 || player.getGameMode().equals(GameMode.CREATIVE)) ? ItemCreator.createItem(Material.LIME_STAINED_GLASS_PANE, "&a&lCommon Enchantment", Arrays.asList("&eEXP Cost: 10 levels")) : ItemCreator.createItem(Material.GRAY_STAINED_GLASS_PANE, "&a&lCommon Enchantment", Arrays.asList("&cYou don't have enough experience to enchant.")));
        setItem(22, epic);
        setItem(23, legendary);

        setItem(40, ItemCreator.createItem(Material.ITEM_FRAME, "&b&lItem", Arrays.asList("&7Place here the item you want to enchant.")));
    }




    @Override
    public void onClick(InventoryClickEvent inventoryClickEvent, Player player) {
        ItemStack clicked = inventoryClickEvent.getCurrentItem();
        ItemStack cursor = inventoryClickEvent.getCursor();
        inventoryClickEvent.setCancelled(true);

        if(clicked.getType().equals(Material.BOOKSHELF)) {
            EnchanterGUI enchanterGUI = new EnchanterGUI();
            enchanterGUI.openInventory(player);
            return;
        }

        if(clicked.getType().equals(Material.ITEM_FRAME)) {
            if(cursor == null) return;
            inventoryClickEvent.getInventory().setItem(40, cursor);
            return;
        }



    }
}
