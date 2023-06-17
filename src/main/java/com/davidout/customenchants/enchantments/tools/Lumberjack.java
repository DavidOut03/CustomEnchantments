package com.davidout.customenchants.enchantments.tools;

import com.davidout.api.custom.enchantment.CustomEnchantment;
import com.davidout.api.custom.enchantment.EnchantmentDetails;
import com.davidout.api.custom.enchantment.EnchantmentManager;
import com.davidout.api.enums.EnchantmentTarget;
import com.davidout.customenchants.enchantments.CustomEnchantmentManager;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.*;

public class Lumberjack extends CustomEnchantment {
    public Lumberjack() {
        super(new EnchantmentDetails(  "lumberjack", 1, "Mine a tree in one time.", EnchantmentTarget.AXE));
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

        if(!EnchantmentManager.containsEnchantment(this, p.getItemInHand())) return;
        if(!p.getItemInHand().getType().toString().contains("AXE")) return;
        if(!e.getBlock().getType().name().endsWith("_LOG")) return;


        List<Block> connectedBlocks = getConnectedLogs(e.getBlock());
        int treeSize = getThreeSize(connectedBlocks);
        int max = getMaxBlocks(treeSize);


        for (int i = 0; i < connectedBlocks.size(); i++) {
            if(i >= max) break;
            CustomEnchantmentManager.breakBlock(connectedBlocks.get(i), p, p.getItemInHand());
        }
    }


    private int getThreeSize(List<Block> blocks) {
        int yMin = Integer.MAX_VALUE;
        int yMax = Integer.MIN_VALUE;

        for (Block block : blocks) {
            yMin = (block.getY() < yMin) ? block.getY() : yMin;
            yMax = (block.getY() > yMax) ? block.getY() : yMax;
        }

        return ( yMax - yMin);

    }

    private List<Block> getConnectedLogs(Block block) {
        // Retrieve all logs connected to the given block
        List<Block> connectedLogs = new ArrayList<>();
        Queue<Block> queue = new LinkedList<>();
        queue.add(block);

        while (!queue.isEmpty()) {
            Block current = queue.poll();
            if(current == null) continue;

            if (!current.getType().name().endsWith("_LOG") && !current.getType().name().endsWith("_LEAVES")) continue;
                connectedLogs.add(current);

                for (Block relative : getAdjacentBlocks(current)) {
                    if (connectedLogs.contains(relative) || queue.contains(relative)) continue;
                        queue.add(relative);
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

    private int getMaxBlocks(int height) {
        if(height < 6) return 63;
        if(height <= 8) return 64;
        if(height <= 12) return 87;
        if(height <= 18) return 300;
        return 400;
    }
}
