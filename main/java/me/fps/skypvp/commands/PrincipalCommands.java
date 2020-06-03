package me.fps.skypvp.commands;

import me.fps.skypvp.skypvp;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class PrincipalCommands implements CommandExecutor {


    private skypvp plugin;

    public PrincipalCommands(skypvp plugin) {
        this.plugin = plugin;
    }


    public boolean onCommand(CommandSender sender, Command commands, String label, String[] args) {
        Player jugador = (Player) sender;
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("version")) {
                if (jugador.hasPermission("skypvp.admin")) {
                    jugador.sendMessage(plugin.name + ChatColor.GREEN + " the version of plugin is: " + plugin.version);
                    return true;
                } else {
                    FileConfiguration messages = plugin.getMessages();
                    String permission = "Messages.permission-denied";
                    jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString(permission)));
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("join")) {
                FileConfiguration config = plugin.getConfig();
                if (config.contains("Config.Spawn.x")) {
                    double x = Double.valueOf(config.getString("Config.Spawn.x"));
                    double y = Double.valueOf(config.getString("Config.Spawn.y"));
                    double z = Double.valueOf(config.getString("Config.Spawn.z"));
                    float yaw = Float.valueOf(config.getString("Config.Spawn.yaw"));
                    float pitch = Float.valueOf(config.getString("Config.Spawn.pitch"));
                    World world = plugin.getServer().getWorld(config.getString("Config.Spawn.world"));
                    Location l = new Location(world, x, y, z, yaw, pitch);
                    jugador.teleport(l);
                    FileConfiguration messages = plugin.getMessages();
                    String join = "Messages.teleport-game";
                    jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString(join)));
                } else {
                    FileConfiguration messages = plugin.getMessages();
                    String error = "Messages.game-exist";
                    jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString(error)));
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("exit")) {
                FileConfiguration config = plugin.getConfig();
                if (config.contains("Config.Lobby.x")) {
                    double x2 = Double.valueOf(config.getString("Config.Lobby.x"));
                    double y2 = Double.valueOf(config.getString("Config.Lobby.y"));
                    double z2 = Double.valueOf(config.getString("Config.Lobby.z"));
                    float yaw2 = Float.valueOf(config.getString("Config.Lobby.yaw"));
                    float pitch2 = Float.valueOf(config.getString("Config.Lobby.pitch"));
                    World world2 = plugin.getServer().getWorld(config.getString("Config.Lobby.world"));
                    Location l = new Location(world2, x2, y2, z2, yaw2, pitch2);
                    jugador.teleport(l);
                    FileConfiguration messages = plugin.getMessages();
                    String lobbytp = "Messages.teleport-lobby";
                    jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString(lobbytp)));
                    return true;
                } else {
                    FileConfiguration messages = plugin.getMessages();
                    String lobby = "Messages.lobby-exist";
                    jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString(lobby)));
                    return true;
                }

            } else if (args[0].equalsIgnoreCase("reload")) {
                if (jugador.hasPermission("skypvp.admin")) {
                    plugin.reloadConfig();
                    plugin.reloadMessages();
                    plugin.reloadPlayers();
                    jugador.sendMessage(plugin.name + ChatColor.GREEN + " has been reload succesfully");
                    return true;
                } else {
                    FileConfiguration messages = plugin.getMessages();
                    String permission = "Messages.permission-denied";
                    jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString(permission)));
                    return true;

                }


            } else if (args[0].equalsIgnoreCase("setspawn")) {
                if (jugador.hasPermission("skypvp.admin")) {
                    Location l = jugador.getLocation();
                    double x = l.getX();
                    double y = l.getY();
                    double z = l.getZ();
                    String world = l.getWorld().getName();
                    float yaw = l.getYaw();
                    float pitch = l.getPitch();
                    FileConfiguration config = plugin.getConfig();
                    config.set("Config.Spawn.x", x);
                    config.set("Config.Spawn.y", y);
                    config.set("Config.Spawn.z", z);
                    config.set("Config.Spawn.world", world);
                    config.set("Config.Spawn.yaw", yaw);
                    config.set("Config.Spawn.pitch", pitch);
                    plugin.saveConfig();
                    jugador.sendMessage(plugin.name + ChatColor.GREEN + " the spawn has been set");
                    return true;
                } else {
                    FileConfiguration messages = plugin.getMessages();
                    String permission = "Messages.permission-denied";
                    jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString(permission)));
                    return true;

                }

            } else if (args[0].equalsIgnoreCase("setlobby")) {
                Location l = jugador.getLocation();
                double x2 = l.getX();
                double y2 = l.getY();
                double z2 = l.getZ();
                String world2 = l.getWorld().getName();
                float yaw2 = l.getYaw();
                float pitch2 = l.getPitch();
                FileConfiguration config = plugin.getConfig();
                config.set("Config.Lobby.x", x2);
                config.set("Config.Lobby.y", y2);
                config.set("Config.Lobby.z", z2);
                config.set("Config.Lobby.world", world2);
                config.set("Config.Lobby.yaw", yaw2);
                config.set("Config.Lobby.pitch", pitch2);
                plugin.saveConfig();
                jugador.sendMessage(plugin.name + ChatColor.GREEN + " the lobby has been set");
                return true;

            } else if (args[0].equalsIgnoreCase("deaths")) {
                FileConfiguration players = plugin.getPlayers();
                if (!players.contains("Players.")) {
                    FileConfiguration messages = plugin.getMessages();
                    List<String> messagenodeaths = messages.getStringList("Messages.no-have-deaths");
                    for (int i = 0; i < messagenodeaths.size(); i++) {
                        String text = messagenodeaths.get(i);
                        jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
                    }
                    return true;

                } else {
                    if (players.contains("Players." + jugador.getUniqueId() + ".deaths")) {
                        int deaths = Integer.valueOf(players.getString("Players." + jugador.getUniqueId() + ".deaths"));
                        FileConfiguration messages = plugin.getMessages();
                        List<String> messagenodeaths = messages.getStringList("Messages.deaths");
                        for (int i = 0; i < messagenodeaths.size(); i++) {
                            String text = messagenodeaths.get(i);
                            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', text.replaceAll("%deaths%", String.valueOf(deaths))));
                        }
                        return true;
                    } else {
                        FileConfiguration messages = plugin.getMessages();
                        List<String> messagenodeaths = messages.getStringList("Messages.no-have-deaths");
                        for (int i = 0; i < messagenodeaths.size(); i++) {
                            String text = messagenodeaths.get(i);
                            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
                        }
                        return true;

                    }
                }


            } else if (args[0].equalsIgnoreCase("kills")) {
                FileConfiguration players = plugin.getPlayers();
                if (!players.contains("Players.")) {
                    FileConfiguration messages = plugin.getMessages();
                    List<String> messagenodeaths = messages.getStringList("Messages.no-have-kills");
                    for (int i = 0; i < messagenodeaths.size(); i++) {
                        String text = messagenodeaths.get(i);
                        jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
                    }
                    return true;

                } else {
                    if (players.contains("Players." + jugador.getUniqueId() + ".kills")) {
                        int kills = Integer.valueOf(players.getString("Players." + jugador.getUniqueId() + ".kills"));
                        FileConfiguration messages = plugin.getMessages();
                        List<String> messagenodeaths = messages.getStringList("Messages.kills");
                        for (int i = 0; i < messagenodeaths.size(); i++) {
                            String text = messagenodeaths.get(i);
                            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', text.replaceAll("%kills%", String.valueOf(kills))));
                        }
                        return true;
                    } else {
                    }
                    FileConfiguration messages = plugin.getMessages();
                    List<String> messagenodeaths = messages.getStringList("Messages.no-have-kills");

                    for (int i = 0; i < messagenodeaths.size(); i++) {
                        String text = messagenodeaths.get(i);
                        jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
                    }
                    return true;

                }


            }else if (args[0].equalsIgnoreCase("stats")){
                FileConfiguration players = plugin.getPlayers();
                if (!players.contains("Players.")) {
                    FileConfiguration messages = plugin.getMessages();
                    List<String> messagenostats = messages.getStringList("Messages.no-have-stats");
                    for (int i = 0; i < messagenostats.size(); i++) {
                        String text = messagenostats.get(i);
                        jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
                    }
                    return true;

                } else {
                    if (players.contains("Players." + jugador.getUniqueId() + ".kills") && players.contains("Players." + jugador.getUniqueId() + ".deaths")) {
                        int deaths = Integer.valueOf(players.getString("Players." + jugador.getUniqueId() + ".deaths"));
                        int kills = Integer.valueOf(players.getString("Players." + jugador.getUniqueId() + ".kills"));
                        FileConfiguration messages = plugin.getMessages();
                        List<String> messagestats = messages.getStringList("Messages.stats");
                        for (int i = 0; i < messagestats.size(); i++) {
                            String text = messagestats.get(i);
                            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', text.replace("%kills%", kills + "").replace("%deaths%", deaths + "")));
                        }
                        return true;
                    } else {
                    }
                    FileConfiguration messages = plugin.getMessages();
                    List<String> messagenostats = messages.getStringList("Messages.no-have-stats");
                    for (int i = 0; i < messagenostats.size(); i++) {
                        String text = messagenostats.get(i);
                        jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
                    }
                    return true;

                }

            }
            } else {
            jugador.sendMessage(plugin.name + ChatColor.RED + " this command doesn't exist");

        }


        return true;
    }
}
