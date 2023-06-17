package com.davidout.customenchants.enchantments.weapons;

import com.davidout.api.custom.enchantment.CustomEnchantment;
import com.davidout.api.custom.enchantment.EnchantmentManager;
import com.davidout.api.enums.EnchantmentTarget;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class AttackSpeed extends CustomEnchantment {

    public AttackSpeed(String name, int maxLevel) {
        super(name, maxLevel, Arrays.asList(EnchantmentTarget.SWORD, EnchantmentTarget.AXE));
    }

    @Override
    public List<Class<? extends Event>> getEvents() {
        return Arrays.asList(PlayerItemHeldEvent.class);
    }

    @Override
    public void onAction(Event event) {
        if(!(event instanceof PlayerItemHeldEvent)) return;
        PlayerItemHeldEvent e = (PlayerItemHeldEvent) event;
        ItemStack newItem = e.getPlayer().getInventory().getItem(e.getNewSlot());
        ItemStack oldItem = e.getPlayer().getInventory().getItem(e.getPreviousSlot());

        AttributeInstance attribute = e.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        if(EnchantmentManager.containsEnchantment(this, oldItem))  attribute.getModifiers().forEach(attribute::removeModifier);
        if(!EnchantmentManager.containsEnchantment(this, newItem)) return;
        int level = newItem.getEnchantmentLevel(this);
        
        double attackSpeed = 4 * level; // Set your desired attack speed here
        attribute.addModifier(new AttributeModifier("generic.attackSpeed", attackSpeed - 4, AttributeModifier.Operation.ADD_NUMBER));

    }
    
}
