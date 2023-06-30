package com.davidout.customenchants.enchantments.armor;

import com.davidout.api.custom.enchantment.EnchantmentDetails;
import com.davidout.api.custom.enchantment.EnchantmentManager;
import com.davidout.api.custom.event.ArmorDequipEvent;
import com.davidout.api.custom.event.ArmorEquipEvent;
import com.davidout.api.custom.enchantment.CustomEnchantment;
import com.davidout.api.enums.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class FlameGuard extends CustomEnchantment {

        public FlameGuard() {
            super(new EnchantmentDetails("flame_guard", 1, "Protects your from fire damage.", EnchantmentTarget.LEGGINGS));
        }

        @Override
        public List<Class<? extends Event>> getEvents() {
            return Arrays.asList(ArmorEquipEvent.class, ArmorDequipEvent.class, EntityDamageByBlockEvent.class);
        }

        @Override
        public void onAction(Event event) {
            if(event instanceof EntityDamageEvent) {
                EntityDamageEvent e = (EntityDamageEvent) event;
                if(!(e.getEntity() instanceof LivingEntity)) return;
                LivingEntity et = (LivingEntity) e.getEntity();
                if(!EnchantmentManager.containsEnchantment(this, et.getEquipment().getBoots())) return;
                if(!e.getCause().equals(EntityDamageEvent.DamageCause.LAVA) && !e.getCause().equals(EntityDamageEvent.DamageCause.LAVA) && !e.getCause().equals(EntityDamageEvent.DamageCause.HOT_FLOOR) && ! e.getCause().equals(EntityDamageEvent.DamageCause.FIRE) && !e.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK)) return;;
                e.setCancelled(true);
                return;
            }

            if(event instanceof ArmorEquipEvent) {
                ArmorEquipEvent e = (ArmorEquipEvent) event;
                if(!EnchantmentManager.containsEnchantment(this, e.getEquipedArmor())) return;
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
                return;
            }

            if(!(event instanceof ArmorDequipEvent)) return;
            ArmorDequipEvent e = (ArmorDequipEvent) event;
            e.getPlayer().removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        }

}
