package net.leonardo_dgs.signselevators;

import lombok.Getter;
import net.leonardo_dgs.signselevators.config.Settings;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class SignsElevators extends JavaPlugin {

    @Getter
    private static SignsElevators instance;

    @Override
    public void onEnable() {
        instance = this;
        Settings.reload();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        new Metrics(this, 5586);
    }

    @Override
    public void onDisable() {

    }

    public static FileConfiguration getSettings() {
        return getInstance().getConfig();
    }

}
