package com.davidout.customenchants.enchantments.armor;

import com.davidout.api.customevent.ArmorDequipEvent;
import com.davidout.api.customevent.ArmorEquipEvent;
import com.davidout.api.enchantment.CustomEnchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class Speed extends CustomEnchantment {
    public Speed(String name, int maxLevel) {
        super(name, maxLevel, EnchantmentTarget.ARMOR_FEET);
    }

    @Override
    public List<Class<? extends Event>> getEvents() {
        return Arrays.asList(ArmorEquipEvent.class, ArmorDequipEvent.class);
    }

    @Override
    public void onAction(Event event) {

        if(event instanceof ArmorEquipEvent) {
            ArmorEquipEvent e = (ArmorEquipEvent) event;
            if(!e.getEquipedArmor().containsEnchantment(this)) return;
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, e.getEquipedArmor().getEnchantmentLevel(this)));
            return;
        }

        if(!(event instanceof ArmorDequipEvent)) return;
        ArmorDequipEvent e = (ArmorDequipEvent) event;
        if(e.getDequipedArmor().containsEnchantment(this)) return;
        e.getPlayer().removePotionEffect(PotionEffectType.SPEED);
    }
}
