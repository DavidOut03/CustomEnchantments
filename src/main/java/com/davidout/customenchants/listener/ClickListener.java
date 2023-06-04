package com.davidout.customenchants.listener;

import com.davidout.api.custom.enchantment.EnchantmentManager;
import com.davidout.api.utillity.item.Item;
import com.davidout.api.utillity.item.ItemCreator;
import com.davidout.customenchants.Main;
import com.davidout.customenchants.enchantments.Enchanter;
import com.davidout.customenchants.gui.EnchanterGUI;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class ClickListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        ItemStack chest = ItemCreator.createItem(Material.DIAMOND_CHESTPLATE);
        ItemStack sword = ItemCreator.createItem(Material.DIAMOND_SWORD);

       EnchantmentManager.addCustomEnchantment(chest, "soulbound", 1);
       EnchantmentManager.addCustomEnchantment(sword, "soulbound", 2);

       e.getPlayer().getInventory().addItem(chest, sword);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Block block = e.getClickedBlock();
        ItemStack item = e.getItem();

        if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !e.getAction().equals(Action.RIGHT_CLICK_AIR)) return;
        if(item == null || !Item.itemIsTheSame(item, Enchanter.getKeyItem())) return;
        if(e.getAction() == Action.RIGHT_CLICK_AIR || block == null || !block.getType().equals(Material.ENCHANTING_TABLE) && !block.getType().equals(Material.ENCHANTING_TABLE)) {
            e.setCancelled(true);
            return;
        }

        if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        e.setCancelled(true);
        EnchanterGUI enchanterGUI = new EnchanterGUI();
        enchanterGUI.openInventory(e.getPlayer(), getBookshelve(e.getClickedBlock()) + "", e.getPlayer().getName());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(InventoryClickEvent event) {
        if(event.isCancelled() ||event.getCurrentItem() == null || event.getCursor() == null) return;
        if(event.getCursor().getType() != Material.BOOK || event.getCursor().getEnchantments().isEmpty()) return;
        event.setCancelled(true);

        ItemStack set = event.getCurrentItem().clone();

        boolean worked = false;
        for (Map.Entry<Enchantment, Integer> enchantmentIntegerEntry : event.getCursor().getEnchantments().entrySet()) {
            Enchantment enchantment = enchantmentIntegerEntry.getKey();
            int level = enchantmentIntegerEntry.getValue();
            boolean enchanted = EnchantmentManager.addCustomEnchantment(set, enchantment, level);
            if(!enchanted) continue;
            worked = true;
        }

        if(!worked) return;
        event.setCursor(null);
        event.setCurrentItem(set);
    }


    public int getBookshelve(Block centerBlock) {
        int bookshelfCount = 0;
        World world = centerBlock.getWorld();

        int centerX = centerBlock.getX();
        int centerY = centerBlock.getY();
        int centerZ = centerBlock.getZ();

        int startX = centerX - 2;
        int startY = centerY;
        int startZ = centerZ - 2;

        for (int x = startX; x <= startX + 4; x++) {
            for (int y = startY; y <= startY + 1; y++) {
                for (int z = startZ; z <= startZ + 4; z++) {
                    Block currentBlock = world.getBlockAt(x, y, z);
                    if(currentBlock.getType().equals(Material.AIR) || currentBlock.getType().equals(Material.ENCHANTING_TABLE)) continue;
                    if (currentBlock.getType() != Material.BOOKSHELF) continue;
                        bookshelfCount++;
                }
            }
        }

        return bookshelfCount;
    }
}
