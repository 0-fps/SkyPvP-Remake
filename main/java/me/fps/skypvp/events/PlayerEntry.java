package me.fps.skypvp.events;

import me.fps.skypvp.skypvp;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;


public class PlayerEntry implements Listener {
    private skypvp plugin;

    public  PlayerEntry (skypvp plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void PlayersJoins (PlayerChangedWorldEvent event){
        Player player = event.getPlayer();
        FileConfiguration players = plugin.getPlayers();
        FileConfiguration config = plugin.getConfig();
        String playerworld = player.getWorld().getName();
        String configworld = config.getString("Config.Spawn.world");
        if (configworld.toLowerCase().equals(playerworld.toLowerCase())) {
            if (!players.contains("Players." + player.getUniqueId() + ".kills") && !players.contains("Players." + player.getUniqueId() + ".points") && !players.contains("Player." + player.getUniqueId() + ".deaths") && !players.contains("Player." + player.getUniqueId() + ".prestige")) {
                players.set("Players." + player.getUniqueId() + ".name", player.getName());
                players.set("Players." + player.getUniqueId() + ".kills", 0);
                players.set("Players." + player.getUniqueId() + ".deaths", 0);
                players.set("Players." + player.getUniqueId() + ".points", 0);
                players.set("Players." + player.getUniqueId() + ".prestige", "No Prestige");
                plugin.savePlayers();
                return;
            }
        }

    }
}
