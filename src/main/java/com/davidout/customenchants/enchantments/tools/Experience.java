package com.davidout.customenchants.enchantments.tools;

import com.davidout.api.custom.enchantment.CustomEnchantment;
import com.davidout.api.custom.enchantment.EnchantmentDetails;
import com.davidout.api.custom.enchantment.EnchantmentManager;
import com.davidout.api.enums.EnchantmentTarget;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerExpChangeEvent;

import java.util.Arrays;
import java.util.List;

public class Experience extends CustomEnchantment {
    public Experience() {
        super(new EnchantmentDetails("experience", 3, "Receive more experience when holding a item with this enchantment", Arrays.asList(EnchantmentTarget.TOOLS, EnchantmentTarget.WEAPONS)));
    }

    @Override
    public List<Class<? extends Event>> getEvents() {
        return Arrays.asList(PlayerExpChangeEvent.class);
    }

    @Override
    public void onAction(Event event) {
        if(!(event instanceof PlayerExpChangeEvent)) return;
        PlayerExpChangeEvent e = (PlayerExpChangeEvent) event;

        if(!EnchantmentManager.containsEnchantment(this, e.getPlayer().getItemInHand())) return;
        float multiplier = (float) ((e.getPlayer().getItemInHand().getEnchantmentLevel(this) * 0.35) + 1);
        e.setAmount( Math.round(multiplier * e.getAmount()) );
    }

}
