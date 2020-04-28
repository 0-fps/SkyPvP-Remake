package me.fps.skypvp.scoreboard;

import me.clip.placeholderapi.PlaceholderAPI;
import me.fps.skypvp.netherboard.BPlayerBoard;
import me.fps.skypvp.netherboard.Netherboard;
import me.fps.skypvp.skypvp;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class Scoreboard {
    private skypvp plugin;

    public Scoreboard(skypvp plugin){
        this.plugin = plugin;
    }


    public void initScoreboard(){
        Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()){
                    updateScoreboard(player);
                }
            }
        },0,20);
    }




    private void updateScoreboard(Player player) {
        FileConfiguration config = plugin.getConfig();
        String configworld = config.getString("Config.Spawn.world");
        String playerworld = player.getWorld().getName();
        FileConfiguration players = plugin.getPlayers();
        int kills = players.getInt("Players." + player.getUniqueId() + ".kills");
        int deaths = players.getInt("Players." + player.getUniqueId() + ".deaths");
        int points = players.getInt("Players." + player.getUniqueId() + ".points");
        boolean scoreuse = config.getBoolean("Scoreboard.use");
        String prestige = players.getString("Players." + player.getUniqueId() + ".prestige");
        String vaultrank = "%vault_rank%";
        vaultrank = PlaceholderAPI.setPlaceholders(player.getPlayer(), vaultrank);
        String vaultmoney = "%vault_eco_balance_formatted%";
        vaultmoney = PlaceholderAPI.setPlaceholders(player.getPlayer(), vaultmoney);
        if (scoreuse != false) {
            if (configworld.toLowerCase().equals(playerworld.toLowerCase())) {
                BPlayerBoard board = Netherboard.instance().getBoard(player);
                if (board == null) {
                    board = Netherboard.instance().createBoard(player, "scorboard");
                }
                List<String> lines = config.getStringList("Scoreboard.text");
                for (int i = 0; i < lines.size(); i++) {
                    board.set(ChatColor.translateAlternateColorCodes('&', lines.get(i).replace("%deaths%", deaths + "").replace("%kills%", kills + "").replace("%points%", points + "").replace("%prestige%", prestige + "")).replace("%player%", player.getName() + "").replace("%vault_rank%", vaultrank + "").replace("%vault_eco_balance_formatted%", vaultmoney+""), lines.size() - (i));
                }
                board.setName(ChatColor.translateAlternateColorCodes('&', config.getString("Scoreboard.title")));
            } else {
                if (Netherboard.instance().getBoard(player) != null) {
                    Netherboard.instance().deleteBoard(player);
                }
            }

        }
    }
}
