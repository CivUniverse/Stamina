package com.civlegacy.stamina;

import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.UUID;

public class ClaimManager {

    private HashMap<UUID, Integer> claimTimes = new HashMap<>();

    public void recordClaim(OfflinePlayer player, Integer time) {
        UUID uuid = player.getUniqueId();
        claimTimes.replace(uuid, time);
        Stamina.getPlugin().getLogger().warning("recordClaim fired.");
    }

    public boolean checkClaim(OfflinePlayer player, Integer time) {
        UUID uuid = player.getUniqueId();
        if(!claimTimes.containsKey(uuid)) {
            Stamina.getPlugin().getLogger().warning("checkClaim warranted recordClaim.");
            claimTimes.put(uuid, time);
            return true;
        }
        Integer lastClaim = claimTimes.get(player.getUniqueId());
        int timeSinceClaim = time - lastClaim;
        boolean test = timeSinceClaim >= 86400000;
        Stamina.getPlugin().getLogger().warning("checkClaim value recorded as " + test);
        return timeSinceClaim >= 86400000;
    }

    public HashMap<UUID, Integer> fetchValues() {
        return claimTimes;
    }

    public void loadValues(HashMap<UUID, Integer> map) {
        claimTimes = map;
    }
}
