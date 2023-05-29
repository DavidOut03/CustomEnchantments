package com.davidout.customenchants.enchantments;

import com.davidout.api.enchantment.CustomEnchantment;
import com.davidout.customenchants.enchantments.all.AutoRepair;
import com.davidout.customenchants.enchantments.armor.FlameGuard;
import com.davidout.customenchants.enchantments.armor.JellyLegs;
import com.davidout.customenchants.enchantments.armor.Speed;
import com.davidout.customenchants.enchantments.tools.*;
import com.davidout.customenchants.enchantments.weapons.Venomous;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.ArrayList;
import java.util.List;

public class CustomEnchantmentManager {

    // tools
    public static final CustomEnchantment autoRepair = new AutoRepair("autorepair", 3);
    public static final CustomEnchantment multiblock = new Multiblock("multiblock", 2);
    public static final CustomEnchantment lumberjack = new Lumberjack("lumberjack", 1);
    public static final CustomEnchantment autoSmelt = new AutoSmelt("autosmelt", 1);
    public static final CustomEnchantment telepathy = new Telepathy("telepathy", 1);
    public static final CustomEnchantment experience = new Experience("experience", 3);
    public static final CustomEnchantment quickHarvest = new QuickHarvest("quick_harvest", 1);


    // armour
    public static final CustomEnchantment speed = new Speed("speed", 1);
    public static final CustomEnchantment flameguard = new FlameGuard("flame_guard", 1);
    public static final CustomEnchantment jellyLegs = new JellyLegs("jelly_legs", 1);

    // weapons
    public static final CustomEnchantment venomoues = new Venomous("venomous", 2);



    public static void breakBlock(Block block, Player player, ItemStack tool) {
        List<ItemStack> drops =  (tool.containsEnchantment(autoSmelt))? AutoSmelt.breakItemWithAutoSmelt(block, player): new ArrayList<>(block.getDrops(tool));
        block.setType(Material.AIR);

        if(tool != null) tool.setDurability((short) (tool.getDurability() + getUnbreakingDamage(tool.getEnchantmentLevel(Enchantment.DURABILITY))));


        if(tool.containsEnchantment(telepathy)) {
            drops.forEach(itemStack -> Telepathy.teleportDropToInventory(block.getLocation(), itemStack, player));
            return;
        }

       drops.forEach(itemStack ->  block.getWorld().dropItemNaturally(block.getLocation(), itemStack));
    }

    private static int getUnbreakingDamage(int unbreakingLevel) {
        int durabilityDamage = 1;

        if(unbreakingLevel <= 0) return durabilityDamage;
            double reductionChance = 1.0 / (unbreakingLevel + 1);
            if (Math.random() >= reductionChance) return durabilityDamage;
                durabilityDamage--;


        return durabilityDamage;
    }

}

