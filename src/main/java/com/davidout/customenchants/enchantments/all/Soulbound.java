package com.davidout.customenchants.enchantments.all;

import com.davidout.api.custom.enchantment.CustomEnchantment;
import com.davidout.customenchants.Main;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.*;

public class Soulbound extends CustomEnchantment {
    public Soulbound(String name, int maxLevel) {
        super(name, maxLevel);
    }

    @Override
    public List<Class<? extends Event>> getEvents() {
        return Arrays.asList(PlayerRespawnEvent.class, PlayerDeathEvent.class);
    }

    @Override
    public void onAction(Event event) {
        if(event instanceof PlayerDeathEvent) {
            PlayerDeathEvent e = (PlayerDeathEvent) event;

            List<ItemStack> drops = new ArrayList<>(e.getDrops());
            List<ItemStack> returned = new ArrayList<>();

            drops.forEach(itemStack -> {
                if(!itemStack.containsEnchantment(this)) return;
                e.getDrops().remove(itemStack);
               returned.add(itemStack);
            });

            e.getEntity().setMetadata("droppedItems", new FixedMetadataValue(Main.getPlugin(), drops));
            return;
        }

        if(!(event instanceof PlayerRespawnEvent)) return;
        PlayerRespawnEvent e = (PlayerRespawnEvent) event;

        if(e.getPlayer().getInventory().getContents() == null) return;
        MetadataValue value = e.getPlayer().getMetadata("droppedItems").get(0);
        List<ItemStack> items = (List<ItemStack>) value.value();

        items.forEach(itemStack -> {
            int enchantmentLevel = itemStack.getEnchantmentLevel(this);
            double restorationChance = 0.4 * enchantmentLevel;
            double random = Math.random();


            if (random >= restorationChance) return;
            e.getPlayer().getInventory().addItem(itemStack);
        });


        // Remove the metadata
        e.getPlayer().removeMetadata("droppedItems", Main.getPlugin());
    }
}