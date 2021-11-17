package net.leonardo_dgs.signselevators;

public class SignsElevators {
    private final SettingsManager settingsManager;
    private final TranslationsManager translationManager;

    public SignsElevators(SettingsManager settingsManager, TranslationsManager translationManager) {
        this.settingsManager = settingsManager;
        this.translationManager = translationManager;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public TranslationsManager getTranslationManager() {
        return translationManager;
    }
}
