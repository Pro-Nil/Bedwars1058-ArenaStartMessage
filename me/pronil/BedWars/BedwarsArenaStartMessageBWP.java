
package me.pronil.BedWars;


import java.io.File;

import com.andrei1058.bedwars.proxy.api.ArenaStatus;
import com.andrei1058.bedwars.proxy.api.CachedArena;
import com.andrei1058.bedwars.proxy.api.event.ArenaCacheUpdateEvent;
import me.pronil.Main;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;


public class BedwarsArenaStartMessageBWP
        implements Listener {
    File file = new File(Bukkit.getServer().getPluginManager().getPlugin("Bedwars1058-ArenaStartMessage").getDataFolder(), "config.yml");
    FileConfiguration config = YamlConfiguration.loadConfiguration(this.file);
    String minPlayersPassedText = this.config.getString("minPlayersPassedText").replaceAll("&", "§");
    String minPlayersPassedTulip = this.config.getString("minPlayersPassedTulip").replaceAll("&", "§");
    String arenaStartingText = this.config.getString("arenaStartingText").replaceAll("&", "§");
    String arenaStartingTulip = this.config.getString("arenaStartingTulip").replaceAll("&", "§");
    String sound = this.config.getString("play-sound");
    String lobbyWorld = this.config.getString("lobbyWorldName").trim();


    @EventHandler
    public void onArenaStart(ArenaCacheUpdateEvent e) {
        CachedArena Arena2 = e.getArena();
        final String ArenaName = Arena2.getArenaName();
        final String ArenaGroup = Arena2.getArenaGroup();
        final FileConfiguration file = Main.getPlugins().getConfig();
        ArenaStatus ArenaStatus = Arena2.getStatus();
        final World Lobby = Bukkit.getWorld(this.lobbyWorld);
        if (ArenaStatus == com.andrei1058.bedwars.proxy.api.ArenaStatus.WAITING) {
            if (Arena2.getCurrentPlayers() >= this.config.getInt("minPlayers")) {
                new BukkitRunnable() {
                    public void run() {
                        String arena = "";
                        final TextComponent addon = new TextComponent(BedwarsArenaStartMessageBWP.this.minPlayersPassedText.replace("{arena}", ArenaName).replace("{arenagroup}", ArenaGroup));
                        addon.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/bw join " + ArenaName.toLowerCase() + arena));
                        addon.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(minPlayersPassedTulip.replace("{arena}", ArenaName).replace("{arenagroup}", ArenaGroup))).create()));
                        for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.getWorld() == Lobby)
                                    player.sendMessage(" ");
                            player.spigot().sendMessage((BaseComponent)addon);
                            if (sound.equalsIgnoreCase("true")){
                                player.playSound(player.getLocation(), Sound.valueOf(file.getString("Sound")), 10.0F, 10.0F);}
                                    player.sendMessage(" ");
                                }
                    }
                }.runTaskLater(Main.getPlugin(), 10L);
            }
            } else if (ArenaStatus == com.andrei1058.bedwars.proxy.api.ArenaStatus.STARTING) {
            new BukkitRunnable(){

                public void run() {
                    String arena = "";
                    final TextComponent addon = new TextComponent(BedwarsArenaStartMessageBWP.this.arenaStartingText.replace("{arena}", ArenaName).replace("{arenagroup}", ArenaGroup));
                    addon.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/bw join " + ArenaName.toLowerCase() + arena));
                    addon.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(arenaStartingTulip.replace("{arena}", ArenaName).replace("{arenagroup}", ArenaGroup))).create()));

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.getWorld() == Lobby)
                            player.sendMessage(" ");
                        player.spigot().sendMessage((BaseComponent)addon);
                        if (sound.equalsIgnoreCase("true")){
                        player.playSound(player.getLocation(), Sound.valueOf(file.getString("Sound")), 10.0F, 10.0F);}
                        player.sendMessage(" ");
                    }
                }}.runTaskLater(Main.getPlugin(), 10L);
        }
    }
}
 
