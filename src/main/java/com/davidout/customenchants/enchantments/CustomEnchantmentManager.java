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
import java.util.Random;

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
    public static final CustomEnchantment haste = new Haste();


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
        if(block.getType().equals(Material.BEDROCK)) return;
        List<ItemStack> drops =  (EnchantmentManager.containsEnchantment(autoSmelt, tool))? AutoSmelt.breakItemWithAutoSmelt(block, player): new ArrayList<>(block.getDrops(tool));

        if(EnchantmentManager.containsEnchantment(telepathy, tool)) {
            drops.forEach(itemStack -> Telepathy.teleportDropToInventory(block.getLocation(), itemStack, player));
            return;
        }


        if(!block.getType().name().toUpperCase().contains("_LEAVE") && !block.getType().name().equalsIgnoreCase("AIR")) {
            drops.forEach(itemStack ->  block.getWorld().dropItemNaturally(block.getLocation(), itemStack));
            removeDurability(tool);
        }


        block.setType(Material.AIR);
    }

    private static void removeDurability(ItemStack itemStack) {
        if(itemStack == null || !(itemStack.getItemMeta() instanceof Damageable)) return;
        Damageable damageable = (Damageable) itemStack.getItemMeta();

        int level = (itemStack.getEnchantments().get(Enchantment.DURABILITY) == null) ? 0 : itemStack.getEnchantments().get(Enchantment.DURABILITY);
        boolean remove = shouldRemoveDurrabillity(level);

        if(!remove) return;
        int damage = damageable.getDamage();
        damageable.setDamage(damage + 1);
        itemStack.setItemMeta(damageable);
    }


    private static boolean shouldRemoveDurrabillity(int unbreakingLevel) {
        if(unbreakingLevel <= 0) return true;
            double reductionChance = 1.0 / (unbreakingLevel + 10);
            double d = new Random().nextDouble();
        return d <= reductionChance;
    }

}

