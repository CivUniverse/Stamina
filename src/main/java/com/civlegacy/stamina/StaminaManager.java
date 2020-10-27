package com.civlegacy.stamina;

import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.UUID;

public class StaminaManager {
    private HashMap<UUID, Integer> staminaManager = new HashMap<UUID, Integer>();

    public Integer getPlayerStamina(OfflinePlayer offlinePlayer) {
        UUID uuid = offlinePlayer.getUniqueId();
        int stam = staminaManager.get(uuid);
        return stam;
    }

    public void givePlayerStamina(OfflinePlayer offlinePlayer, Integer integer) {
        UUID uuid = offlinePlayer.getUniqueId();
        int stam = staminaManager.get(uuid);
        int add = integer;
        int last = stam + add;
        staminaManager.replace(uuid, last);
    }

    public void addStamina(OfflinePlayer offlinePlayer) {
        UUID uuid = offlinePlayer.getUniqueId();
        int stam = staminaManager.get(uuid);
        int add = stam++;
        staminaManager.replace(uuid, add);
    }

    public void removePlayerStamina(OfflinePlayer offlinePlayer, Integer integer) {
        UUID uuid = offlinePlayer.getUniqueId();
        int stam = staminaManager.get(uuid);
        int loss = stam - integer;
        staminaManager.replace(uuid, loss);
    }

    public boolean managerContains(OfflinePlayer player) {
        UUID uuid = player.getUniqueId();
        return staminaManager.containsKey(uuid);
    }

    public void createPlayerAccount(OfflinePlayer offlinePlayer) {
        UUID uuid = offlinePlayer.getUniqueId();
        Stamina.getPlugin().getLogger().warning("Making new Bank account for player: " + offlinePlayer.getName());
        staminaManager.put(uuid, 2);
    }

    public HashMap<UUID, Integer> fetchValues() {
        return staminaManager;
    }

    public void loadValues(HashMap<UUID, Integer> map) {
        staminaManager = map;
    }


}
