package com.civlegacy.stamina.Command;

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

public class CommandHandler implements CommandExecutor {
    StaminaManager manager = Stamina.getStaminaManager();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "no.");
            return false;
        }

        OfflinePlayer player = Bukkit.getOfflinePlayer(((Player) commandSender).getUniqueId());

        if (args.length == 0) {
            int stam = manager.getPlayerStamina(player);
            commandSender.sendMessage(ChatColor.AQUA + "You have " + ChatColor.GREEN + stam + ChatColor.AQUA + " stamina.");
            return true;
        } else if (args.length == 1) {
            int stam = Integer.parseInt(args[0]);

            if (stam == 0) {
                commandSender.sendMessage(ChatColor.RED + "You need to put a number in...");
                return true;
            }

            int bal = manager.getPlayerStamina(player);
            if (bal < 10) {
                commandSender.sendMessage(ChatColor.RED + "Your account balance needs to be more than 10 to withdraw Stamina.");
                return true;
            }

            if (stam > bal) {
                commandSender.sendMessage(ChatColor.RED + "You don't have that much Stamina in your account.");
                return true;
            }

            PlayerInventory playerInventory = ((Player) commandSender).getPlayer().getInventory();
            //Stam representation.******
            ItemStack stamina = new ItemStack(Material.GOLDEN_APPLE);
            stamina.setDurability((short) 1);
            stamina.setAmount(stam);
            ItemMeta stamMeta = stamina.getItemMeta();
            stamMeta.setDisplayName("Stamina");
            stamina.setItemMeta(stamMeta);
            //***************************

            playerInventory.addItem(stamina);
            manager.removePlayerStamina(player, stam);
            commandSender.sendMessage(ChatColor.AQUA + "You have " + ChatColor.GREEN + manager.getPlayerStamina(player) + ChatColor.AQUA + " stamina.");
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("give") && commandSender.isOp()) {
                int give = Integer.parseInt(args[2]);
                Player giveplayer = Bukkit.getPlayer(args[1]);
                if (giveplayer == null) {
                    commandSender.sendMessage("no player found");
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
                }
                manager.removePlayerStamina(remplayer, remove);
                commandSender.sendMessage("Gave " + remplayer + " " + remove + " stamina.");
                return true;
            }
        }
        return false;
    }
}
