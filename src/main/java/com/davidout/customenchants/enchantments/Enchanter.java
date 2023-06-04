package com.davidout.customenchants.enchantments;

import com.davidout.api.custom.enchantment.CustomEnchantment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Enchanter {

    private static Map<CustomEnchantment, List<Integer>> getCommonEnchantments() {
        Map<CustomEnchantment, List<Integer>> enchantments = new HashMap<>();

        // all
        enchantments.put(CustomEnchantmentManager.autoRepair, Arrays.asList(1));

        // tools
        enchantments.put(CustomEnchantmentManager.lumberjack, Arrays.asList(1));
        enchantments.put(CustomEnchantmentManager.autoSmelt, Arrays.asList(1));
        enchantments.put(CustomEnchantmentManager.telepathy, Arrays.asList(1));
        enchantments.put(CustomEnchantmentManager.experience, Arrays.asList(1));
        enchantments.put(CustomEnchantmentManager.quickHarvest, Arrays.asList(1));

        //armour
        enchantments.put(CustomEnchantmentManager.speed, Arrays.asList(1));


        // weapons
        enchantments.put(CustomEnchantmentManager.venomous, Arrays.asList(1));
        enchantments.put(CustomEnchantmentManager.attackSpeed, Arrays.asList(1));
        enchantments.put(CustomEnchantmentManager.soulReaper, Arrays.asList(1));
        return enchantments;
    }

    private static Map<CustomEnchantment, List<Integer>> getEpicEnchantments() {
        Map<CustomEnchantment, List<Integer>> enchantments = new HashMap<>();

        // all
        enchantments.put(CustomEnchantmentManager.autoRepair, Arrays.asList(2));

        // tools
        enchantments.put(CustomEnchantmentManager.multiblock, Arrays.asList(1));
        enchantments.put(CustomEnchantmentManager.lumberjack, Arrays.asList(1));
        enchantments.put(CustomEnchantmentManager.experience, Arrays.asList(2));

        // armour
        enchantments.put(CustomEnchantmentManager.speed, Arrays.asList(1));
        enchantments.put(CustomEnchantmentManager.jellyLegs, Arrays.asList(1));
        enchantments.put(CustomEnchantmentManager.flameguard, Arrays.asList(1));
        enchantments.put(CustomEnchantmentManager.harmony, Arrays.asList(1));

        // weapons
        enchantments.put(CustomEnchantmentManager.venomous, Arrays.asList(1,2));
        enchantments.put(CustomEnchantmentManager.attackSpeed, Arrays.asList(2));
        enchantments.put(CustomEnchantmentManager.soulReaper, Arrays.asList(2,3));


        return enchantments;

    }

    private static Map<CustomEnchantment, List<Integer>> getLegendaryEnchantments() {
        Map<CustomEnchantment, List<Integer>> enchantments = new HashMap<>();

        // all
        enchantments.put(CustomEnchantmentManager.autoRepair, Arrays.asList(3));


        // tools
        enchantments.put(CustomEnchantmentManager.multiblock, Arrays.asList(1, 2));
        enchantments.put(CustomEnchantmentManager.experience, Arrays.asList(2, 3));

        //armour
        enchantments.put(CustomEnchantmentManager.speed, Arrays.asList(2));
        enchantments.put(CustomEnchantmentManager.harmony, Arrays.asList(2));


        // weapons
        enchantments.put(CustomEnchantmentManager.venomous, Arrays.asList(2));
        enchantments.put(CustomEnchantmentManager.attackSpeed, Arrays.asList(3));
        enchantments.put(CustomEnchantmentManager.soulReaper, Arrays.asList(4));

        return enchantments;
    }



}
