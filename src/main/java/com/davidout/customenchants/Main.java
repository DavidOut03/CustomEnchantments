package com.davidout.customenchants;

import com.davidout.api.MinecraftPlugin;
import com.davidout.api.custom.command.CustomCommand;
import com.davidout.api.custom.file.PluginFile;
import com.davidout.api.custom.language.LanguageManager;
import com.davidout.api.utillity.text.TextUtils;
import com.davidout.customenchants.commands.KeyRecipeCommand;
import com.davidout.customenchants.enchantments.CustomEnchantmentManager;
import com.davidout.customenchants.enchantments.Enchanter;
import com.davidout.customenchants.gui.EnchanterGUI;
import com.davidout.customenchants.gui.EnchantmentsGUI;
import com.davidout.customenchants.gui.RecipeGui;
import com.davidout.customenchants.listener.ClickListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Arrays;
import java.util.List;

public final class Main extends MinecraftPlugin {

    @Override
    public void onStartup() {
        Bukkit.getConsoleSender().sendMessage(TextUtils.formatColorCodes(LanguageManager.getTranslation("onEnable")));

       registerEnchantments();
       registerGUIS();
       registerCustomRecipes();
    }

    @Override
    public void onShutdown() {
        Bukkit.getConsoleSender().sendMessage(TextUtils.formatColorCodes(LanguageManager.getTranslation("onDisable")));
    }

    @Override
    public List<Listener> registerEvents() {
        return Arrays.asList(new ClickListener(), new EnchanterGUI());
    }

    @Override
    public List<CustomCommand> registerCommands() {
        return Arrays.asList(new KeyRecipeCommand());
    }





    public void registerEnchantments() {
        Arrays.asList(
                CustomEnchantmentManager.autoRepair,
                CustomEnchantmentManager.soulBound,

                CustomEnchantmentManager.multiblock,
                CustomEnchantmentManager.lumberjack,
                CustomEnchantmentManager.autoSmelt,
                CustomEnchantmentManager.telepathy,
                CustomEnchantmentManager.experience,
                CustomEnchantmentManager.quickHarvest,
                CustomEnchantmentManager.haste,

                CustomEnchantmentManager.speed,
                CustomEnchantmentManager.flameguard,
                CustomEnchantmentManager.jellyLegs,
                CustomEnchantmentManager.harmony,

                CustomEnchantmentManager.venomous,
                CustomEnchantmentManager.attackSpeed,
                CustomEnchantmentManager.soulReaper,
                CustomEnchantmentManager.trophy
        ).forEach(customEnchantment -> {
            getEnchantmentManager().addEnchantment(customEnchantment);
        });
    }

    public void registerGUIS() {
        getGuiManager().addGUI(new EnchanterGUI());
        getGuiManager().addGUI(new EnchantmentsGUI());
        getGuiManager().addGUI(new RecipeGui());
    }

    public void registerCustomRecipes() {
        ItemStack keyResult = Enchanter.getKeyItem();
        ShapedRecipe keyRecipe = new ShapedRecipe(new NamespacedKey(getPlugin(), "enchanter_key"), keyResult);
        keyRecipe.shape(
                "---",
                "+*+",
                "==="
        );

        keyRecipe.setIngredient('-', Material.PAPER);
        keyRecipe.setIngredient('+', Material.LAPIS_LAZULI);
        keyRecipe.setIngredient('*', Material.DIAMOND);
        keyRecipe.setIngredient('=', Material.LEATHER);

        Bukkit.addRecipe(keyRecipe);
    }
}
