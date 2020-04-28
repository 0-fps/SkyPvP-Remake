package me.fps.skypvp.api;

import me.fps.skypvp.skypvp;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class ExpansionSkyPvP extends PlaceholderExpansion{

    private skypvp plugin;

    public ExpansionSkyPvP (final skypvp plugin){
        this.plugin = plugin;
    }

    public boolean persist(){
        return true;
    }

    public boolean canRegister(){
        return true;
    }

    public String getAuthor(){
        return "Fps";
    }

    public String getIdentifier(){
        return "SkyPvP";
    }
    public String getVersion(){
        return this.plugin.getDescription().getVersion();
    }
    public String onPlaceholderRequest (final Player player, final String identifier){
        FileConfiguration players = plugin.getPlayers();
        if (player == null){
            return "";
        }
        if(identifier.equals("kills")){
            String kills = String.valueOf(players.getString("Players." + player.getUniqueId() + ".kills"));
            return kills;

        }
        if(identifier.equals("deaths")){
            String deaths = String.valueOf(players.getString("Players." + player.getUniqueId() + ".deaths"));
            return deaths;
        }
        if(identifier.equals("points")){
            String points = String.valueOf(players.getString("Players." + player.getUniqueId() + ".points"));
            return points;
        }
        if(identifier.equals("prestige")){
            String prestige = String.valueOf(players.getString("Players." + player.getUniqueId() + ".prestige"));
            return prestige;
        }
        return null;
    }
}
