package com.civlegacy.stamina;

import org.bukkit.OfflinePlayer;

import java.util.HashMap;

public class ClaimManager {

    private HashMap<OfflinePlayer, Integer> claimTimes = new HashMap<>();

    public void recordClaim(OfflinePlayer player, Integer time) {
        claimTimes.replace(player, time);
    }

    public boolean checkClaim(OfflinePlayer player, Integer time) {
        if(!claimTimes.containsKey(player)) {
            recordClaim(player, time);
            return true;
        }
        Integer lastClaim = claimTimes.get(player);
        Integer timeSinceClaim = time - lastClaim;
        if (timeSinceClaim >= 86400000) {
            return true;
        }
        return false;
    }
}
