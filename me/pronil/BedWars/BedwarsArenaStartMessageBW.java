
package me.pronil.BedWars;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.arena.Arena;
import java.io.File;

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


public class BedwarsArenaStartMessageBW
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
    public void onArenaStart(GameStateChangeEvent e) {
        Arena Arena2 = (Arena)e.getArena();
        final String ArenaName = Arena2.getDisplayName();
        final String ArenaGroup = Arena2.getGroup();
        final FileConfiguration file = Main.getPlugins().getConfig();
        GameState ArenaStatus = e.getNewState();
        final World Lobby = Bukkit.getWorld(this.lobbyWorld);
        if (ArenaStatus == GameState.waiting) {
            if (Arena2.getPlayers().size() >= this.config.getInt("minPlayers")) {
                new BukkitRunnable() {

                    public void run() {
                        String arena = "";
                        final TextComponent addon = new TextComponent(BedwarsArenaStartMessageBW.this.minPlayersPassedText.replace("{arena}", ArenaName).replace("{arenagroup}", ArenaGroup));
                        addon.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/bw join " + ArenaName.toLowerCase() + arena));
                        addon.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(minPlayersPassedTulip.replace("{arena}", ArenaName).replace("{arenagroup}", ArenaGroup))).create()));
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            if (player.getWorld() == Lobby) {
                                player.sendMessage(" ");
                                player.spigot().sendMessage((BaseComponent) addon);
                                if (sound.equalsIgnoreCase("true")) {
                                    player.playSound(player.getLocation(), Sound.valueOf(file.getString("Sound")), 10.0F, 10.0F);
                                }
                            } }

                    }
                }.runTaskLater(Main.getPlugin(), 10L);
            }
        } else if (ArenaStatus == GameState.starting) {
            new BukkitRunnable(){

                public void run() {
                    String arena = "";
                    final TextComponent addon = new TextComponent(BedwarsArenaStartMessageBW.this.arenaStartingText.replace("{arena}", ArenaName).replace("{arenagroup}", ArenaGroup));
                    addon.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/bw join " + ArenaName.toLowerCase() + arena));
                    addon.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(arenaStartingTulip.replace("{arena}", ArenaName).replace("{arenagroup}", ArenaGroup))).create()));

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.getWorld() == Lobby){
                            player.sendMessage(" ");
                        player.spigot().sendMessage((BaseComponent)addon);
                        if (sound.equalsIgnoreCase("true")){
                            player.playSound(player.getLocation(), Sound.valueOf(file.getString("Sound")), 10.0F, 10.0F);}
                    }}
                }}.runTaskLater(Main.getPlugin(), 10L);
        }
    }
}
 
