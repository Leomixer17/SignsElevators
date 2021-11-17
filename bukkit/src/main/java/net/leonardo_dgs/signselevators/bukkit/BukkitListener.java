package net.leonardo_dgs.signselevators.bukkit;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.leonardo_dgs.signselevators.SettingsManager;
import net.leonardo_dgs.signselevators.SignsElevators;
import net.leonardo_dgs.signselevators.TranslationsManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class BukkitListener implements Listener {
    private final SettingsManager settings;
    private final TranslationsManager translations;
    private final BukkitAudiences adventure;

    public BukkitListener(SignsElevators instance, BukkitAudiences adventure) {
        settings = instance.getSettingsManager();
        translations = instance.getTranslationManager();
        this.adventure = adventure;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        if (event.getClickedBlock() == null || !(event.getClickedBlock().getState() instanceof Sign))
            return;
        Sign sign = (Sign) event.getClickedBlock().getState();
        if (!isElevatorSign(sign))
            return;
        Player player = event.getPlayer();
        if (!player.hasPermission("signselevators.use"))
            return;

        Audience playerAudience = adventure.player(player);
        Location location = event.getClickedBlock().getLocation();
        boolean sendMessagesInActionbar = settings.getSendMessagesInActionbar();

        boolean up = sign.getLine(1).equalsIgnoreCase(settings.getSignElevatorUp());
        Location destinationSignLocation = up ? findDestinationUp(location) : findDestinationDown(location);
        if (destinationSignLocation == null) {
            Component message = translations.getNoElevatorSignFound(player.getLocale(), "prefix", translations.getPrefix(player.getLocale()));
            if (sendMessagesInActionbar)
                playerAudience.sendActionBar(message);
            else
                playerAudience.sendMessage(message);
            return;
        }

        Location destinationLocation = event.getPlayer().getLocation();
        destinationLocation.setY(destinationSignLocation.getY() + (player.getLocation().getY() - sign.getLocation().getY()));

        if (isObstructed(destinationLocation)) {
            Component message = translations.getDestinationObstructed(player.getLocale(), "prefix", translations.getPrefix(player.getLocale()));
            if (sendMessagesInActionbar)
                playerAudience.sendActionBar(message);
            else
                playerAudience.sendMessage(message);
            return;
        }
        if (!isSafe(destinationLocation)) {
            Component message = translations.getDestinationUnsafe(player.getLocale(), "prefix", translations.getPrefix(player.getLocale()));
            if (sendMessagesInActionbar)
                playerAudience.sendActionBar(message);
            else
                playerAudience.sendMessage(message);
            return;
        }

        Sign destinationSign = (Sign) destinationSignLocation.getBlock().getState();
        player.teleport(destinationLocation);
        if (!destinationSign.getLine(0).isEmpty()) {
            Component message = translations.getElevatorSuccess(player.getLocale(),
                    "prefix", translations.getPrefix(player.getLocale()),
                    "destination_elevator_name", destinationSign.getLine(0),
                    "elevator_name", sign.getLine(0));
            if (sendMessagesInActionbar)
                playerAudience.sendActionBar(message);
            else
                playerAudience.sendMessage(message);
        }
    }

    private boolean isElevatorSign(Sign sign) {
        String line = sign.getLine(1);
        return line.equalsIgnoreCase(settings.getSignElevatorUp()) || line.equalsIgnoreCase(settings.getSignElevatorDown());
    }

    private Location findDestinationUp(Location location) {
        for (int i = location.getBlockY() + 1; i <= 320; i++) {
            location.setY(i);
            BlockState blockState = location.getBlock().getState();
            if (blockState instanceof Sign && isElevatorSign((Sign) blockState))
                return location;
        }
        return null;
    }

    private Location findDestinationDown(Location location) {
        for (int i = location.getBlockY() - 1; i >= -64; i--) {
            location.setY(i);
            BlockState blockState = location.getBlock().getState();
            if (blockState instanceof Sign && isElevatorSign((Sign) blockState))
                return location;
        }
        return null;
    }

    private static boolean isObstructed(Location loc) {
        Location above = loc.clone();
        above.setY(loc.getBlockY() + 1);
        return !loc.getBlock().isPassable() || !above.getBlock().isPassable();
    }

    private static boolean isSafe(Location loc) {
        Location above = loc.clone();
        above.setY(loc.getY() + 1);
        Location below = loc.clone();
        below.setY(loc.getY() - 1);
        boolean aboveLocSafe = isSafe(above.getBlock()) && above.getBlock().isPassable();
        boolean locSafe = isSafe(loc.getBlock()) && loc.getBlock().isPassable();
        boolean belowLocSafe = isSafe(below.getBlock()) && !below.getBlock().isPassable();
        return aboveLocSafe && locSafe && belowLocSafe;
    }

    private static boolean isSafe(Block block) {
        switch (block.getType()) {
            case CACTUS:
            case COBWEB:
            case LAVA:
            case MAGMA_BLOCK:
                return false;
            default:
                return true;
        }
    }
}
