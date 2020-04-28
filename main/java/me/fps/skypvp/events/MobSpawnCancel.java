package me.fps.skypvp.events;

import me.fps.skypvp.skypvp;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.List;

public class MobSpawnCancel implements Listener {
    private skypvp plugin;

    public MobSpawnCancel(skypvp plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void MobSpawn(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        FileConfiguration config = plugin.getConfig();
        Boolean cancelled = config.getBoolean("Config.Mob-Spawning");
        String entityworld = entity.getWorld().getName();
        String configworld = config.getString("Config.Spawn.world");
        if (configworld.toLowerCase().equals(entityworld.toLowerCase())) {
            if (cancelled == false) {
                if(entity.getType().equals(EntityType.PLAYER)){
                    event.setCancelled(false);
                    return;
                }else{
                    event.setCancelled(true);
                    return;
                }

                }

            }
        }
    }
