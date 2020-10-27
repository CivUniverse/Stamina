package com.civlegacy.stamina.Listeners;

import com.civlegacy.stamina.Stamina;
import com.civlegacy.stamina.StaminaManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerListener implements Listener {

    StaminaManager manager = Stamina.getStaminaManager();

    @EventHandler
    public void playerFirstLogin(PlayerLoginEvent event) {
        if(!event.getPlayer().hasPlayedBefore()) {
            OfflinePlayer player = Bukkit.getPlayer(event.getPlayer().getUniqueId());
            manager.createPlayerAccount(player);
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
        PlayerInventory playerInventory = event.getPlayer().getInventory();

        if(event.getItem().isSimilar(stamina)) {
            manager.givePlayerStamina(event.getPlayer(), 1);
            event.getPlayer().sendMessage(ChatColor.AQUA + "You now have " + ChatColor.GREEN + manager.getPlayerStamina(event.getPlayer()) + ChatColor.AQUA + " stamina.");
        }
    }
}
