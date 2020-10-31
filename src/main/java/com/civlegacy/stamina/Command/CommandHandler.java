package com.civlegacy.stamina.Command;

import com.civlegacy.stamina.ClaimManager;
import com.civlegacy.stamina.Stamina;
import com.civlegacy.stamina.StaminaManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Date;

public class CommandHandler implements CommandExecutor {
    StaminaManager manager = Stamina.getStaminaManager();
    ClaimManager claimMan = Stamina.getClaimManager();

    Date date = new Date();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "no.");
            return false;
        }
        OfflinePlayer player = Bukkit.getOfflinePlayer(((Player) commandSender).getUniqueId());

        if (args.length == 0) {
            int stam = Stamina.getStaminaManager().getPlayerStamina(player);
            commandSender.sendMessage(ChatColor.AQUA + "You have " + ChatColor.GREEN + stam + ChatColor.AQUA + " stamina.");
            return true;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("claim")) {
                if (claimMan.checkClaim(player, (int) date.getTime())) {
                    commandSender.sendMessage(ChatColor.AQUA + "You successfully claimed your Stamina for today.");
                    manager.givePlayerStamina(player, Stamina.getDailyStam());
                    claimMan.recordClaim(player, (int) date.getTime());
                } else {
                    commandSender.sendMessage(ChatColor.RED + "You already claimed your stamina for today.");
                }
                return true;
            }

            if (Integer.parseInt(args[0]) == 0) {
                commandSender.sendMessage(ChatColor.RED + "You need to put a number in...");
                return true;
            }

            int bal = manager.getPlayerStamina(player);
            if (bal < 10) {
                commandSender.sendMessage(ChatColor.RED + "Your account balance needs to be more than 10 to withdraw Stamina.");
                return true;
            }

            if(Integer.parseInt(args[0]) < 0) {
                commandSender.sendMessage(ChatColor.RED + "You cannot use negative numbers...");
                return true;
            }

            if (Integer.parseInt(args[0]) > bal) {
                commandSender.sendMessage(ChatColor.RED + "You don't have that much Stamina in your account.");
                return true;
            }

            PlayerInventory playerInventory = ((Player) commandSender).getPlayer().getInventory();
            //Stam representation.******
            ItemStack stamina = new ItemStack(Material.GOLDEN_APPLE);
            stamina.setDurability((short) 1);
            stamina.setAmount(Integer.parseInt(args[0]));
            ItemMeta stamMeta = stamina.getItemMeta();
            stamMeta.setDisplayName("Stamina");
            stamina.setItemMeta(stamMeta);
            //***************************

            playerInventory.addItem(stamina);
            manager.removePlayerStamina(player, Integer.parseInt(args[0]));
            commandSender.sendMessage(ChatColor.AQUA + "You have " + ChatColor.GREEN + manager.getPlayerStamina(player) + ChatColor.AQUA + " stamina.");
            return true;
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("give") && commandSender.isOp()) {
                int give = Integer.parseInt(args[2]);
                Player giveplayer = Bukkit.getPlayer(args[1]);
                if (giveplayer == null) {
                    commandSender.sendMessage("no player found");
                    return true;
                }
                manager.givePlayerStamina(giveplayer, give);
                commandSender.sendMessage("Gave " + giveplayer + " " + give + " stamina.");
                return true;
            }

            if (args[0].equalsIgnoreCase("remove") && player.isOp()) {
                int remove = Integer.parseInt(args[2]);
                Player remplayer = Bukkit.getPlayer(args[1]);
                if (remplayer == null) {
                    commandSender.sendMessage("no player found");
                    return true;
                }
                manager.removePlayerStamina(remplayer, remove);
                commandSender.sendMessage("Removed " + remplayer + " " + remove + " stamina.");
                return true;
            }
        }
        return false;
    }
}
