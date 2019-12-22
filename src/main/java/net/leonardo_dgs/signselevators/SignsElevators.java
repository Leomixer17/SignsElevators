package net.leonardo_dgs.signselevators;

import lombok.Getter;
import net.leonardo_dgs.signselevators.config.Settings;
import org.bstats.bukkit.MetricsLite;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class SignsElevators extends JavaPlugin {

    @Getter
    private static SignsElevators instance;

    @Override
    public void onEnable()
    {
        instance = this;
        Settings.reload();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        new MetricsLite(this);
    }

    @Override
    public void onDisable()
    {

    }

    public static FileConfiguration getSettings()
    {
        return getInstance().getConfig();
    }

}
