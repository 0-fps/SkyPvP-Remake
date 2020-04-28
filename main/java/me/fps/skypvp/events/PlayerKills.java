package me.fps.skypvp.events;


import me.clip.placeholderapi.PlaceholderAPI;
import me.fps.skypvp.skypvp;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerKills implements Listener {
    private skypvp plugin;

    public PlayerKills(skypvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void KillPlayer(PlayerDeathEvent event) {
        Player Killer = event.getEntity().getKiller();
        Economy econ = plugin.getEconomy();
        FileConfiguration config = plugin.getConfig();
        FileConfiguration players = plugin.getPlayers();
        Player Victim = event.getEntity();
        String victimworld = Victim.getWorld().getName();
        String configworld = config.getString("Config.Spawn.world");
        String prestige = players.getString("Players."+Killer.getUniqueId()+".prestige");
        players.set("Players." + Killer.getUniqueId() + ".name", Killer.getName());
        if (configworld.toLowerCase().equals(victimworld.toLowerCase())) {
            if (players.contains("Players." + Killer.getUniqueId() + ".kills") && players.contains("Players." + Killer.getUniqueId() + ".points")){
                if (Killer.getPlayer() == null){
                    String nokillerformat = config.getString("Config.No-Killer-Death-Message");
                    String vaultvictim = "%vault_prefix%";
                    vaultvictim = PlaceholderAPI.setPlaceholders(Killer.getPlayer(), vaultvictim);
                    nokillerformat = ChatColor.translateAlternateColorCodes('&', nokillerformat);
                    nokillerformat = nokillerformat.replace("%victim%", Victim.getDisplayName() + "").replace("%vault_prefix%", vaultvictim+"");
                    event.setDeathMessage(nokillerformat);
                }else{
                    String vaultprefix = "%vault_prefix%";
                    vaultprefix = PlaceholderAPI.setPlaceholders(Killer.getPlayer(), vaultprefix);
                    String format = config.getString("Config.Death-Message");
                    format = ChatColor.translateAlternateColorCodes('&', format);
                    format = format.replace("%killer%", Killer.getDisplayName() + "").replace("%victim%", Victim.getDisplayName() + "").replace("%prestige%", prestige + "").replace("%vault_prefix%", vaultprefix + "");
                    event.setDeathMessage(format);
                }

                Double ganance = config.getDouble("Config.KillMoney");
                String moneymessage = config.getString("Config.KillMoney-Message");
                moneymessage = ChatColor.translateAlternateColorCodes('&', moneymessage);
                moneymessage  = moneymessage.replace("%victim%", Victim.getDisplayName() + "").replace("%money%", ganance+"");
                econ.depositPlayer(Killer, ganance);
                Killer.sendMessage(ChatColor.translateAlternateColorCodes('&', moneymessage));
                int points = Integer.valueOf(players.getString("Players." + Killer.getUniqueId() + ".points"));
                players.set("Players." + Killer.getUniqueId() + ".points", points + 2);
                int kills = Integer.valueOf(players.getString("Players." + Killer.getUniqueId() + ".kills"));
                players.set("Players." + Killer.getUniqueId() + ".kills", kills + 1);
                if (points <= 9) {
                    players.set("Players." + Killer.getUniqueId() + ".prestige", "§8No Prestige");
                    plugin.savePlayers();
                    return;
                }else if(points <= 10){
                    players.set("Players." + Killer.getUniqueId() + ".prestige", "§8Coal IV");
                    plugin.savePlayers();
                }else if(points <= 15){
                    players.set("Players." + Killer.getUniqueId() + ".prestige", "§8Coal III");
                    plugin.savePlayers();
                }else if(points <= 20){
                    players.set("Players." + Killer.getUniqueId() + ".prestige", "§8Coal II");
                    plugin.savePlayers();
                }else if(points <= 25){
                    players.set("Players." + Killer.getUniqueId() + ".prestige", "§8Coal I");
                    plugin.savePlayers();
                }else if(points <= 35){
                    players.set("Players." + Killer.getUniqueId() + ".prestige", "§7Iron V");
                    plugin.savePlayers();
                }else if(points <= 40){
                    players.set("Players." + Killer.getUniqueId() + ".prestige", "§7Iron IV");
                    plugin.savePlayers();
                }else if(points <= 45){
                    players.set("Players." + Killer.getUniqueId() + ".prestige", "§7Iron III");
                    plugin.savePlayers();
                }else if(points <= 50){
                    players.set("Players." + Killer.getUniqueId() + ".prestige", "§7Iron II");
                    plugin.savePlayers();
                }else if(points <= 55){
                    players.set("Players." + Killer.getUniqueId() + ".prestige", "§7Iron I");
                    plugin.savePlayers();
                }else if(points <= 65){
                    players.set("Players." + Killer.getUniqueId() + ".prestige", "§6Gold III");
                    plugin.savePlayers();
                }else if(points <= 70){
                    players.set("Players." + Killer.getUniqueId() + ".prestige", "§6Gold II");
                    plugin.savePlayers();
                }else if(points <= 75){
                    players.set("Players." + Killer.getUniqueId() + ".prestige", "§6Gold I");
                    plugin.savePlayers();
                }else if(points <= 85){
                    players.set("Players." + Killer.getUniqueId() + ".prestige", "§aEmerald IV");
                    plugin.savePlayers();
                }else if(points <= 90){
                    players.set("Players." + Killer.getUniqueId() + ".prestige", "§aEmerald III");
                    plugin.savePlayers();
                }else if(points <= 95){
                    players.set("Players." + Killer.getUniqueId() + ".prestige", "§aEmerald II");
                    plugin.savePlayers();
                }else if(points <= 100){
                    players.set("Players." + Killer.getUniqueId() + ".prestige", "§aEmerald I");
                    plugin.savePlayers();
                }else if(points <= 110){
                    players.set("Players." + Killer.getUniqueId() + ".prestige", "§bDiamond IV");
                    plugin.savePlayers();
                }else if(points <= 130){
                    players.set("Players." + Killer.getUniqueId() + ".prestige", "§bDiamond III");
                    plugin.savePlayers();
                }else if(points <= 160){
                    players.set("Players." + Killer.getUniqueId() + ".prestige", "§bDiamond II");
                    plugin.savePlayers();
                }else if(points <= 190){
                    players.set("Players." + Killer.getUniqueId() + ".prestige", "§bDiamond I");
                    plugin.savePlayers();
                }else{
                    players.set("Players." + Killer.getUniqueId() + ".prestige", "§bDiamond I");
                    plugin.savePlayers();
                }

            }

        }

    }
}