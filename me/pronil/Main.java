
package me.pronil;

import me.pronil.BedWars.BedwarsArenaStartMessageBW;
import me.pronil.BedWars.BedwarsArenaStartMessageBWP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main
        extends JavaPlugin {
    public static Main getPlugins() {
        return JavaPlugin.getPlugin(Main.class);
    }
    public static Main instance;
    String pluginName = "Bedwars1058-ArenaStartMessage";
    String pluginVersion = getDescription().getVersion();
    private static Plugin plugin;

    public void onEnable() {
        instance = this;
        plugin = this;
        getConfig().options().copyDefaults(true);
        saveConfig();
        initHooks();
    }
    public boolean isBedWars1058Present() {
        return Bukkit.getPluginManager().getPlugin("BedWars1058") != null;
    }

    public boolean isBedWarsProxyPresent() {
        return Bukkit.getPluginManager().getPlugin("BedWarsProxy") != null;
    }
    private void initHooks() {

        if (isBedWars1058Present()) {
            PluginManager pm = this.getServer().getPluginManager();
            pm.registerEvents(new BedwarsArenaStartMessageBW(), this);
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r "));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r "));
            Bukkit.getConsoleSender().sendMessage("--------------------------------------------------------------------");
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r "));
            Bukkit.getConsoleSender().sendMessage(this.pluginName + " v" + this.pluginVersion);
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r "));
            Bukkit.getConsoleSender().sendMessage("Successfully Loaded");
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r "));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&fBedWars1058 &7found and hooked successfully."));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r "));
            Bukkit.getConsoleSender().sendMessage("Author - Pro_Nil");
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r "));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Running Java &f" + System.getProperty("java.version")));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Running &f" + Bukkit.getServer().getName() + " &7fork &fv" + Bukkit.getServer().getBukkitVersion()));
            Bukkit.getConsoleSender().sendMessage("--------------------------------------------------------------------");
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r "));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r "));

        } else if (isBedWarsProxyPresent()) {
            PluginManager pm = this.getServer().getPluginManager();
            pm.registerEvents(new BedwarsArenaStartMessageBWP(), this);
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r "));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r "));
            Bukkit.getConsoleSender().sendMessage("--------------------------------------------------------------------");
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r "));
            Bukkit.getConsoleSender().sendMessage(this.pluginName + " v" + this.pluginVersion);
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r "));
            Bukkit.getConsoleSender().sendMessage("Successfully Loaded");
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r "));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&fBedWarsProxy &7found and hooked successfully."));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r "));
            Bukkit.getConsoleSender().sendMessage("Author - Pro_Nil");
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r "));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Running Java &f" + System.getProperty("java.version")));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Running &f" + Bukkit.getServer().getName() + " &7fork &fv" + Bukkit.getServer().getBukkitVersion()));
            Bukkit.getConsoleSender().sendMessage("--------------------------------------------------------------------");
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r "));
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r "));


        } else {
            Bukkit.getLogger().severe("There is no BedWars plugin installed!");
            Bukkit.getLogger().severe("Disabling...");
            Bukkit.getPluginManager().disablePlugin(this);
        }



        }

  public static Plugin getPlugin() {
    return plugin;
}

    }
