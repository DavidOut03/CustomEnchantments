package com.davidout.customenchants.enchantments.tools;

import com.davidout.api.enchantment.CustomEnchantment;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Lumberjack extends CustomEnchantment {
    public Lumberjack(String name, int maxLevel) {
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
        if(!e.getPlayer().getItemInHand().getType().toString().contains("AXE")) return;
        breakTree(e.getBlock(), e.getPlayer().getInventory().getItemInHand());
    }

    private void breakTree(Block block, ItemStack itemStack) {
        // Recursively break all logs connected to the given block
        for (Block relative : getConnectedLogs(block)) {
            relative.breakNaturally(itemStack);
            breakTree(relative, itemStack);
        }
    }

    private List<Block> getConnectedLogs(Block block) {
        // Retrieve all logs connected to the given block
        List<Block> connectedLogs = new ArrayList<>();
        Queue<Block> queue = new LinkedList<>();
        queue.add(block);

        while (!queue.isEmpty()) {
            Block current = queue.poll();

            if (!current.getType().name().endsWith("_LOG")) continue;
                connectedLogs.add(current);

                for (Block relative : getAdjacentBlocks(current)) {
                    if (!connectedLogs.contains(relative) && !queue.contains(relative)) {
                        queue.add(relative);
                    }
                }

        }

        return connectedLogs;
    }

    private List<Block> getAdjacentBlocks(Block block) {
        // Get the blocks adjacent to the given block
        List<Block> adjacentBlocks = new ArrayList<>();
        World world = block.getWorld();
        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();

        adjacentBlocks.add(world.getBlockAt(x + 1, y, z));
        adjacentBlocks.add(world.getBlockAt(x - 1, y, z));
        adjacentBlocks.add(world.getBlockAt(x, y + 1, z));
        adjacentBlocks.add(world.getBlockAt(x, y - 1, z));
        adjacentBlocks.add(world.getBlockAt(x, y, z + 1));
        adjacentBlocks.add(world.getBlockAt(x, y, z - 1));

        return adjacentBlocks;
    }
}
