package com.davidout.customenchants.enchantments;

import com.davidout.api.custom.enchantment.CustomEnchantment;
import com.davidout.api.custom.enchantment.EnchantmentManager;
import com.davidout.customenchants.enchantments.all.AutoRepair;
import com.davidout.customenchants.enchantments.all.Soulbound;
import com.davidout.customenchants.enchantments.armor.FlameGuard;
import com.davidout.customenchants.enchantments.armor.Harmony;
import com.davidout.customenchants.enchantments.armor.JellyLegs;
import com.davidout.customenchants.enchantments.armor.Speed;
import com.davidout.customenchants.enchantments.tools.*;
import com.davidout.customenchants.enchantments.weapons.AttackSpeed;
import com.davidout.customenchants.enchantments.weapons.SoulReaper;
import com.davidout.customenchants.enchantments.weapons.Trophy;
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

    // all
    public static final CustomEnchantment autoRepair = new AutoRepair();
    public static final CustomEnchantment soulBound = new Soulbound();

    // tools

    public static final CustomEnchantment multiblock = new Multiblock();
    public static final CustomEnchantment lumberjack = new Lumberjack();
    public static final CustomEnchantment autoSmelt = new AutoSmelt();
    public static final CustomEnchantment telepathy = new Telepathy();
    public static final CustomEnchantment experience = new Experience();
    public static final CustomEnchantment quickHarvest = new QuickHarvest();


    // armour
    public static final CustomEnchantment speed = new Speed();
    public static final CustomEnchantment flameguard = new FlameGuard();
    public static final CustomEnchantment jellyLegs = new JellyLegs();
    public static final CustomEnchantment harmony = new Harmony();

    // weapons
    public static final CustomEnchantment venomous = new Venomous();
    public static final CustomEnchantment attackSpeed = new AttackSpeed();
    public static final CustomEnchantment soulReaper = new SoulReaper();
    public static final CustomEnchantment trophy = new Trophy();



    public static void breakBlock(Block block, Player player, ItemStack tool) {
        List<ItemStack> drops =  (EnchantmentManager.containsEnchantment(autoSmelt, tool))? AutoSmelt.breakItemWithAutoSmelt(block, player): new ArrayList<>(block.getDrops(tool));
        block.setType(Material.AIR);

        if(tool != null) tool.setDurability((short) (tool.getDurability() + getUnbreakingDamage(tool.getEnchantmentLevel(Enchantment.DURABILITY))));
        if(EnchantmentManager.containsEnchantment(telepathy, tool)) {
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

