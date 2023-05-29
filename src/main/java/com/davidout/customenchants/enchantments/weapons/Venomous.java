package com.davidout.customenchants.enchantments.weapons;

import com.davidout.api.enchantment.CustomEnchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.List;

public class Venomous extends CustomEnchantment {
    public Venomous(String name, int maxLevel) {
        super(name, maxLevel, EnchantmentTarget.WEAPON);
    }

    @Override
    public List<Class<? extends Event>> getEvents() {
        return Collections.singletonList(EntityDamageByEntityEvent.class);
    }

    @Override
    public void onAction(Event event) {
        if(!(event instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if(!(e.getDamager() instanceof LivingEntity) || !(e.getEntity() instanceof LivingEntity)) return;
        LivingEntity da = (LivingEntity) e.getDamager();
        LivingEntity et = (LivingEntity) e.getEntity();
        if(da.getEquipment().getItemInHand() == null) return;
        if(!da.getEquipment().getItemInHand().containsEnchantment(this)) return;
        et.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 5, da.getEquipment().getItemInHand().getEnchantmentLevel(this) - 1));
     }
}
