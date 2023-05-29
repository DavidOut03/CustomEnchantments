package com.davidout.customenchants;

import com.davidout.api.MinecraftPlugin;
import com.davidout.api.command.CustomCommand;
import com.davidout.api.utillity.TextUtils;
import com.davidout.customenchants.enchantments.CustomEnchantmentManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.List;

public final class Main extends MinecraftPlugin {

    @Override
    public void onStartup() {
        Bukkit.getConsoleSender().sendMessage(TextUtils.formatColorCodes("&cHellow there"));

       registerEnchantments();
    }

    @Override
    public void onShutdown() {

    }

    @Override
    public List<Listener> registerEvents() {
        return Arrays.asList(new TestListener());
    }

    @Override
    public List<CustomCommand> registerCommands() {
        return Arrays.asList();
    }

    public void registerEnchantments() {
        Arrays.asList(
                CustomEnchantmentManager.telepathy,
                CustomEnchantmentManager.autoRepair,
                CustomEnchantmentManager.autoSmelt,
                CustomEnchantmentManager.speed,
                CustomEnchantmentManager.flameguard,
                CustomEnchantmentManager.multiblock,
                CustomEnchantmentManager.lumberjack,
                CustomEnchantmentManager.experience,
                CustomEnchantmentManager.quickHarvest,
                CustomEnchantmentManager.jellyLegs,
                CustomEnchantmentManager.venomoues
        ).forEach(customEnchantment -> {
            getEnchantmentManager().addEnchantment(customEnchantment);
        });
    }
}
