package net.leonardo_dgs.signselevators.bukkit;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.leonardo_dgs.signselevators.SettingsManager;
import net.leonardo_dgs.signselevators.SignsElevators;
import net.leonardo_dgs.signselevators.SignsElevatorsProvider;
import net.leonardo_dgs.signselevators.TranslationsManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class BukkitSignsElevatorsPlugin extends JavaPlugin {
    private BukkitAudiences adventure;

    @Override
    public void onEnable() {
        adventure = BukkitAudiences.create(this);
        SettingsManager settingsManager = new SettingsManager(new File(getDataFolder().getPath(), "config.yml"), "config.yml");
        TranslationsManager translationManager = new TranslationsManager(new File(getDataFolder().getPath(), "translations"));
        SignsElevators instance = new SignsElevators(settingsManager, translationManager);
        SignsElevatorsProvider.register(instance);
        translationManager.init();
        Bukkit.getPluginManager().registerEvents(new BukkitListener(instance, adventure), this);
        new Metrics(this, 5586);
    }

    @Override
    public void onDisable() {
        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
        SignsElevatorsProvider.unregister();
    }
}
