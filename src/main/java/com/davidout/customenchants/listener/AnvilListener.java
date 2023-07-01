package com.davidout.customenchants.listener;

import com.davidout.api.custom.enchantment.CustomEnchantment;
import com.davidout.api.custom.enchantment.EnchantmentManager;
import com.davidout.customenchants.enchantments.Enchanter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class AnvilListener implements Listener {


    @EventHandler
    public void onGrind(PrepareGrindstoneEvent e) {
        ItemStack itemStack = e.getInventory().getItem(0);
        if(e.getResult() == null || itemStack == null || itemStack.getType() == Material.AIR) return;
        if(e.getInventory().getItem(0) != null && !containsCustomEnchantments(itemStack)) return;
        ItemStack results = e.getResult();
        ItemMeta meta = results.getItemMeta();
        if(meta == null) return;
        meta.setLore(new ArrayList<>());
        results.setItemMeta(meta);
        e.setResult(results);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAnvil(PrepareAnvilEvent e) {
        Map<CustomEnchantment, Integer> enchantments = getCombinedEnchantments(e.getInventory().getItem(0), e.getInventory().getItem(1));
        ItemStack slot1 = e.getInventory().getItem(0);
        ItemStack slot2 = e.getInventory().getItem(1);

        if(slot1 != null && slot2 != null && !getCombinedEnchantments(slot1, slot2).isEmpty() && slot1.getType().equals(slot2.getType())) {
            ItemStack itemStack = slot1.clone();
            itemStack.setDurability((short)0);
            enchantments.forEach((customEnchantment, level) -> EnchantmentManager.addCustomEnchantment(itemStack, customEnchantment, level));
            e.setResult(itemStack);
            return;
        }

        if(slot1 == null || e.getResult() == null || !EnchantmentManager.containsCustomEnchantment(slot1)) return;
        enchantments.forEach((customEnchantment, level) -> EnchantmentManager.addCustomEnchantment(e.getResult(), customEnchantment, level));
    }

    private boolean containsCustomEnchantments(ItemStack itemStack) {
        return itemStack.getEnchantments().entrySet().stream().anyMatch(enchantmentIntegerEntry -> enchantmentIntegerEntry.getKey() instanceof CustomEnchantment);
    }

    private Map<CustomEnchantment, Integer> getCombinedEnchantments(ItemStack itemStack1, ItemStack itemStack2) {
        Map<CustomEnchantment, Integer> enchantments = new HashMap<>(EnchantmentManager.getCustomEnchantments(itemStack1));

        EnchantmentManager.getCustomEnchantments(itemStack2).forEach((customEnchantment, integer) -> {
            if(!enchantments.containsKey(customEnchantment)) {
                enchantments.put(customEnchantment, integer);
                return;
            }

            if(enchantments.get(customEnchantment) >= integer) return;
            enchantments.put(customEnchantment, integer);
        });


        return enchantments;
    }
}
