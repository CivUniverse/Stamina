package com.civlegacy.stamina;

import com.civlegacy.stamina.Command.CommandHandler;
import com.civlegacy.stamina.Listeners.PlayerListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Stamina extends JavaPlugin {

    private static StaminaManager staminaManager;
    private static ClaimManager claimManager;
    private static Plugin stamina;

    @Override
    public void onEnable() {
        super.onEnable();
        stamina = this;
        staminaManager = new StaminaManager();
        claimManager = new ClaimManager();
        loadListeners();
        this.getCommand("stamina").setExecutor(new CommandHandler());
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void loadListeners() {
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public static StaminaManager getStaminaManager() {
        return staminaManager;
    }

    public static ClaimManager getClaimManager() { return claimManager; }

    public static Plugin getPlugin() { return stamina; }

}
