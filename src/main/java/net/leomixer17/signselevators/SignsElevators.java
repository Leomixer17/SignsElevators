package net.leomixer17.signselevators;

import net.leomixer17.signselevators.config.Settings;
import org.bstats.bukkit.MetricsLite;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class SignsElevators extends JavaPlugin {

    private static SignsElevators plugin;

    @Override
    public void onEnable()
    {
        plugin = this;
        Settings.reload();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        new MetricsLite(this);
    }

    @Override
    public void onDisable()
    {

    }

    public static SignsElevators getPlugin()
    {
        return plugin;
    }

    public static FileConfiguration getSettings()
    {
        return getPlugin().getConfig();
    }

}
