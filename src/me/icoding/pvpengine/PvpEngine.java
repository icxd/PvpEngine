package me.icoding.pvpengine;

import me.icoding.pvpengine.commands.*;
import me.icoding.pvpengine.commands.gamemode.GMACommand;
import me.icoding.pvpengine.commands.gamemode.GMCCommand;
import me.icoding.pvpengine.commands.gamemode.GMSCommand;
import me.icoding.pvpengine.commands.gamemode.GMSPCommand;
import me.icoding.pvpengine.gui.*;
import me.icoding.pvpengine.listeners.*;
import me.icoding.pvpengine.managers.KitsManager;
import me.icoding.pvpengine.managers.PlayerManager;
import me.icoding.pvpengine.managers.RankManager;
import me.icoding.pvpengine.managers.VaultManager;
import me.icoding.pvpengine.runnables.ScoreboardRunnable;
import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class PvpEngine extends JavaPlugin {

    public static HashMap<UUID, PermissionAttachment> playerPermissions = new HashMap<>();

    public static PvpEngine INSTANCE;

    public static RankManager rankManager;
    public static PlayerManager playerManager;
    public static KitsManager kitsManager;
    public static VaultManager vaultManager;

    @Override
    public void onEnable() {
        INSTANCE = this;

        rankManager = new RankManager();
        playerManager = new PlayerManager();
        kitsManager = new KitsManager();
        vaultManager = new VaultManager();

        new ScoreboardRunnable();

        this.registerConfig();
        this.registerCommands();
        this.registerListeners();

        Bukkit.getConsoleSender().sendMessage("§9[PvpEngine] §rLoaded v1.0.0");
    }

    @Override
    public void onDisable() {
        playerPermissions.clear();

        Bukkit.getConsoleSender().sendMessage("§9[PvpEngine] §rDisabled v1.0.0");
    }

    private void registerConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void registerCommands() {
        getCommand("sethub").setExecutor(new SetHubCommand());
        getCommand("set").setExecutor(new SetCommand());
        getCommand("hub").setExecutor(new HubCommand());
        getCommand("pvpengine").setExecutor(new PvpEngineCommand());
        getCommand("gmc").setExecutor(new GMCCommand());
        getCommand("gms").setExecutor(new GMSCommand());
        getCommand("gmsp").setExecutor(new GMSPCommand());
        getCommand("gma").setExecutor(new GMACommand());
        getCommand("kit").setExecutor(new KitCommand());
        getCommand("shop").setExecutor(new ShopCommand());
        getCommand("trash").setExecutor(new TrashCommand());
        getCommand("enchant").setExecutor(new EnchantCommand());
        getCommand("spy").setExecutor(new SpyCommand());
        getCommand("vanish").setExecutor(new VanishCommand());
        getCommand("vault").setExecutor(new VaultCommand());
    }

    private void registerListeners() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new JoinListener(), this);
        pm.registerEvents(new QuitListener(), this);
        pm.registerEvents(new ChatListener(), this);
        pm.registerEvents(new DeathListener(), this);
        pm.registerEvents(new HungerListener(), this);
        pm.registerEvents(new BlockListener(), this);
        pm.registerEvents(new ExpGainListener(), this);
        pm.registerEvents(new WeatherListener(), this);
        pm.registerEvents(new CommandListener(), this);

        pm.registerEvents(new ShopGUI(), this);
        pm.registerEvents(new ShopExtendedGUI(), this);
        pm.registerEvents(new EnchantGUI(), this);
        pm.registerEvents(new TrashGUI(), this);
        pm.registerEvents(new VaultGUI(), this);
        pm.registerEvents(new VaultOthersGUI(), this);
    }
}
