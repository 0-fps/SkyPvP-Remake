package me.fps.skypvp.events;

import me.fps.skypvp.skypvp;
import org.bukkit.configuration.file.FileConfiguration;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodCancel implements Listener {
    private skypvp plugin;

    public FoodCancel(skypvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void HungerCancel(FoodLevelChangeEvent event) {
        Entity player = event.getEntity();
        FileConfiguration config = plugin.getConfig();
        Boolean cancelled = config.getBoolean("Config.Hunger-Change");
        String userworld = player.getWorld().getName();
        String configworld = config.getString("Config.Spawn.world");
        if (configworld.toLowerCase().equals(userworld.toLowerCase())) {
            if (cancelled == false) {
                    event.setCancelled(true);
                    return;

                }

            }

        }

    }

