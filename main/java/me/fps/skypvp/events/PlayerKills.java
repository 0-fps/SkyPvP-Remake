package me.fps.skypvp.events;


import me.clip.placeholderapi.PlaceholderAPI;
import me.fps.skypvp.skypvp;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
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
        FileConfiguration players = plugin.getPlayers();
        Player player = event.getEntity();
        Economy econ = plugin.getEconomy();
        FileConfiguration config = plugin.getConfig();
        Boolean cancelled = config.getBoolean("Config.Cancel-Drops");
        int deaths = Integer.valueOf(players.getString("Players." + player.getUniqueId() + ".deaths"));
        players.set("Players." + player.getUniqueId() + ".deaths", deaths + 1);
        String victimworld = player.getWorld().getName();
        String configworld = config.getString("Config.Spawn.world");
        players.set("Players." + player.getUniqueId() + ".name", player.getName());
        if (configworld.toLowerCase().equals(victimworld.toLowerCase())) {
            if (players.contains("Players." + player.getUniqueId() + ".kills") && players.contains("Players." + player.getUniqueId() + ".points")) {
                if (player.getKiller() instanceof Player) {
                    Player killer = event.getEntity().getKiller();
                    int kills = Integer.valueOf(players.getString("Players." + killer.getUniqueId() + ".kills"));
                    String prestige = players.getString("Players."+killer.getUniqueId()+".prestige");
                    int points = Integer.valueOf(players.getString("Players." + killer.getUniqueId() + ".points"));
                    players.set("Players." + killer.getUniqueId() + ".points", points + 2);
                    players.set("Players." + killer.getUniqueId() + ".kills", kills + 1);
                    plugin.savePlayers();
                    ConfigurationSection section = config.getConfigurationSection("Config.Prestiges");
                    if(section != null) {
                        for (String prestiges : config.getConfigurationSection("Config.Prestiges").getKeys(false)){
                            int pointstoget = config.getInt("Config.Prestiges." + prestiges + ".Points");
                            if(points == pointstoget){
                                players.set("Players."+killer.getUniqueId()+".prestige", prestiges);
                            }
                        }
                    }

                    plugin.savePlayers();
                    Double ganance = config.getDouble("Config.KillMoney");
                    String moneymessage = config.getString("Config.KillMoney-Message");
                    moneymessage = ChatColor.translateAlternateColorCodes('&', moneymessage);
                    moneymessage = moneymessage.replace("%victim%", player.getDisplayName() + "").replace("%money%", ganance + "");
                    econ.depositPlayer(killer, ganance);
                    killer.sendMessage(ChatColor.translateAlternateColorCodes('&', moneymessage));
                    String vaultprefix = "%vault_prefix%";
                    vaultprefix = PlaceholderAPI.setPlaceholders(killer.getPlayer(), vaultprefix);
                    String format = config.getString("Config.Death-Message");
                    format = ChatColor.translateAlternateColorCodes('&', format);
                    format = format.replace("%killer%", killer.getDisplayName() + "").replace("%victim%", player.getDisplayName() + "").replace("%prestige%", prestige + "").replace("%vault_prefix%", vaultprefix + "");
                    event.setDeathMessage(format);
                    player.spigot().respawn();
                    double x = Double.valueOf(config.getString("Config.Spawn.x"));
                    double y = Double.valueOf(config.getString("Config.Spawn.y"));
                    double z = Double.valueOf(config.getString("Config.Spawn.z"));
                    float yaw = Float.valueOf(config.getString("Config.Spawn.yaw"));
                    float pitch = Float.valueOf(config.getString("Config.Spawn.pitch"));
                    World world = plugin.getServer().getWorld(config.getString("Config.Spawn.world"));
                    Location l = new Location(world, x, y, z, yaw, pitch);
                    player.teleport(l);
                    plugin.savePlayers();
                    if (cancelled != false){
                        event.getDrops().clear();
                    }
                    plugin.savePlayers();
                    return;

                }
                plugin.savePlayers();
                return;


            }
            plugin.savePlayers();
            return;


        }

    }
}
