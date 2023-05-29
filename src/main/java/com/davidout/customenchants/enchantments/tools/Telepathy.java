package com.davidout.customenchants.enchantments.tools;

import com.davidout.api.enchantment.CustomEnchantment;
import com.davidout.api.enchantment.EnchantmentManager;
import com.davidout.customenchants.enchantments.CustomEnchantmentManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Telepathy extends CustomEnchantment {
    public Telepathy(String name, int maxLevel) {
        super(name, maxLevel, EnchantmentTarget.TOOL);
    }

    @Override
    public List<Class<? extends Event>> getEvents() {
        return Arrays.asList(BlockBreakEvent.class);
    }

    @Override
    public void onAction(Event event) {
        if(!(event instanceof BlockBreakEvent)) return;
            BlockBreakEvent breakEvent = (BlockBreakEvent) event;
            Player player = breakEvent.getPlayer();
             if(player.getInventory().firstEmpty() == -1) return;
             if(!player.getItemInHand().containsEnchantment(this) || player.getItemInHand().containsEnchantment(CustomEnchantmentManager.autoSmelt) || player.getItemInHand().containsEnchantment(CustomEnchantmentManager.multiblock)) return;
             CustomEnchantmentManager.breakBlock(breakEvent.getBlock(), breakEvent.getPlayer(), breakEvent.getPlayer().getItemInHand());
    }


    public static void teleportDropToInventory(Location location, ItemStack itemStack, Player player) {
        if(player.getInventory().firstEmpty() == -1) {
            location.getWorld().dropItemNaturally(location, itemStack);
            return;
        }

        player.getInventory().addItem(itemStack);
    }
}
