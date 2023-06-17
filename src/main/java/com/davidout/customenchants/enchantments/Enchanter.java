package com.davidout.customenchants.enchantments;

import com.davidout.api.custom.enchantment.CustomEnchantment;
import com.davidout.api.custom.enchantment.EnchantmentManager;
import com.davidout.api.enums.EnchantmentTarget;
import com.davidout.api.utillity.TextUtils;
import com.davidout.api.utillity.item.ItemCreator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class Enchanter {

    public static ItemStack getKeyItem() {
        return ItemCreator.createItem(Material.WRITTEN_BOOK, "&5&lEnchanters key", Arrays.asList(" ", "&dClick with this item on a enchantment table,", "&dto unlock the custom enchantments."));
    }

    public static ItemStack getBookItem() {
        return ItemCreator.createItem(Material.BOOK, "&eEnchanted Book", Arrays.asList(" ", "&7Click with this book on a item to apply it."));
    }

    public static void enchantItem(EnchantmentType type, ItemStack itemStack) {
        Random random = new Random();
        int amount = random.nextInt((type == EnchantmentType.COMMON) ? 1 : (type == EnchantmentType.EPIC) ? 2 : 3) + 1;

        Map<CustomEnchantment,Integer> addedEnchantments = getRandomEnchantments(type, amount, itemStack);
        addedEnchantments.forEach((customEnchantment, integer) -> {
            EnchantmentManager.addCustomEnchantment(itemStack, customEnchantment, integer);
        });

        if(itemStack.getType() != Material.BOOK) return;
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = meta.getLore();
        lore.addAll(Arrays.asList(" ", TextUtils.formatColorCodes("&9Can be applied to: " + Enchanter.enchantmentTargets(new ArrayList<>(addedEnchantments.keySet())))));
        meta.setLore(lore);
        itemStack.setItemMeta(meta);

    }

    public static String enchantmentTargets(CustomEnchantment enchantment) {
        String targets = "";

        for (EnchantmentTarget target : enchantment.getTargets()) {
            String name = target.name().toLowerCase();
            targets += (targets.equalsIgnoreCase("")) ? name : " , " + name;
        }

        return targets;
    }

    public static String enchantmentTargets(List<CustomEnchantment> enchantments) {
        String targets = "";

        for (CustomEnchantment customEnchantment : enchantments) {
            for (EnchantmentTarget target : customEnchantment.getTargets()) {
                if(targets.contains(target.name())) continue;
                String name = target.name().toLowerCase();
                targets += (targets.equalsIgnoreCase("")) ? name : " , " + name;
            }
        }


        return targets;
    }


    private static Map<CustomEnchantment, Integer> getRandomEnchantments(EnchantmentType type, int amount, ItemStack item) {
        Map<CustomEnchantment, List<Integer>> possibleEnchantments = (type == EnchantmentType.COMMON) ? getCommonEnchantments() : (type == EnchantmentType.EPIC) ? getEpicEnchantments() : getLegendaryEnchantments();
        Map<CustomEnchantment, Integer> returned = new HashMap<>();


        for(int i = 0; i < amount; i++) {
            CustomEnchantment randomEnchantment = getRandomEnchantment(type, item);
            Random random = new Random();
            List<Integer> levels = possibleEnchantments.get(randomEnchantment);
            int randomLevel = levels.get(random.nextInt(levels.size()));
            returned.put(randomEnchantment, randomLevel);
        }

        return returned;
    }

    private static CustomEnchantment getRandomEnchantment(EnchantmentType type, ItemStack itemStack) {
        List<CustomEnchantment> enchantmentList = new ArrayList<>((type == EnchantmentType.COMMON) ? getCommonEnchantments().keySet() : (type == EnchantmentType.EPIC) ? getEpicEnchantments().keySet() : getLegendaryEnchantments().keySet());
        CustomEnchantment randomEnchantment = enchantmentList.get(new Random().nextInt(enchantmentList.size()));

        for (EnchantmentTarget target : randomEnchantment.getTargets()) {
            if(!target.includes(itemStack)) continue;
            return randomEnchantment;
        }

        return getRandomEnchantment(type, itemStack);
    }

    public static List<CustomEnchantment> getPossibleEnchantments() {
        return EnchantmentManager.getCustomEnchants();
    }


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
        enchantments.put(CustomEnchantmentManager.trophy, Arrays.asList(1));
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
        enchantments.put(CustomEnchantmentManager.trophy, Arrays.asList(2));


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
        enchantments.put(CustomEnchantmentManager.trophy, Arrays.asList(3));

        return enchantments;
    }



}
