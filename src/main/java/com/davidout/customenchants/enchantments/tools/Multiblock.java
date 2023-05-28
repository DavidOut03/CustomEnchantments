package com.davidout.customenchants.enchantments.tools;

import com.davidout.api.enchantment.CustomEnchantment;
import com.davidout.api.utillity.TextUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public class Multiblock extends CustomEnchantment {
    public Multiblock(String name, int maxLevel, EnchantmentTarget tool) {
        super(name, maxLevel);
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
        if(!player.getInventory().getItemInMainHand().containsEnchantment(this)) return;
        int level = player.getInventory().getItemInMainHand().getEnchantmentLevel(this);

        breakBlocks(player, blockBreakEvent.getBlock(), player.getInventory().getItemInMainHand(), 1);
    }

    private void breakBlocks(Player p, Block brokenBlock, ItemStack itemStack, int level) {

    }



//    public static void setTextBelowName(Player p, String text) {
//        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
//        Objective objective = (scoreboard.getObjective("belowName") == null) ? scoreboard.registerNewObjective("belowName", "dummy", text) : scoreboard.getObjective("belowName");
//        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
//
//        List<String> oldLines = new ArrayList<>(objective.getScoreboard().getEntries());
//
//        String teamName = "belowName_" + p.getName();
//
//        Team team = (scoreboard.getTeam(teamName) == null) ? scoreboard.registerNewTeam(teamName) : scoreboard.getTeam(teamName);
//        team.setPrefix(getPrefix(text));
//        team.setSuffix(getSuffix(text));
//        team.addEntry(getEntry(text));
//
//        if(!oldLines.isEmpty() && oldLines.get(0) != null) scoreboard.resetScores(oldLines.get(0));
//
//        objective.setDisplayName(TextUtils.formatColorCodes(text));
//    }

    private static String getPrefix(String line) {
        String lineText = (line == null) ? "" : TextUtils.formatColorCodes(line);
        return (lineText.length() <= 16) ? "" : lineText.substring(0, 16);
    }

    private static String getEntry(String line) {
        String lineText = (line == null) ? "" : TextUtils.formatColorCodes(line);
        return (lineText.length() <= 16) ? lineText : (lineText.length() <= 32) ? lineText.substring(15, lineText.length()) : lineText.substring(15, 31);
    }

    private static String getSuffix(String line) {
        String lineText = (line == null) ? "" : TextUtils.formatColorCodes(line);
        return (lineText.length() <= 32) ? "" : lineText.substring(15, 31);
    }


    public void break3x3x1Blocks(World world, int centerX, int centerY, int centerZ, ItemStack itemStack) {
        breakBlocksInDirection(world, centerX, centerY, centerZ, "left", itemStack);
        breakBlocksInDirection(world, centerX, centerY, centerZ, "right", itemStack);
        breakBlocksInDirection(world, centerX, centerY, centerZ, "up", itemStack);
        breakBlocksInDirection(world, centerX, centerY, centerZ, "down", itemStack);
    }

    private void breakBlocksInDirection(World world, int centerX, int centerY, int centerZ, String direction, ItemStack itemStack) {
        int minX = centerX - 1;
        int maxX = centerX + 1;
        int minY = centerY;
        int maxY = centerY;
        int minZ = centerZ;
        int maxZ = centerZ;

        switch (direction.toLowerCase()) {
            case "left":
                maxX--;
                break;
            case "right":
                minX++;
                break;
            case "up":
                maxY++;
                break;
            case "down":
                minY--;
                break;
            default:
                Bukkit.getLogger().warning("Invalid direction provided: " + direction);
                return;
        }

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block block = world.getBlockAt(x, y, z);
                    if (block.getType() != Material.AIR) {
                        block.breakNaturally(itemStack);
                    }
                }
            }
        }
    }

}
