package net.leonardo_dgs.signselevators.config;

import net.leonardo_dgs.signselevators.SignsElevators;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public final class Messages {

    private static FileConfiguration messages;
    private static final String langLocation = "net/leonardo_dgs/signselevators/messages/";

    public static String PREFIX;
    public static String NOELEVATORSIGNFOUND;
    public static String DESTINATIONOBSTRUCTED;
    public static String DESTINATIONUNSAFE;

    private static String getMsg(String key) {
        return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(messages.getString(key)));
    }

    static void reload() {
        String langCode = (SignsElevators.getInstance().getResource(langLocation + "messages_" + Settings.LANGUAGE + ".yml") == null) ? "en" : Settings.LANGUAGE;
        final File f = new File(SignsElevators.getInstance().getDataFolder(), "messages_" + langCode + ".yml");
        if (!f.exists())
            try {
                if (!f.createNewFile())
                    throw new IOException();
            } catch (IOException e) {
                e.printStackTrace();
            }
        messages = Settings.loadDefaults(YamlConfiguration.loadConfiguration(f), YamlConfiguration.loadConfiguration(new InputStreamReader(Objects.requireNonNull(SignsElevators.getInstance().getResource(langLocation + "messages_" + langCode + ".yml")))));
        try {
            messages.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadMessages();
    }

    private static void loadMessages() {
        PREFIX = getMsg("prefix");
        NOELEVATORSIGNFOUND = getMsg("no_elevator_sign_found");
        DESTINATIONOBSTRUCTED = getMsg("destination_obstructed");
        DESTINATIONUNSAFE = getMsg("destination_unsafe");
    }

}
