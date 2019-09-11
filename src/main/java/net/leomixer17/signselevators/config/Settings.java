package net.leomixer17.signselevators.config;

import net.leomixer17.signselevators.SignsElevators;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashSet;
import java.util.Set;

import static org.bukkit.Material.*;

public final class Settings {

    static String LANGUAGE;
    public static String SIGNLINEUP;
    public static String SIGNLINEDOWN;
    public static boolean SENDMESSAGESINACTIONBAR;
    public static Set<Material> UNSAFEBLOCKS;
    public static Set<Material> VOIDBLOCKS;
    public static Set<Material> FLOORSAFEBLOCKS;

    public static void reload()
    {
        SignsElevators.getPlugin().saveDefaultConfig();
        loadSettings();
        Messages.reload();
    }

    static YamlConfiguration loadDefaults(Configuration conf, Configuration defconf)
    {
        final YamlConfiguration finalConfig = new YamlConfiguration();
        for (final String s : defconf.getKeys(true))
            if (conf.get(s) != null)
                finalConfig.set(s, conf.get(s));
            else
                finalConfig.set(s, defconf.get(s));

        for (final String s : conf.getKeys(true))
            if (finalConfig.get(s) == null)
                finalConfig.set(s, conf.get(s));

        return finalConfig;
    }

    private static void loadSettings()
    {
        LANGUAGE = SignsElevators.getSettings().getString("language");
        SIGNLINEUP = SignsElevators.getSettings().getString("sign_line_up");
        SIGNLINEDOWN = SignsElevators.getSettings().getString("sign_line_down");
        SENDMESSAGESINACTIONBAR = SignsElevators.getSettings().getBoolean("send_messages_in_action_bar");
        UNSAFEBLOCKS = getUnsafeBlocks();
        VOIDBLOCKS = getVoidBlocks();
        FLOORSAFEBLOCKS = getFloorSafeBlocks();
    }

    private static Set<Material> getUnsafeBlocks()
    {
        Set<Material> blocks = new HashSet<>(getVoidBlocks());
        blocks.add(CACTUS);
        blocks.add(COBWEB);
        blocks.add(LAVA);
        blocks.add(MAGMA_BLOCK);
        return blocks;
    }

    private static Set<Material> getFloorSafeBlocks()
    {
        Set<Material> blocks = new HashSet<>();
        blocks.add(BLACK_CARPET);
        blocks.add(BLUE_CARPET);
        blocks.add(BROWN_CARPET);
        blocks.add(CYAN_CARPET);
        return blocks;
    }

    private static Set<Material> getVoidBlocks()
    {
        Set<Material> blocks = new HashSet<>();
        blocks.add(ACACIA_BUTTON);
        blocks.add(ACACIA_DOOR);
        blocks.add(ACACIA_PRESSURE_PLATE);
        blocks.add(ACACIA_SAPLING);
        blocks.add(ACACIA_SIGN);
        blocks.add(ACACIA_WALL_SIGN);
        blocks.add(ACACIA_TRAPDOOR);
        blocks.add(ACTIVATOR_RAIL);
        blocks.add(AIR);
        blocks.add(CAVE_AIR);
        blocks.add(VOID_AIR);
        blocks.add(ALLIUM);
        blocks.add(ARMOR_STAND);
        blocks.add(AZURE_BLUET);
        blocks.add(BAMBOO);
        blocks.add(BAMBOO_SAPLING);
        blocks.add(BEETROOTS);
        blocks.add(BIRCH_BUTTON);
        blocks.add(BIRCH_DOOR);
        blocks.add(BIRCH_PRESSURE_PLATE);
        blocks.add(BIRCH_SAPLING);
        blocks.add(BIRCH_SIGN);
        blocks.add(BIRCH_WALL_SIGN);
        blocks.add(BIRCH_SLAB);
        blocks.add(BIRCH_TRAPDOOR);
        blocks.add(BLACK_BANNER);
        blocks.add(BLACK_CARPET);
        blocks.add(BLUE_BANNER);
        blocks.add(BLUE_CARPET);
        blocks.add(BLUE_ORCHID);
        blocks.add(BRAIN_CORAL);
        blocks.add(BROWN_BANNER);
        blocks.add(BROWN_CARPET);
        blocks.add(BROWN_MUSHROOM);
        blocks.add(BUBBLE_CORAL);
        blocks.add(BUBBLE_CORAL_FAN);
        blocks.add(CORNFLOWER);
        blocks.add(CYAN_BANNER);
        blocks.add(CYAN_CARPET);
        blocks.add(DANDELION);
        blocks.add(DARK_OAK_BUTTON);
        blocks.add(DARK_OAK_DOOR);
        blocks.add(DARK_OAK_PRESSURE_PLATE);
        blocks.add(DARK_OAK_SAPLING);
        blocks.add(DARK_OAK_SIGN);
        blocks.add(DARK_OAK_WALL_SIGN);
        blocks.add(DARK_OAK_SLAB);
        blocks.add(DEAD_BRAIN_CORAL);
        blocks.add(DEAD_BRAIN_CORAL_FAN);
        blocks.add(DEAD_BUBBLE_CORAL);
        blocks.add(DEAD_BUBBLE_CORAL_FAN);
        blocks.add(DEAD_BUSH);
        blocks.add(DEAD_FIRE_CORAL);
        blocks.add(DEAD_FIRE_CORAL_FAN);
        blocks.add(DEAD_HORN_CORAL);
        blocks.add(DEAD_HORN_CORAL_BLOCK);
        blocks.add(DEAD_HORN_CORAL_FAN);
        blocks.add(DEAD_TUBE_CORAL);
        blocks.add(DEAD_TUBE_CORAL_FAN);
        blocks.add(DETECTOR_RAIL);
        blocks.add(FERN);
        blocks.add(FIRE_CORAL);
        blocks.add(FIRE_CORAL_FAN);
        blocks.add(GRASS);
        blocks.add(GRAY_BANNER);
        blocks.add(GREEN_BANNER);
        blocks.add(HEAVY_WEIGHTED_PRESSURE_PLATE);
        blocks.add(HORN_CORAL);
        blocks.add(HORN_CORAL_FAN);
        blocks.add(JUNGLE_BUTTON);
        blocks.add(JUNGLE_DOOR);
        blocks.add(JUNGLE_PRESSURE_PLATE);
        blocks.add(JUNGLE_SAPLING);
        blocks.add(JUNGLE_SIGN);
        blocks.add(JUNGLE_WALL_SIGN);
        blocks.add(JUNGLE_TRAPDOOR);
        blocks.add(KELP_PLANT);
        blocks.add(LADDER);
        blocks.add(LARGE_FERN);
        blocks.add(LEVER);
        blocks.add(LIGHT_BLUE_BANNER);
        blocks.add(LIGHT_BLUE_CARPET);
        blocks.add(LIGHT_GRAY_BANNER);
        blocks.add(LIGHT_WEIGHTED_PRESSURE_PLATE);
        blocks.add(LILAC);
        blocks.add(LILY_OF_THE_VALLEY);
        blocks.add(LILY_PAD);
        blocks.add(LIME_BANNER);
        blocks.add(LIME_CARPET);
        blocks.add(MAGENTA_BANNER);
        blocks.add(MAGENTA_CARPET);
        blocks.add(NETHER_WART);
        blocks.add(OAK_BUTTON);
        blocks.add(OAK_DOOR);
        blocks.add(OAK_PRESSURE_PLATE);
        blocks.add(OAK_SAPLING);
        blocks.add(OAK_SIGN);
        blocks.add(OAK_WALL_SIGN);
        blocks.add(OAK_TRAPDOOR);
        blocks.add(ORANGE_BANNER);
        blocks.add(ORANGE_CARPET);
        blocks.add(ORANGE_TULIP);
        blocks.add(OXEYE_DAISY);
        blocks.add(PAINTING);
        blocks.add(PEONY);
        blocks.add(PINK_BANNER);
        blocks.add(PINK_CARPET);
        blocks.add(PINK_TULIP);
        blocks.add(POPPY);
        blocks.add(POWERED_RAIL);
        blocks.add(PURPLE_BANNER);
        blocks.add(PURPLE_CARPET);
        blocks.add(RAIL);
        blocks.add(RED_BANNER);
        blocks.add(RED_CARPET);
        blocks.add(RED_MUSHROOM);
        blocks.add(RED_TULIP);
        blocks.add(REDSTONE_WIRE);
        blocks.add(REDSTONE_TORCH);
        blocks.add(ROSE_BUSH);
        blocks.add(SEAGRASS);
        blocks.add(SNOW);
        blocks.add(SPRUCE_BUTTON);
        blocks.add(SPRUCE_DOOR);
        blocks.add(SPRUCE_PRESSURE_PLATE);
        blocks.add(SPRUCE_SAPLING);
        blocks.add(SPRUCE_SIGN);
        blocks.add(SPRUCE_WALL_SIGN);
        blocks.add(SPRUCE_TRAPDOOR);
        blocks.add(STONE_BUTTON);
        blocks.add(STONE_PRESSURE_PLATE);
        blocks.add(STRING);
        blocks.add(SUGAR_CANE);
        blocks.add(SUNFLOWER);
        blocks.add(SWEET_BERRIES);
        blocks.add(TALL_GRASS);
        blocks.add(TORCH);
        blocks.add(TRIPWIRE_HOOK);
        blocks.add(TUBE_CORAL);
        blocks.add(TUBE_CORAL_FAN);
        blocks.add(VINE);
        blocks.add(WHITE_BANNER);
        blocks.add(WHITE_CARPET);
        blocks.add(WHITE_TULIP);
        blocks.add(WITHER_ROSE);
        blocks.add(YELLOW_BANNER);
        blocks.add(YELLOW_CARPET);
        return blocks;
    }

}
