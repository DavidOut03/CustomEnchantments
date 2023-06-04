package com.davidout.customenchants.enchantments.weapons;

import com.davidout.api.custom.enchantment.CustomEnchantment;
import com.davidout.api.enums.EnchantmentTarget;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Arrays;
import java.util.List;

public class SoulReaper extends CustomEnchantment {
    public SoulReaper(String name, int maxLevel) {
        super(name, maxLevel, Arrays.asList(EnchantmentTarget.SWORD, EnchantmentTarget.AXE));
    }

    @Override
    public List<Class<? extends Event>> getEvents() {
        return Arrays.asList(EntityDamageByEntityEvent.class);
    }

    @Override
    public void onAction(Event event) {
        if(!(event instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;

        if(!(e.getDamager() instanceof Player)) return;
        Player player = (Player) e.getDamager();

        if(!player.getItemInHand().containsEnchantment(this) || !(e.getEntity() instanceof Monster)) return;
        e.setDamage(e.getDamage() * (1.2 * player.getItemInHand().getEnchantmentLevel(this)));
    }
}
