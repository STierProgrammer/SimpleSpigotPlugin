package me.stierprogrammer.spigotPlugin.Commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class Commands implements CommandExecutor {
    private final HashMap<UUID, HashMap<String, Location>> playerHomes = new HashMap<>();

    @Override
    public boolean onCommand(
        @NotNull CommandSender sender,
        @NotNull Command command,
        @NotNull String label,
        @NotNull String[] args
    ) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command!");

            return false;
        }

        if (command.getName().equalsIgnoreCase("sethome")) {
            if (args.length < 1) {
                player.sendMessage("Usage: /sethome <name>");

                return false;
            }

            String homeName = args[0].toLowerCase();

            playerHomes.putIfAbsent(player.getUniqueId(), new HashMap<>());
            playerHomes.get(player.getUniqueId()).put(homeName, player.getLocation());

            player.sendMessage("Home '" + homeName + "' set!");

            return true;
        }
        else if (command.getName().equalsIgnoreCase("home")) {
            if (args.length < 1) {
                player.sendMessage("Usage: /home <name>");

                return false;
            }

            String homeName = args[0].toLowerCase();

            HashMap<String, Location> homes = playerHomes.get(player.getUniqueId());

            if (homes != null && homes.containsKey(homeName)) {
                player.teleport(homes.get(homeName));
                player.sendMessage("Teleported to home '" + homeName + "'!");
            }
            else {
                player.sendMessage("Home '" + homeName + "' doesn't exist!");
            }

            return true;
        }

        return false;
    }
}
