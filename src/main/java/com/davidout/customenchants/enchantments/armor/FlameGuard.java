package com.davidout.customenchants.enchantments.armor;

import com.davidout.api.custom.enchantment.EnchantmentManager;
import com.davidout.api.custom.event.ArmorDequipEvent;
import com.davidout.api.custom.event.ArmorEquipEvent;
import com.davidout.api.custom.enchantment.CustomEnchantment;
import com.davidout.api.enums.EnchantmentTarget;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class FlameGuard extends CustomEnchantment {

        public FlameGuard(String name, int maxLevel) {
            super(name, maxLevel, EnchantmentTarget.LEGGINGS);
        }

        @Override
        public List<Class<? extends Event>> getEvents() {
            return Arrays.asList(ArmorEquipEvent.class, ArmorDequipEvent.class);
        }

        @Override
        public void onAction(Event event) {
            if(event instanceof ArmorEquipEvent) {
                ArmorEquipEvent e = (ArmorEquipEvent) event;
                if(!EnchantmentManager.containsEnchantment(this, e.getEquipedArmor())) return;
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 99999, 0));
                return;
            }

            if(!(event instanceof ArmorDequipEvent)) return;
            ArmorDequipEvent e = (ArmorDequipEvent) event;
            e.getPlayer().removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        }

}
