package net.leonardo_dgs.signselevators;

import me.lucko.helper.utils.Players;
import net.leonardo_dgs.signselevators.config.Messages;
import net.leonardo_dgs.signselevators.config.Settings;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public final class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            return;
        if (!isSign(Objects.requireNonNull(event.getClickedBlock())))
            return;
        if (!isElevatorSign((Sign) event.getClickedBlock().getState()))
            return;

        Sign sign = (Sign) event.getClickedBlock().getState();
        Location destinationSignLocation = findDestination(event.getClickedBlock().getLocation(), sign.getLine(1).equalsIgnoreCase(Settings.SIGNLINEUP));
        if (destinationSignLocation == null)
        {
            if (Settings.SENDMESSAGESINACTIONBAR)
                Players.sendActionBar(Messages.PREFIX + Messages.NOELEVATORSIGNFOUND, event.getPlayer());
            else
                event.getPlayer().sendMessage(Messages.PREFIX + Messages.NOELEVATORSIGNFOUND);
            return;
        }
        Location destinationLocation = event.getPlayer().getLocation();
        destinationLocation.setY(destinationSignLocation.getY() + (event.getPlayer().getLocation().getY() - sign.getLocation().getY()));
        if (isObstructed(destinationLocation))
        {
            if (Settings.SENDMESSAGESINACTIONBAR)
                Players.sendActionBar(Messages.PREFIX + Messages.DESTINATIONOBSTRUCTED, event.getPlayer());
            else
                event.getPlayer().sendMessage(Messages.PREFIX + Messages.DESTINATIONOBSTRUCTED);
            return;
        }
        if (!isSafe(destinationLocation))
        {
            if (Settings.SENDMESSAGESINACTIONBAR)
                Players.sendActionBar(Messages.PREFIX + Messages.DESTINATIONUNSAFE, event.getPlayer());
            else
                event.getPlayer().sendMessage(Messages.PREFIX + Messages.DESTINATIONUNSAFE);
            return;
        }

        Sign destinationSign = (Sign) destinationSignLocation.getBlock().getState();
        event.getPlayer().teleport(destinationLocation);
        if (destinationSign.getLine(0).isEmpty())
            return;
        if (Settings.SENDMESSAGESINACTIONBAR)
            Players.sendActionBar(Messages.PREFIX + destinationSign.getLine(0), event.getPlayer());
        else
            event.getPlayer().sendMessage(Messages.PREFIX + destinationSign.getLine(0));
    }

    private static boolean isSign(Block block)
    {
        Material type = block.getType();
        return type.equals(Material.ACACIA_SIGN) || type.equals(Material.ACACIA_WALL_SIGN) ||
                type.equals(Material.BIRCH_SIGN) || type.equals(Material.BIRCH_WALL_SIGN) ||
                type.equals(Material.DARK_OAK_SIGN) || type.equals(Material.DARK_OAK_WALL_SIGN) ||
                type.equals(Material.JUNGLE_SIGN) || type.equals(Material.JUNGLE_WALL_SIGN) ||
                type.equals(Material.OAK_SIGN) || type.equals(Material.OAK_WALL_SIGN) ||
                type.equals(Material.SPRUCE_SIGN) || type.equals(Material.SPRUCE_WALL_SIGN);
    }

    private static boolean isElevatorSign(Sign sign)
    {
        String line = sign.getLine(1);
        return line.equalsIgnoreCase(Settings.SIGNLINEUP) || line.equalsIgnoreCase(Settings.SIGNLINEDOWN);
    }

    private static Location findDestination(Location location, boolean up)
    {
        if (up)
            for (int i = location.getBlockY() + 1; i <= 256; i++)
            {
                location.setY(i);
                Block block = location.getBlock();
                if (isSign(block) && isElevatorSign((Sign) block.getState()))
                    return location;
            }
        else
            for (int i = location.getBlockY() - 1; i >= 0; i--)
            {
                location.setY(i);
                Block block = location.getBlock();
                if (isSign(block) && isElevatorSign((Sign) block.getState()))
                    return location;
            }
        return null;
    }

    private static boolean isObstructed(Location loc)
    {
        Location above = loc.clone();
        above.setY(loc.getBlockY() + 1);
        return !Settings.VOIDBLOCKS.contains(loc.getBlock().getType()) || !Settings.VOIDBLOCKS.contains(above.getBlock().getType());
    }

    private static boolean isSafe(Location loc)
    {
        Location above = loc.clone();
        above.setY(loc.getY() + 1);
        Location below = loc.clone();
        below.setY(loc.getY() - 1);
        boolean aboveLocSafe = !Settings.UNSAFEBLOCKS.contains(above.getBlock().getType()) || Settings.VOIDBLOCKS.contains(above.getBlock().getType());
        boolean locSafe = !Settings.UNSAFEBLOCKS.contains(loc.getBlock().getType()) || Settings.VOIDBLOCKS.contains(loc.getBlock().getType());
        boolean belowLocSafe = !Settings.UNSAFEBLOCKS.contains(below.getBlock().getType()) || Settings.FLOORSAFEBLOCKS.contains(below.getBlock().getType());
        return aboveLocSafe && locSafe && belowLocSafe;
    }

}
