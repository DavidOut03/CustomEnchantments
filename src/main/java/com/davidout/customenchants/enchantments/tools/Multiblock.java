package com.davidout.customenchants.enchantments.tools;

import com.davidout.api.custom.enchantment.CustomEnchantment;
import com.davidout.api.custom.enchantment.EnchantmentDetails;
import com.davidout.api.custom.enchantment.EnchantmentManager;
import com.davidout.api.enums.EnchantmentTarget;
import com.davidout.customenchants.enchantments.CustomEnchantmentManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Multiblock extends CustomEnchantment {
    public Multiblock() {
        super(new EnchantmentDetails(  "multiblock", 2, "Mine a bigger area of blocks.", Arrays.asList(EnchantmentTarget.PICKAXE, EnchantmentTarget.SHOVEL)));
    }

    @Override
    public List<Class<? extends Event>> getEvents() {
        return Arrays.asList(BlockBreakEvent.class, PlayerItemDamageEvent.class);
    }

    @Override
    public void onAction(Event event) {
        if(event instanceof PlayerItemDamageEvent) {
            PlayerItemDamageEvent e = (PlayerItemDamageEvent) event;
            if(!EnchantmentManager.containsEnchantment(this, e.getItem())) return;
            Random random = new Random();
            double percent = random.nextDouble() * 100;

            if(percent < 35) return;
            e.setCancelled(true);
            e.setDamage(0);
        }

        if(!(event instanceof BlockBreakEvent)) return;
        BlockBreakEvent blockBreakEvent = (BlockBreakEvent) event;
        Player player = blockBreakEvent.getPlayer();

        if(!player.getInventory().getItemInMainHand().getType().toString().toLowerCase().contains("pickaxe")) return;
        if(!EnchantmentManager.containsEnchantment(this, player.getItemInHand())) return;
        int level = player.getInventory().getItemInMainHand().getEnchantments().get(this);
        CustomEnchantmentManager.breakBlock(blockBreakEvent.getBlock(), player, player.getItemInHand());
        breakArea(player, blockBreakEvent.getBlock(), level);
    }


    private void breakArea(Player player, Block targetBlock, int radius) {
        BlockFace playerFacing = player.getFacing();

        for(int a = -radius; a <= radius; a++) {
            for(int b = -radius; b <= radius; b++) {

                Block currentBlock = (playerIsLookingUpOrDown(player)) ? targetBlock.getWorld().getBlockAt(targetBlock.getX() + a, targetBlock.getY(), targetBlock.getZ() + b) :
                                     (playerFacing == BlockFace.EAST || playerFacing == BlockFace.WEST) ? targetBlock.getWorld().getBlockAt(targetBlock.getX(), targetBlock.getY() + a, targetBlock.getZ() + b) :
                                                                                                          targetBlock.getWorld().getBlockAt(targetBlock.getX() + a, targetBlock.getY() + b, targetBlock.getZ());

                if(currentBlock.equals(targetBlock)) continue;
                CustomEnchantmentManager.breakBlock(currentBlock, player, player.getItemInHand());
            }
        }

    }

    private boolean playerIsLookingUpOrDown(Player p) {
        float pitch = p.getLocation().getPitch();
        return pitch <= -45 || pitch >= 45;
    }



}
