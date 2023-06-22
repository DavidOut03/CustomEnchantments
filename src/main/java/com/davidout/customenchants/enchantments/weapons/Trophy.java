package com.davidout.customenchants.enchantments.weapons;

import com.davidout.api.custom.enchantment.CustomEnchantment;
import com.davidout.api.custom.enchantment.EnchantmentDetails;
import com.davidout.api.custom.enchantment.EnchantmentManager;
import com.davidout.api.enums.EnchantmentTarget;
import com.davidout.api.utillity.item.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Trophy extends CustomEnchantment {
    public Trophy() {
        super(new EnchantmentDetails("trophy", 3, "Creates the chance to receive a mob head when killing a mob.", Arrays.asList(EnchantmentTarget.SWORD, EnchantmentTarget.AXE)));
    }

    @Override
    public List<Class<? extends Event>> getEvents() {
        return Arrays.asList(EntityDeathEvent.class);
    }

    @Override
    public void onAction(Event event) {
        if(!(event instanceof EntityDeathEvent) || ((EntityDeathEvent) event).getEntity().getKiller() == null) return;
        LivingEntity entity = ((EntityDeathEvent) event).getEntity();
        Player killer = entity.getKiller();

        if(!EnchantmentManager.containsEnchantment(this, killer.getItemInHand())) return;
        ItemStack head = getHead(killer, entity);

        int enchantmentLevel = killer.getItemInHand().getEnchantments().get(this);
        double restorationChance = 0.3 * enchantmentLevel;
        double random = Math.random();

        if (head == null || random >= restorationChance) return;
        entity.getWorld().dropItem(entity.getLocation(), Objects.requireNonNull(getHead(killer, entity)));
    }

    private ItemStack getHead(Player player, LivingEntity et) {
        if(et instanceof Player) {
            ItemStack playerHead = ItemCreator.createItem(Material.PLAYER_HEAD, "&e" + player.getName() + "'s head.");
            SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
            skullMeta.setOwnerProfile(player.getPlayerProfile());
            playerHead.setItemMeta(skullMeta);
            return playerHead;
        }

        String headName = et.getType().name().toUpperCase() + "_HEAD";
        if(Material.valueOf(headName) == null) return null;
        String itemName = et.getType().name().substring(0, 1) + et.getType().name().substring(1);
        return ItemCreator.createItem(Material.valueOf(headName), "&e" + itemName + " head");
    }
}
