package com.davidout.customenchants;

import com.davidout.api.MinecraftPlugin;
import com.davidout.api.command.CustomCommand;
import com.davidout.api.utillity.TextUtils;
import com.davidout.customenchants.enchantments.all.AutoRepair;
import com.davidout.customenchants.enchantments.armor.FlameGuard;
import com.davidout.customenchants.enchantments.armor.Speed;
import com.davidout.customenchants.enchantments.tools.AutoSmelt;
import com.davidout.customenchants.enchantments.tools.Lumberjack;
import com.davidout.customenchants.enchantments.tools.Multiblock;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.EnchantmentTarget;
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
                // enchantment for all items
                new AutoRepair("autorepair", 1),

                // enchantments for tools
                new Multiblock("multiblock", 3, EnchantmentTarget.TOOL),
                new Lumberjack("lumberjack", 1),
                new AutoSmelt("autosmelt", 1),


                // enchantments for armor
                new Speed("speed", 2, EnchantmentTarget.ARMOR_FEET),
                new FlameGuard("flame_guard", 1)

        ).forEach(customEnchantment -> {
            getEnchantmentManager().addEnchantment(customEnchantment);
        });
    }
}
