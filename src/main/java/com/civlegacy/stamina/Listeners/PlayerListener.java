package com.civlegacy.stamina.Listeners;

import com.civlegacy.stamina.Stamina;
import com.civlegacy.stamina.StaminaManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public class PlayerListener implements Listener {

    StaminaManager manager = Stamina.getStaminaManager();

    @EventHandler
    public void playerLogin(PlayerJoinEvent event) {
        if(!manager.managerContains(event.getPlayer())) {
            manager.createPlayerAccount(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerEat(PlayerItemConsumeEvent event) {
        //Stamina Item Representation...
        ItemStack stamina = new ItemStack(Material.GOLDEN_APPLE);
        stamina.setAmount(event.getItem().getAmount());
        stamina.setDurability((short) 1);
        ItemMeta stamMeta = stamina.getItemMeta();
        stamMeta.setDisplayName("Stamina");
        stamina.setItemMeta(stamMeta);
        Player player = event.getPlayer();
        PlayerInventory playerInventory = event.getPlayer().getInventory();

        if(event.getItem().isSimilar(stamina)) {
            manager.givePlayerStamina(event.getPlayer(), 1);
            event.getPlayer().sendMessage(ChatColor.AQUA + "You now have " + ChatColor.GREEN + manager.getPlayerStamina(event.getPlayer()) + ChatColor.AQUA + " stamina.");
            Bukkit.getScheduler().runTaskLater(Stamina.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    Collection<PotionEffect> potionEffects = player.getActivePotionEffects();
                    for(PotionEffect potionEffect : potionEffects) {
                        player.removePotionEffect(potionEffect.getType());
                    }
                }
            }, 10);
        }
    }
}
