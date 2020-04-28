package me.fps.skypvp;


import me.fps.skypvp.api.ExpansionSkyPvP;
import me.fps.skypvp.commands.PrincipalCommands;
import me.fps.skypvp.events.*;
import me.fps.skypvp.scoreboard.Scoreboard;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class skypvp extends JavaPlugin {
    private FileConfiguration messages = null;
    private FileConfiguration players = null;
    private static Economy econ = null;
    private File messagesFile = null;
    private File playersFile = null;

    public String ConfigRute;
    PluginDescriptionFile pdffile = getDescription();
    public String version = ChatColor.BLUE+"["+pdffile.getVersion()+ChatColor.BLUE+"]";
    public String name = ChatColor.RED+"["+pdffile.getName()+ChatColor.RED+"]";

    public void onEnable (){

        Scoreboard scoreboard = new Scoreboard(this);
        scoreboard.initScoreboard();

        Bukkit.getConsoleSender().sendMessage(name+" has been enabled (version: "+version+")");
        registerCommands();
        registerConfig();
        registerMessages();
        registerPlayers();
        registerEvents();
        setupEconomy();
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new ExpansionSkyPvP(this).register();
        }

    }

    public void onDisable(){
        Bukkit.getConsoleSender().sendMessage(name+" has been disabled (version: "+version+")");

    }

    private boolean setupEconomy(){
        if(getServer().getPluginManager().getPlugin("Vault") == null){
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if(rsp == null){
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public Economy getEconomy(){
        return this.econ;

    }

    public void registerCommands(){
        this.getCommand("skypvp").setExecutor(new PrincipalCommands(this));
    }

    public void registerConfig(){
        File config = new File(this.getDataFolder(),"config.yml");
        ConfigRute = config.getPath();
        if(!config.exists()){
            this.getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }
    public FileConfiguration getMessages(){
        if(messages == null){
            reloadMessages();
        }
        return messages;
    }
    public void reloadMessages(){
        if(messages == null){
            messagesFile  = new File(getDataFolder(),"messages.yml");
        }
        messages = YamlConfiguration.loadConfiguration(messagesFile);
        Reader defConfigStream;
        try{
            defConfigStream = new InputStreamReader(this.getResource("messages.yml"),"UTF8");
            if(defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                messages.setDefaults(defConfig);
            }
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();

        }
    }
    public void saveMessages(){
        try{
            messages.save(messagesFile);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void registerMessages(){
        messagesFile = new File(this.getDataFolder(),"messages.yml");
        if(!messagesFile.exists()){
            this.getMessages().options().copyDefaults(true);
            saveMessages();
        }
    }
    public FileConfiguration getPlayers(){
        if(players == null){
            reloadPlayers();
        }
        return players;
    }
    public void reloadPlayers(){
        if(players == null){
            playersFile  = new File(getDataFolder(),"players.yml");
        }
        players = YamlConfiguration.loadConfiguration(playersFile);
        Reader defConfigStream;
        try{
            defConfigStream = new InputStreamReader(this.getResource("players.yml"),"UTF8");
            if(defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                players.setDefaults(defConfig);
            }
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();

        }
    }
    public void savePlayers(){
        try{
            players.save(playersFile);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void registerPlayers(){
        playersFile = new File(this.getDataFolder(),"players.yml");
        if(!playersFile.exists()){
            this.getPlayers().options().copyDefaults(true);
            savePlayers();
        }
    }
    public void registerEvents(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerKills(this), this);
        pm.registerEvents(new PlayerDeaths(this), this);
        pm.registerEvents(new PlayerEntry(this), this);
        pm.registerEvents(new PlayerChat(this),this);
        pm.registerEvents(new PlayerCancelDrops(this), this);
        pm.registerEvents(new MobSpawnCancel(this), this);
        pm.registerEvents(new FoodCancel(this), this);
    }

}

