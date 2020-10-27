package com.civlegacy.stamina;

import org.bukkit.OfflinePlayer;

import java.util.HashMap;

public class StaminaManager {
    private HashMap<OfflinePlayer, Integer> staminaManager = new HashMap<OfflinePlayer, Integer>();

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

    public void createPlayerAccount(OfflinePlayer offlinePlayer) {
        staminaManager.put(offlinePlayer, 2);
    }


}
