package com.davidout.customenchants.enchantments.armor;

import com.davidout.api.custom.enchantment.EnchantmentDetails;
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

public class Speed extends CustomEnchantment {
    public Speed() {
        super(new EnchantmentDetails("speed", 2, "Receive a speed boost with these boots.", EnchantmentTarget.BOOTS));
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
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, e.getEquipedArmor().getEnchantments().get(this) - 1));
            return;
        }

        if(!(event instanceof ArmorDequipEvent)) return;
        ArmorDequipEvent e = (ArmorDequipEvent) event;
        if(!EnchantmentManager.containsEnchantment(this, e.getDequipedArmor())) return;
        e.getPlayer().removePotionEffect(PotionEffectType.SPEED);
    }
}
