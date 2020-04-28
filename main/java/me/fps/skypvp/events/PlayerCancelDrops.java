package me.fps.skypvp.events;

import me.fps.skypvp.skypvp;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerCancelDrops implements Listener {
    private skypvp plugin;

    public PlayerCancelDrops(skypvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void CancelDrops(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        FileConfiguration config = plugin.getConfig();
        String playerworld = player.getWorld().getName();
        String configworld = config.getString("Config.Spawn.world");
        Boolean cancelled = config.getBoolean("Config.Cancel-Drops");
        if (configworld.toLowerCase().equals(playerworld.toLowerCase())){
            if (cancelled != false) {
                event.setCancelled(true);
            }
        }
    }

}
