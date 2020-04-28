package me.fps.skypvp.events;

import me.fps.skypvp.skypvp;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;



public class PlayerDeaths implements Listener {
    private skypvp plugin;

    public PlayerDeaths(skypvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void DeathPlayer(PlayerDeathEvent event) {
        Player Victim = event.getEntity();
        FileConfiguration config = plugin.getConfig();
        FileConfiguration players = plugin.getPlayers();
        Boolean cancelled = config.getBoolean("Config.Cancel-Drops");
        String victimworld = Victim.getWorld().getName();
        String configworld = config.getString("Config.Spawn.world");
        players.set("Players." + Victim.getUniqueId() + ".name", Victim.getName());
        if (configworld.toLowerCase().equals(victimworld.toLowerCase())) {
            if (cancelled != false){
                event.getDrops().clear();
            }
            Victim.spigot().respawn();
            double x = Double.valueOf(config.getString("Config.Spawn.x"));
            double y = Double.valueOf(config.getString("Config.Spawn.y"));
            double z = Double.valueOf(config.getString("Config.Spawn.z"));
            float yaw = Float.valueOf(config.getString("Config.Spawn.yaw"));
            float pitch = Float.valueOf(config.getString("Config.Spawn.pitch"));
            World world = plugin.getServer().getWorld(config.getString("Config.Spawn.world"));
            Location l = new Location(world, x, y, z, yaw, pitch);
            Victim.teleport(l);

            if (players.contains("Players." + Victim.getUniqueId() + ".deaths")) {
                int deaths = Integer.valueOf(players.getString("Players." + Victim.getUniqueId() + ".deaths"));
                players.set("Players." + Victim.getUniqueId() + ".deaths", deaths+1);
                plugin.savePlayers();
                return;
            } else {
                players.set("Players." + Victim.getUniqueId() + ".deaths", 1);
                plugin.savePlayers();
                return;
            }

        }
    }
}



