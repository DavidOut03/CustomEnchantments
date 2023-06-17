package com.davidout.customenchants.enchantments.tools;

import com.davidout.api.custom.enchantment.CustomEnchantment;
import com.davidout.api.custom.enchantment.EnchantmentManager;
import com.davidout.api.enums.EnchantmentTarget;
import com.davidout.customenchants.Main;
import com.davidout.customenchants.enchantments.CustomEnchantmentManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class QuickHarvest extends CustomEnchantment {
    public QuickHarvest(String name, int maxLevel) {
        super(name, maxLevel, EnchantmentTarget.HOE);
    }

    @Override
    public List<Class<? extends Event>> getEvents() {
        return Collections.singletonList(BlockBreakEvent.class);
    }

    @Override
    public void onAction(Event event) {
        if(!(event instanceof BlockBreakEvent)) return;
        BlockBreakEvent e = (BlockBreakEvent) event;
        Location underBlock = e.getBlock().getWorld().getBlockAt(e.getBlock().getX(), e.getBlock().getY() - 1, e.getBlock().getZ()).getLocation();


        if(!EnchantmentManager.containsEnchantment(this, e.getPlayer().getItemInHand()) || !e.getPlayer().getItemInHand().getType().name().endsWith("_HOE")) return;
        if(e.getBlock().getWorld().getBlockAt(underBlock).getType() != Material.FARMLAND && e.getBlock().getWorld().getBlockAt(underBlock).getType() != Material.SOUL_SAND) return;
        if(getCrops().get(e.getBlock().getType()) == null || !(e.getBlock().getBlockData() instanceof Ageable)) return;
        Ageable ageable = (Ageable) e.getBlock().getBlockData();
        if(ageable.getAge() != ageable.getMaximumAge())  {
            e.setCancelled(true);
            return;
        }



        e.setCancelled(true);
        Bukkit.getScheduler().runTask(Main.getPlugin(), () ->  e.getBlock().setType(e.getBlock().getType()));

            e.getBlock().getDrops(e.getPlayer().getItemInHand()).forEach(itemStack -> {
                if(EnchantmentManager.containsEnchantment(CustomEnchantmentManager.telepathy, e.getPlayer().getItemInHand())) {
                    Telepathy.teleportDropToInventory(e.getPlayer().getLocation(), itemStack, e.getPlayer());
                    return;
                }

                if(getCrops().get(e.getBlock()) == itemStack.getType()) itemStack.setAmount(itemStack.getAmount() - 1);
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), itemStack);
            });

    }


    private static HashMap<Material, Material> getCrops() {
        HashMap<Material, Material> crops = new HashMap<>();
        crops.put(Material.WHEAT, Material.WHEAT_SEEDS);
        crops.put(Material.CARROTS, Material.CARROT);
        crops.put(Material.POTATOES, Material.POTATO);
        crops.put(Material.BEETROOTS, Material.BEETROOT_SEEDS);
        crops.put(Material.NETHER_WART, Material.NETHER_WART);
        return crops;
    }

}
