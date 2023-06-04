package com.davidout.customenchants.listener;

import com.davidout.api.custom.enchantment.EnchantmentManager;
import com.davidout.api.utillity.item.ItemCreator;
import com.davidout.customenchants.Main;
import com.davidout.customenchants.gui.EnchanterGUI;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
        if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || !e.getClickedBlock().getType().equals(Material.ENCHANTING_TABLE) && !e.getClickedBlock().getType().equals(Material.LEGACY_ENCHANTMENT_TABLE)) return;
        e.setCancelled(true);
        EnchanterGUI gui = new EnchanterGUI();
        gui.openInventory(e.getPlayer(), getBookshelve(e.getClickedBlock()) + "", e.getPlayer().getName());
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
