package net.leonardo_dgs.signselevators;

import de.leonhard.storage.LightningBuilder;
import de.leonhard.storage.internal.FileType;
import de.leonhard.storage.internal.FlatFile;
import de.leonhard.storage.internal.settings.ConfigSettings;
import de.leonhard.storage.internal.settings.ReloadSettings;

import java.io.File;

public class SettingsManager {
    private FlatFile config;

    public SettingsManager(File configFile, String resource) {
        LightningBuilder configBuilder = LightningBuilder
                .fromFile(configFile)
                .addInputStreamFromResource(resource)
                .setReloadSettings(ReloadSettings.INTELLIGENT)
                .setConfigSettings(ConfigSettings.PRESERVE_COMMENTS);
        switch (FileType.fromFile(configFile)) {
            case JSON:
                config = configBuilder.createJson();
                break;
            case YAML:
                config = configBuilder.createYaml();
                break;
            case TOML:
                config = configBuilder.createToml();
                break;
        }
    }

    public String getDefaultLanguage() {
        return config.getString("default_language");
    }

    public boolean getPerPlayerLanguage() {
        return config.getBoolean("per_player_language");
    }

    public boolean getSendMessagesInActionbar() {
        return config.getBoolean("send_messages_in_actionbar");
    }

    public String getSignElevatorUp() {
        return config.getString("elevator.up");
    }

    public String getSignElevatorDown() {
        return config.getString("elevator.down");
    }
}
