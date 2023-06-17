package com.davidout.customenchants.enchantments.all;

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
import java.util.Random;

public class AutoRepair extends CustomEnchantment {
    public AutoRepair() {
        super(new EnchantmentDetails("autorepair", 3, "Repairs the items in your inventory when you walk.", EnchantmentTarget.ALL));
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
        for (ItemStack item : player.getInventory().getContents()) {
            if(!EnchantmentManager.containsEnchantment(this, item)) continue;
            int enchantmentLevel = item.getEnchantmentLevel(this);
            double restorationChance = 0.2 * enchantmentLevel;
            double random = Math.random();

            if (random >= restorationChance) continue;
                item.setDurability((short) ( item.getDurability() -  .01));

        }
    }
}
