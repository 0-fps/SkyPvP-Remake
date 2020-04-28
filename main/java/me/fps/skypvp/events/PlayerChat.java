package me.fps.skypvp.events;

import me.clip.placeholderapi.PlaceholderAPI;
import me.fps.skypvp.skypvp;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener {
    private skypvp plugin;

    public PlayerChat(skypvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void ChatFormat (AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage();
        FileConfiguration players = plugin.getPlayers();
        FileConfiguration config = plugin.getConfig();
        String playerworld = player.getWorld().getName();
        String configworld = config.getString("Config.Spawn.world");
        boolean chatoption = config.getBoolean("Config.Chat-Format-Use");
        String prestige = players.getString("Players." + player.getUniqueId() + ".prestige");
        String vaultprefix = "%vault_prefix%";
        vaultprefix = PlaceholderAPI.setPlaceholders(player.getPlayer(),vaultprefix);
        if (chatoption != false) {
            if (configworld.toLowerCase().equals(playerworld.toLowerCase())) {
                String format = config.getString("Config.Chat-Format");
                format = ChatColor.translateAlternateColorCodes('&', format);
                format = format.replace("%player%", player.getDisplayName() + "").replace("%message%", message + "").replace("%prestige%", prestige + "").replace("%vault_prefix%", vaultprefix + "");
                event.setFormat(format);
            }
        }



    }
}
