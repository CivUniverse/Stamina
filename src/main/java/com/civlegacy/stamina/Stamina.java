package com.civlegacy.stamina;

import com.civlegacy.stamina.Command.CommandHandler;
import com.civlegacy.stamina.Listeners.PlayerListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;

public class Stamina extends JavaPlugin {

    private static StaminaManager staminaManager;
    private static ClaimManager claimManager;
    private static Plugin stamina;

    private static Integer dailyStam;

    private File stamManagerFile = new File("./plugins/Stamina/stamMan.tsv");
    private File claimManagerFile = new File("./plugins/Stamina/claimMan.tsv");

    @Override
    public void onEnable() {
        super.onEnable();
        this.saveConfig();
        stamina = this;
        staminaManager = new StaminaManager();
        claimManager = new ClaimManager();
        dailyStam = this.getConfig().getInt("dailyStam");
        try {
            loadDB();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        loadListeners();
        this.getCommand("stamina").setExecutor(new CommandHandler());
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.saveConfig();
        try {
            saveDB();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadListeners() {
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public static StaminaManager getStaminaManager() {
        return staminaManager;
    }

    public static ClaimManager getClaimManager() { return claimManager; }

    public static Plugin getPlugin() { return stamina; }

    public static Integer getDailyStam() { return dailyStam; }

    private void saveDB() throws IOException {
        FileOutputStream f = new FileOutputStream(stamManagerFile);
        ObjectOutputStream s = new ObjectOutputStream(f);
        HashMap<UUID, Integer> stamMan = getStaminaManager().fetchValues();
        s.writeObject(stamMan);
        s.close();

        FileOutputStream fi = new FileOutputStream(claimManagerFile);
        ObjectOutputStream so = new ObjectOutputStream(fi);
        HashMap<UUID, Integer> claimMan = getClaimManager().fetchValues();
        so.writeObject(claimMan);
        so.close();
    }

    private void loadDB() throws IOException, ClassNotFoundException {
        FileInputStream f = new FileInputStream(stamManagerFile);
        ObjectInputStream s = new ObjectInputStream(f);
        HashMap<UUID, Integer> loadStam = (HashMap<UUID, Integer>) s.readObject();
        getStaminaManager().loadValues(loadStam);
        s.close();

        FileInputStream fi = new FileInputStream(claimManagerFile);
        ObjectInputStream so = new ObjectInputStream(fi);
        HashMap<UUID, Integer> loadClaim = (HashMap<UUID, Integer>) so.readObject();
        getClaimManager().loadValues(loadClaim);
        so.close();
    }

}
