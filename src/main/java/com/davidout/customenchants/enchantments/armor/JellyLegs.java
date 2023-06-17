package com.davidout.customenchants.enchantments.armor;

import com.davidout.api.custom.enchantment.CustomEnchantment;
import com.davidout.api.custom.enchantment.EnchantmentManager;
import com.davidout.api.enums.EnchantmentTarget;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JellyLegs extends CustomEnchantment {
    public JellyLegs(String name, int maxLevel) {
        super(name, maxLevel, EnchantmentTarget.BOOTS);
    }

    @Override
    public List<Class<? extends Event>> getEvents() {
        return Arrays.asList(PlayerMoveEvent.class, EntityDamageEvent.class);
    }

    @Override
    public void onAction(Event event) {
        if(!(event instanceof EntityDamageEvent)) return;
            EntityDamageEvent e = (EntityDamageEvent) event;
            if(!(e.getEntity() instanceof LivingEntity)) return;
            LivingEntity et = (LivingEntity) e.getEntity();
            if(et.getEquipment().getBoots() == null) return;
            if(!EnchantmentManager.containsEnchantment(this, et.getEquipment().getBoots())) return;
            e.setCancelled(true);

            if(et instanceof Player &&  ((Player) et).isSneaking()) return;
            applyBounce(et);
    }



    private void applyBounce(Entity entity) {
        Vector bounceVector = entity.getLocation().getDirection().multiply(0.6).setY(1.2);

        entity.setVelocity(bounceVector);
        entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_SLIME_JUMP, 1.0f, 1.0f);
    }


}
