
package me.pronil.BedWars;

import com.andrei1058.bedwars.proxy.api.ArenaStatus;
import com.andrei1058.bedwars.proxy.api.CachedArena;
import com.andrei1058.bedwars.proxy.api.event.*;
import java.io.File;
import me.pronil.API.Reflection;
import me.pronil.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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
    String lobbyWorld = this.config.getString("lobbyWorldName").trim();

    @EventHandler
    public void onArenaStart(ArenaCacheUpdateEvent e) {
        CachedArena Arena2 = e.getArena();
        final String ArenaName = Arena2.getArenaName();
        final String ArenaGroup = Arena2.getArenaGroup();
        ArenaStatus ArenaStatus = Arena2.getStatus();
        final World lobby = Bukkit.getWorld(this.lobbyWorld);
        if (ArenaStatus == com.andrei1058.bedwars.proxy.api.ArenaStatus.WAITING) {
            if (Arena2.getCurrentPlayers() >= this.config.getInt("minPlayers")) {
                new BukkitRunnable(){

                    public void run() {
                        String arena = "";
                        lobby.getPlayers().forEach(player -> player.sendMessage(" "));
                        Reflection.JSONMessage joinmsg = new Reflection.JSONMessage("");
                        Reflection.JSONMessage.ChatExtra addon = new Reflection.JSONMessage.ChatExtra(BedwarsArenaStartMessageBWP.this.minPlayersPassedText.replace("{arena}", ArenaName).replace("{arenagroup}", ArenaGroup));
                        addon.addClickEvent(Reflection.JSONMessage.ClickEventType.RUN_COMMAND, "/bw join " + ArenaName.toLowerCase() + arena);
                        addon.addHoverEvent(Reflection.JSONMessage.HoverEventType.SHOW_TEXT, BedwarsArenaStartMessageBWP.this.minPlayersPassedTulip.replace("{arena}", ArenaName).replace("{arenagroup}", ArenaGroup));
                        joinmsg.addExtra(addon);
                        lobby.getPlayers().forEach(player -> Reflection.sendChatPacket(player, joinmsg.toString()));
                        lobby.getPlayers().forEach(player -> player.sendMessage(" "));
                    }
                }.runTaskLater(Main.getPlugin(), 10L);
            }
        } else if (ArenaStatus == com.andrei1058.bedwars.proxy.api.ArenaStatus.STARTING) {
            new BukkitRunnable(){

                public void run() {
                    String arena = "";
                    lobby.getPlayers().forEach(player -> player.sendMessage(" "));
                    Reflection.JSONMessage joinmsg = new Reflection.JSONMessage("");
                    Reflection.JSONMessage.ChatExtra addon = new Reflection.JSONMessage.ChatExtra(BedwarsArenaStartMessageBWP.this.arenaStartingText.replace("{arena}", ArenaName).replace("{arenagroup}", ArenaGroup));
                    addon.addClickEvent(Reflection.JSONMessage.ClickEventType.RUN_COMMAND, "/bw join " + ArenaName.toLowerCase() + arena);
                    addon.addHoverEvent(Reflection.JSONMessage.HoverEventType.SHOW_TEXT, BedwarsArenaStartMessageBWP.this.arenaStartingTulip.replace("{arena}", ArenaName).replace("{arenagroup}", ArenaGroup));
                    joinmsg.addExtra(addon);
                    lobby.getPlayers().forEach(player -> Reflection.sendChatPacket(player, joinmsg.toString()));
                    lobby.getPlayers().forEach(player -> player.sendMessage(" "));
                }
            }.runTaskLater(Main.getPlugin(), 10L);
        }
    }
}
 