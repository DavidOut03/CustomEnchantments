package com.davidout.customenchants.enchantments.all;

import com.davidout.api.enchantment.CustomEnchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class AutoRepair extends CustomEnchantment {
    public AutoRepair(String name, int maxLevel) {
        super(name, maxLevel);
    }

    @Override
    public List<Class<? extends Event>> getEvents() {
        return Arrays.asList(PlayerMoveEvent.class);
    }

    @Override
    public void onAction(Event event) {
        if(!(event instanceof PlayerMoveEvent)) return;
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        Player player = e.getPlayer();

        if(!(e.getTo().getBlockX() != e.getFrom().getBlockX() || e.getTo().getBlockZ() != e.getFrom().getBlockZ())) return;


        for (ItemStack item : player.getInventory().getContents()) {
            if(item == null || !item.containsEnchantment(this)) continue;
            item.setDurability((short) ( item.getDurability() -  .01));
        }
    }
}
