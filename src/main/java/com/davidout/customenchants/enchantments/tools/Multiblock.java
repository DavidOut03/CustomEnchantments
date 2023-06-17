package com.davidout.customenchants.enchantments.tools;

import com.davidout.api.custom.enchantment.CustomEnchantment;
import com.davidout.api.custom.enchantment.EnchantmentManager;
import com.davidout.api.enums.EnchantmentTarget;
import com.davidout.api.utillity.TextUtils;
import com.davidout.customenchants.enchantments.CustomEnchantmentManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public class Multiblock extends CustomEnchantment {
    public Multiblock(String name, int maxLevel) {
        super(name, maxLevel, EnchantmentTarget.PICKAXE);
    }

    @Override
    public List<Class<? extends Event>> getEvents() {
        return Collections.singletonList(BlockBreakEvent.class);
    }

    @Override
    public void onAction(Event event) {
        if(!(event instanceof BlockBreakEvent)) return;
        BlockBreakEvent blockBreakEvent = (BlockBreakEvent) event;
        Player player = blockBreakEvent.getPlayer();

        if(!player.getInventory().getItemInMainHand().getType().toString().toLowerCase().contains("pickaxe")) return;
        if(!EnchantmentManager.containsEnchantment(this, player.getItemInHand())) return;
        int level = player.getInventory().getItemInMainHand().getEnchantmentLevel(this);
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
