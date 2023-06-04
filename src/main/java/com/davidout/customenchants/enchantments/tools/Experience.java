package com.davidout.customenchants.enchantments.tools;

import com.davidout.api.custom.enchantment.CustomEnchantment;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerExpChangeEvent;

import java.util.Arrays;
import java.util.List;

public class Experience extends CustomEnchantment {
    public Experience(String name, int maxLevel) {
        super(name, maxLevel);
    }

    @Override
    public List<Class<? extends Event>> getEvents() {
        return Arrays.asList(PlayerExpChangeEvent.class);
    }

    @Override
    public void onAction(Event event) {
        if(!(event instanceof PlayerExpChangeEvent)) return;
        PlayerExpChangeEvent e = (PlayerExpChangeEvent) event;

        if(!e.getPlayer().getItemInHand().containsEnchantment(this)) return;
        float multiplier = (float) ((e.getPlayer().getItemInHand().getEnchantmentLevel(this) * 0.35) + 1);
        e.setAmount( Math.round(multiplier * e.getAmount()) );
    }

}
