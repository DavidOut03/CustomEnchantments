package com.davidout.customenchants.enchantments.weapons;

import com.davidout.api.custom.enchantment.CustomEnchantment;
import com.davidout.api.custom.enchantment.EnchantmentDetails;
import com.davidout.api.custom.enchantment.EnchantmentManager;
import com.davidout.api.enums.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Venomous extends CustomEnchantment {
    public Venomous() {
        super(new EnchantmentDetails("venomous", 2, "Applies a poison effect when hitting an enemy.", Arrays.asList(EnchantmentTarget.SWORD, EnchantmentTarget.AXE) ));
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
        if(!EnchantmentManager.containsEnchantment(this, da.getEquipment().getItemInHand())) return;
        et.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 40, da.getEquipment().getItemInHand().getEnchantments().get(this) - 1));
     }
}
