package com.davidout.customenchants.gui;

import com.davidout.api.custom.gui.GUI;
import com.davidout.api.custom.gui.GUIManager;
import com.davidout.api.utillity.TextUtils;
import com.davidout.api.utillity.item.Item;
import com.davidout.api.utillity.item.ItemCreator;
import com.davidout.customenchants.enchantments.Enchanter;
import com.davidout.customenchants.enchantments.EnchantmentType;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class EnchanterGUI extends GUI implements Listener {

    @Override
    public String getTitle() {
        return "&5&lEnchanter";
    }

    @Override
    public int getRows() {
        return 6;
    }

    @Override
    public void createInventory(String... strings) {
        int shelveCount = (int) Integer.parseInt(strings[0]);
        Player player = Bukkit.getPlayer(strings[1]);

        setItem(13, ItemCreator.createItem(Material.BOOKSHELF, "&d&lEnchantment list", Arrays.asList("&7See all the possible enchantments.")));

        ItemStack epic = (shelveCount >= 7) ? (player.getLevel() >= 20 || player.getGameMode().equals(GameMode.CREATIVE)) ? ItemCreator.createItem(Material.BLUE_STAINED_GLASS_PANE, "&9&lEpic Enchantment", Arrays.asList("&7Click to enchant the selected item.","&eEXP Cost: 20 levels")) : ItemCreator.createItem(Material.GRAY_STAINED_GLASS_PANE, "&9&lEpic Enchantment", Arrays.asList("&cYou don't have enough experience to enchant.")) : ItemCreator.createItem(Material.RED_STAINED_GLASS_PANE, "&c&lLocked", Arrays.asList("&cPlace more bookshelves to unlock this enchantment type."));
        ItemStack legendary = (shelveCount >= 16) ? (player.getLevel() >= 30 || player.getGameMode().equals(GameMode.CREATIVE)) ? ItemCreator.createItem(Material.ORANGE_STAINED_GLASS_PANE, "&6&lLegendary Enchantment", Arrays.asList("&7Click to enchant the selected item.","&eEXP Cost: 30 levels")) : ItemCreator.createItem(Material.GRAY_STAINED_GLASS_PANE, "&9&lEpic Enchantment", Arrays.asList("&cYou don't have enough experience to enchant.")) : ItemCreator.createItem(Material.RED_STAINED_GLASS_PANE, "&c&lLocked", Arrays.asList("&cPlace more bookshelves to unlock this enchantment type."));

        setItem(21, (player.getLevel() >= 10 || player.getGameMode().equals(GameMode.CREATIVE)) ? ItemCreator.createItem(Material.LIME_STAINED_GLASS_PANE, "&a&lCommon Enchantment", Arrays.asList("&7Click to enchant the selected item.","&eEXP Cost: 10 levels")) : ItemCreator.createItem(Material.GRAY_STAINED_GLASS_PANE, "&a&lCommon Enchantment", Arrays.asList("&cYou don't have enough experience to enchant.")));
        setItem(22, epic);
        setItem(23, legendary);

        setItem(40, ItemCreator.createItem(Material.ITEM_FRAME, "&b&lItem", Arrays.asList("&7Place here the item you want to enchant.")));

        for (int i = 0; i < (getRows() * 9); i++) {
            if(getItem(i) != null) continue;
            setItem(i, ItemCreator.createItem(Material.GRAY_STAINED_GLASS_PANE, " "));
        }
    }


    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if(e.getInventory() == e.getPlayer().getInventory()) return;
        if(!TextUtils.formatColorCodes(e.getView().getTitle()).equalsIgnoreCase(TextUtils.formatColorCodes(getTitle()))) return;
        if(e.getInventory().getItem(40).getType() == Material.ITEM_FRAME) return;
        ItemStack itemStack = e.getInventory().getItem(40);

        if(e.getPlayer().getInventory().firstEmpty() == -1) {
            e.getPlayer().getLocation().getWorld().dropItemNaturally(e.getPlayer().getLocation(), itemStack);
            return;
        }

        e.getPlayer().getInventory().addItem(itemStack);
    }



    @Override
    public void onClick(InventoryClickEvent e, Player player) {
        ItemStack clicked = e.getCurrentItem();
        ItemStack cursor = e.getCursor();
        e.setCancelled(true);

        ItemStack enchantItem = e.getInventory().getItem(40);


        if(clicked.getType().equals(Material.BOOKSHELF)) {
            EnchantmentsGUI gui = new EnchantmentsGUI();
            gui.openInventory(player);
            return;
        }


        if(clicked.getType().name().toLowerCase().endsWith("_glass_pane")) {
            enchantItem = (enchantItem.getType() == Material.BOOK) ? Enchanter.getBookItem() : enchantItem;

            if(clicked.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                if(player.getLevel() < 10 && !player.getGameMode().equals(GameMode.CREATIVE)) return;
                Enchanter.enchantItem(EnchantmentType.COMMON, enchantItem);
            }

            if(clicked.getType().equals(Material.BLUE_STAINED_GLASS_PANE)) {
                if(player.getLevel() < 20 && !player.getGameMode().equals(GameMode.CREATIVE)) return;
                Enchanter.enchantItem(EnchantmentType.EPIC, enchantItem);
            }

            if(clicked.getType().equals(Material.ORANGE_STAINED_GLASS_PANE)) {
                if(player.getLevel() < 30 && !player.getGameMode().equals(GameMode.CREATIVE)) return;
                Enchanter.enchantItem(EnchantmentType.LEGENDARY, enchantItem);
            }

            e.getInventory().setItem(40, enchantItem);
            return;
        }





        if(e.getSlot() != 40) return;
        ItemStack currentItem = (clicked.getType().equals(Material.ITEM_FRAME)) ? null : clicked;
        if(Item.itemIsNull(currentItem) && Item.itemIsNull(e.getCursor())) return; // cancel if the cursor and clicked item are null
        if(Item.itemIsNull(currentItem) && !e.getCursor().getEnchantments().isEmpty()) return; // cancel if item already contains enchantments
        if(Item.itemIsNull(currentItem) && !Item.itemIsNull(e.getCursor())) {
            if(e.getCursor().getType() == Material.BOOK && e.getCursor().getAmount() > 1) {
                ItemStack newBook = e.getCursor().clone();
                ItemStack newCursor = e.getCursor().clone();
                newBook.setAmount(1);
                newCursor.setAmount(e.getCursor().getAmount() - 1);
                e.setCurrentItem(newBook);
                e.setCursor(newCursor);
                return;
            }

            e.setCurrentItem(e.getCursor());
            e.setCursor(null);
            return;
        }

        if(!Item.itemIsNull(currentItem) && Item.itemIsNull(e.getCursor())) {
            e.setCursor(currentItem);
            e.getClickedInventory().setItem(40, ItemCreator.createItem(Material.ITEM_FRAME, "&b&lItem", Arrays.asList("&7Place here the item you want to enchant.")));
            return;
        }

        if(e.getCursor().getType().equals(Material.BOOK)) return;
        e.setCursor(clicked);
        e.setCurrentItem(cursor);
    }
}
