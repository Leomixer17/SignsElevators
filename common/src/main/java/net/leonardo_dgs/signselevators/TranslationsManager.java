package net.leonardo_dgs.signselevators;

import de.leonhard.storage.LightningBuilder;
import de.leonhard.storage.Yaml;
import de.leonhard.storage.internal.settings.DataType;
import de.leonhard.storage.internal.settings.ReloadSettings;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.template.TemplateResolver;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TranslationsManager {
    private static final String langPath = "translations/";
    private final File langFolder;
    private final Map<String, Yaml> langConfigs = new HashMap<>();

    public TranslationsManager(File langFolder) {
        this.langFolder = langFolder;
        if (!langFolder.exists() && !langFolder.mkdirs())
            throw new RuntimeException();
    }

    public void init() {
        String defaultLang = SignsElevatorsProvider.get().getSettingsManager().getDefaultLanguage();
        langConfigs.put(defaultLang, loadLangConfig(defaultLang));
    }

    public Component getMessage(String key, String langCode, Object... substitutions) {
        SettingsManager settings = SignsElevatorsProvider.get().getSettingsManager();
        if (langCode == null || !settings.getPerPlayerLanguage())
            langCode = settings.getDefaultLanguage();

        Yaml langConfig = langConfigs.get(langCode);
        if (langConfig == null) {
            langConfig = loadLangConfig(langCode);
            langConfigs.put(langCode, langConfig);
        }

        return MiniMessage.miniMessage().deserialize(langConfig.getString(key), TemplateResolver.resolving(substitutions));
    }

    public Component getPrefix(String langCode) {
        return getMessage("prefix", langCode);
    }

    public Component getNoElevatorSignFound(String langCode, Object... substitutions) {
        return getMessage("no_elevator_found", langCode, substitutions);
    }

    public Component getDestinationObstructed(String langCode, Object... substitutions) {
        return getMessage("destination_obstructed", langCode, substitutions);
    }

    public Component getDestinationUnsafe(String langCode, Object... substitutions) {
        return getMessage("destination_unsafe", langCode, substitutions);
    }

    public Component getElevatorSuccess(String langCode, Object... substitutions) {
        return getMessage("elevator_used", langCode, substitutions);
    }

    private Yaml loadLangConfig(String langCode) {
        SettingsManager settings = SignsElevatorsProvider.get().getSettingsManager();
        File file = new File(langFolder.toPath().toString(), langCode + ".yml");
        Yaml langConfig = LightningBuilder.fromFile(file)
                .setReloadSettings(ReloadSettings.INTELLIGENT)
                .setDataType(DataType.SORTED)
                .createYaml();

        InputStream resource = getClass().getClassLoader().getResourceAsStream(langPath + langCode + ".yml");
        if (resource == null) {
            resource = getClass().getClassLoader().getResourceAsStream(langPath + settings.getDefaultLanguage() + ".yml");
            if (resource == null)
                resource = getClass().getClassLoader().getResourceAsStream(langPath + "en_us.yml");
        }
        langConfig.addDefaultsFromInputStream(resource);

        return langConfig;
    }
}
