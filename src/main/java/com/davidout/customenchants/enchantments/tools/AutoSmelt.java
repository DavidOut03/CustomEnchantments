package com.davidout.customenchants.enchantments.tools;

import com.davidout.api.enchantment.CustomEnchantment;
import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class AutoSmelt extends CustomEnchantment {

    Material[] ores = {
            Material.COAL_ORE,
            Material.IRON_ORE,
            Material.GOLD_ORE,
            Material.REDSTONE_ORE,
            Material.LAPIS_ORE,
            Material.DIAMOND_ORE,
            Material.EMERALD_ORE,
            Material.NETHER_QUARTZ_ORE
            // Add more ore materials as needed
    };

    public AutoSmelt(String name, int maxLevel) {
        super(name, maxLevel, EnchantmentTarget.TOOL);
    }

    @Override
    public List<Class<? extends Event>> getEvents() {
        return Arrays.asList(BlockBreakEvent.class);
    }

    @Override
    public void onAction(Event event) {
        if(!(event instanceof BlockBreakEvent)) return;
        BlockBreakEvent e = (BlockBreakEvent) event;
        Player p = e.getPlayer();

        if(!e.getPlayer().getItemInHand().containsEnchantment(this)) return;

        if(!e.getPlayer().getItemInHand().getType().toString().contains("PICKAXE") || !e.getBlock().getType().name().endsWith("_ORE")) return;

        Collection<ItemStack> drops = e.getBlock().getDrops(e.getPlayer().getItemInHand());
        e.setDropItems(false);


        drops.forEach(itemStack -> {
            if(itemStack.getType() == Material.GOLD_ORE || itemStack.getType() == Material.RAW_GOLD) itemStack =new ItemStack(Material.GOLD_INGOT, itemStack.getAmount());
            if(itemStack.getType() == Material.IRON_ORE || itemStack.getType() == Material.RAW_IRON)  itemStack = new ItemStack(Material.IRON_INGOT, itemStack.getAmount());
            if(itemStack.getType() == Material.COPPER_ORE || itemStack.getType() == Material.RAW_COPPER) itemStack = new ItemStack(Material.COPPER_ORE, itemStack.getAmount());

            if(itemStack.getType() == Material.RAW_GOLD_BLOCK) itemStack = new ItemStack(Material.GOLD_BLOCK, itemStack.getAmount());
            if(itemStack.getType() == Material.RAW_IRON_BLOCK) itemStack = new ItemStack(Material.IRON_BLOCK, itemStack.getAmount());
            if(itemStack.getType() == Material.RAW_COPPER_BLOCK) itemStack = new ItemStack(Material.COPPER_BLOCK, itemStack.getAmount());

            p.getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(),itemStack);
        });
    }
}
