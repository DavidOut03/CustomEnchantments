package com.davidout.customenchants;

import com.davidout.api.enchantment.EnchantmentManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class TestListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        ItemStack axe = new ItemStack(Material.DIAMOND_AXE);
        EnchantmentManager.addCustomEnchantment(axe, "autosmelt", 1);
        EnchantmentManager.addCustomEnchantment(axe, "telepathy", 1);
        e.getPlayer().getInventory().addItem(axe);

        ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
        EnchantmentManager.addCustomEnchantment(pickaxe, "multiblock", 1);
        EnchantmentManager.addCustomEnchantment(pickaxe, "autosmelt", 1);
        EnchantmentManager.addCustomEnchantment(pickaxe, "telepathy", 1);
        e.getPlayer().getInventory().addItem(pickaxe);

        ItemStack pickaxe2 = new ItemStack(Material.DIAMOND_PICKAXE);
        EnchantmentManager.addCustomEnchantment(pickaxe2, "multiblock", 1);
        EnchantmentManager.addCustomEnchantment(pickaxe2, "autosmelt", 1);
        e.getPlayer().getInventory().addItem(pickaxe2);

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.getPlayer().getInventory().clear();
    }


}
