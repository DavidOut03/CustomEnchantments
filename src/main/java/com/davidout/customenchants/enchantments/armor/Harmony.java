package com.davidout.customenchants.enchantments.armor;

import com.davidout.api.custom.enchantment.CustomEnchantment;
import com.davidout.api.custom.enchantment.EnchantmentDetails;
import com.davidout.api.custom.enchantment.EnchantmentManager;
import com.davidout.api.enums.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class Harmony extends CustomEnchantment {
    public Harmony() {
        super(new EnchantmentDetails("harmony", 2, "Slowly increases your health and food level when walking.", EnchantmentTarget.CHESTPLATE));
    }

    @Override
    public List<Class<? extends Event>> getEvents() {
        return Arrays.asList(PlayerMoveEvent.class);
    }

    @Override
    public void onAction(Event event) {
        if(!(event instanceof PlayerMoveEvent)) return;
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        Player player = e.getPlayer();

        if(!(e.getTo().getBlockX() != e.getFrom().getBlockX() || e.getTo().getBlockZ() != e.getFrom().getBlockZ()) || player.isFlying()) return;
        ItemStack item = player.getInventory().getChestplate();
            if(!EnchantmentManager.containsEnchantment(this, item)) return;
            int enchantmentLevel = item.getEnchantmentLevel(this);
            double restorationChance = 0.2 * enchantmentLevel;
            double random = Math.random();

            if (random >= restorationChance) return;
            double newHealth = (player.getHealth() == 20) ? 20 : player.getHealth() + item.getEnchantmentLevel(this);
            int newFood = (player.getFoodLevel() == 20) ? 20 : player.getFoodLevel() + item.getEnchantmentLevel(this);

            player.setHealth(newHealth);
            player.setFoodLevel(newFood);
        }
}

