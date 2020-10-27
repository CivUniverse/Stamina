package com.civlegacy.stamina;

import com.civlegacy.stamina.Command.CommandHandler;
import com.civlegacy.stamina.Listeners.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Stamina extends JavaPlugin {

    private static StaminaManager staminaManager;

    @Override
    public void onEnable() {
        super.onEnable();
        loadListeners();
        loadManagers();
        this.getCommand("stamina").setExecutor(new CommandHandler());
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void loadListeners() {
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    private void loadManagers() {
        staminaManager = new StaminaManager();
    }

    public static StaminaManager getStaminaManager() {
        return staminaManager;
    }
}
