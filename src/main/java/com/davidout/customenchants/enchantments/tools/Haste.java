package com.davidout.customenchants.enchantments.tools;

import com.davidout.api.custom.enchantment.CustomEnchantment;
import com.davidout.api.custom.enchantment.EnchantmentDetails;
import com.davidout.api.custom.enchantment.EnchantmentManager;
import com.davidout.api.custom.event.ArmorDequipEvent;
import com.davidout.api.custom.event.ArmorEquipEvent;
import com.davidout.api.enums.EnchantmentTarget;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class Haste extends CustomEnchantment {
    public Haste() {
        super(new EnchantmentDetails("haste", 2, "Mine a bit faster", EnchantmentTarget.TOOLS));
    }

    @Override
    public List<Class<? extends Event>> getEvents() {
        return Arrays.asList(PlayerItemHeldEvent.class);
    }

    @Override
    public void onAction(Event event) {
        if(!(event instanceof PlayerItemHeldEvent)) return;
        PlayerItemHeldEvent e = (PlayerItemHeldEvent) event;
        ItemStack prevItem = e.getPlayer().getInventory().getItem(e.getPreviousSlot());
        ItemStack newItem = e.getPlayer().getInventory().getItem(e.getNewSlot());
        if(EnchantmentManager.containsEnchantment(this, prevItem)) e.getPlayer().removePotionEffect(PotionEffectType.FAST_DIGGING);;
        if(!EnchantmentManager.containsEnchantment(this, newItem)) return;
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, newItem.getEnchantments().get(this) - 1));
    }
}