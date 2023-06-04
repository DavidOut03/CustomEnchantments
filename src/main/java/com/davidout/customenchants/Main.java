package com.davidout.customenchants;

import com.davidout.api.MinecraftPlugin;
import com.davidout.api.custom.command.CustomCommand;
import com.davidout.api.custom.file.PluginFile;
import com.davidout.api.utillity.TextUtils;
import com.davidout.customenchants.enchantments.CustomEnchantmentManager;
import com.davidout.customenchants.gui.EnchanterGUI;
import com.davidout.customenchants.listener.ClickListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.List;

public final class Main extends MinecraftPlugin {

    @Override
    public void onStartup() {
        Bukkit.getConsoleSender().sendMessage(TextUtils.formatColorCodes("&cHellow there"));

       registerEnchantments();
       registerGUIS();
    }

    @Override
    public void onShutdown() {

    }

    @Override
    public List<Listener> registerEvents() {
        return Arrays.asList(new TestListener(), new ClickListener());
    }

    @Override
    public List<CustomCommand> registerCommands() {
        return Arrays.asList();
    }

    @Override
    public List<PluginFile> filesToCreate() {
        return Arrays.asList();
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

                CustomEnchantmentManager.speed,
                CustomEnchantmentManager.flameguard,
                CustomEnchantmentManager.jellyLegs,
                CustomEnchantmentManager.harmony,

                CustomEnchantmentManager.venomous,
                CustomEnchantmentManager.attackSpeed,
                CustomEnchantmentManager.soulReaper
        ).forEach(customEnchantment -> {
            getEnchantmentManager().addEnchantment(customEnchantment);
        });
    }

    public void registerGUIS() {
        getGuiManager().addGUI(new EnchanterGUI());
    }
}
