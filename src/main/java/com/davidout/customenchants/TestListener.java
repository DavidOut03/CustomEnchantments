package com.davidout.customenchants;

import com.davidout.api.enchantment.EnchantmentManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class TestListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
        pickaxe.setDurability((short) 500);
        EnchantmentManager.addCustomEnchantment(pickaxe, "autosmelt", 1);
        EnchantmentManager.addCustomEnchantment(pickaxe, "autorepair", 1);
        e.getPlayer().getInventory().addItem(pickaxe);

        ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
        leggings.setDurability((short) 500);
        EnchantmentManager.addCustomEnchantment(leggings, "autorepair", 1);
        EnchantmentManager.addCustomEnchantment(leggings, "flame_guard", 1);
        e.getPlayer().getInventory().addItem(leggings);

        ItemStack axe = new ItemStack(Material.DIAMOND_AXE);
        axe.setDurability((short) 500);
        EnchantmentManager.addCustomEnchantment(axe, "autorepair", 1);
        EnchantmentManager.addCustomEnchantment(axe, "lumberjack", 1);
        e.getPlayer().getInventory().addItem(axe);


    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.getPlayer().getInventory().clear();
    }
}
