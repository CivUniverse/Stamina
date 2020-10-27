package com.civlegacy.stamina;

import org.bukkit.OfflinePlayer;

import java.util.HashMap;

public class StaminaManager {
    HashMap<OfflinePlayer, Integer> staminaManager;

    public Integer getPlayerStamina(OfflinePlayer offlinePlayer) {
        int stam = staminaManager.get(offlinePlayer);
        return stam;
    }

    public void givePlayerStamina(OfflinePlayer offlinePlayer, Integer integer) {
        int stam = staminaManager.get(offlinePlayer);
        int add = integer;
        int last = stam + add;
        staminaManager.replace(offlinePlayer, last);
    }

    public void addStamina(OfflinePlayer offlinePlayer) {
        int stam = staminaManager.get(offlinePlayer);
        int add = stam++;
        staminaManager.replace(offlinePlayer, add);
    }

    public void removePlayerStamina(OfflinePlayer offlinePlayer, Integer integer) {
        int stam = staminaManager.get(offlinePlayer);
        int loss = stam - integer;
        staminaManager.replace(offlinePlayer, loss);
    }


}
