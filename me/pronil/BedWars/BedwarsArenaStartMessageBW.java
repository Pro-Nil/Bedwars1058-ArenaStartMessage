
package me.pronil.BedWars;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.arena.Arena;
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

public class BedwarsArenaStartMessageBW
        implements Listener {
    File file = new File(Bukkit.getServer().getPluginManager().getPlugin("Bedwars1058-ArenaStartMessage").getDataFolder(), "config.yml");
    FileConfiguration config = YamlConfiguration.loadConfiguration(this.file);
    String minPlayersPassedText = this.config.getString("minPlayersPassedText").replaceAll("&", "§");
    String minPlayersPassedTulip = this.config.getString("minPlayersPassedTulip").replaceAll("&", "§");
    String arenaStartingText = this.config.getString("arenaStartingText").replaceAll("&", "§");
    String arenaStartingTulip = this.config.getString("arenaStartingTulip").replaceAll("&", "§");
    String lobbyWorld = this.config.getString("lobbyWorldName").trim();

    @EventHandler
    public void onArenaStart(GameStateChangeEvent e) {
        Arena Arena2 = (Arena)e.getArena();
        final String ArenaName = Arena2.getDisplayName();
        final String ArenaGroup = Arena2.getGroup();
        GameState ArenaStatus = e.getNewState();
        final World lobby = Bukkit.getWorld(this.lobbyWorld);
        if (ArenaStatus == GameState.waiting) {
            if (Arena2.getPlayers().size() >= this.config.getInt("minPlayers"))
                new BukkitRunnable(){

                    public void run() {
                        String arena = "";
                        lobby.getPlayers().forEach(player -> player.sendMessage(" "));
                        Reflection.JSONMessage joinmsg = new Reflection.JSONMessage("");
                        Reflection.JSONMessage.ChatExtra addon = new Reflection.JSONMessage.ChatExtra(BedwarsArenaStartMessageBW.this.minPlayersPassedText.replace("{arena}", ArenaName).replace("{arenagroup}", ArenaGroup));
                        addon.addClickEvent(Reflection.JSONMessage.ClickEventType.RUN_COMMAND, "/bw join " + ArenaName.toLowerCase() + arena);
                        addon.addHoverEvent(Reflection.JSONMessage.HoverEventType.SHOW_TEXT, BedwarsArenaStartMessageBW.this.minPlayersPassedTulip.replace("{arena}", ArenaName).replace("{arenagroup}", ArenaGroup));
                        joinmsg.addExtra(addon);
                        lobby.getPlayers().forEach(player -> Reflection.sendChatPacket(player, joinmsg.toString()));
                        lobby.getPlayers().forEach(player -> player.sendMessage(" "));
                    }
                }.runTaskLater(Main.getPlugin(), 10L);

    } else if (ArenaStatus == GameState.starting) {
            new BukkitRunnable(){

                public void run() {
                    String arena = "";
                    lobby.getPlayers().forEach(player -> player.sendMessage(" "));
                    Reflection.JSONMessage joinmsg = new Reflection.JSONMessage("");
                    Reflection.JSONMessage.ChatExtra addon = new Reflection.JSONMessage.ChatExtra(BedwarsArenaStartMessageBW.this.arenaStartingText.replace("{arena}", ArenaName).replace("{arenagroup}", ArenaGroup));
                    addon.addClickEvent(Reflection.JSONMessage.ClickEventType.RUN_COMMAND, "/bw join " + ArenaName.toLowerCase() + arena);
                    addon.addHoverEvent(Reflection.JSONMessage.HoverEventType.SHOW_TEXT, BedwarsArenaStartMessageBW.this.arenaStartingTulip.replace("{arena}", ArenaName).replace("{arenagroup}", ArenaGroup));
                    joinmsg.addExtra(addon);
                    lobby.getPlayers().forEach(player -> Reflection.sendChatPacket(player, joinmsg.toString()));
                    lobby.getPlayers().forEach(player -> player.sendMessage(" "));
                }
            }.runTaskLater(Main.getPlugin(), 10L);
        }
    }
}
 