package com.jackbaron.gatekeeper.bukkit;

import com.jackbaron.gatekeeper.common.Constants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getServer;

public class SetSlots implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String name, @NotNull String[] args) {
        if (!sender.hasPermission("gatekeeper.setslots")) {
            sender.sendMessage(Constants.getErrorPrefix(Plugin.platform) + "You do not have permission to do that!");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(Constants.getErrorPrefix(Plugin.platform) + "Usage: /gatekeeper setslots <slots>");
            return true;
        }

        try {
            int slots = Integer.parseInt(args[1]);
            changeSlots(slots);

            sender.sendMessage(Constants.getPrefix(Plugin.platform) + "Slots set to "+ slots +".");
        } catch (NumberFormatException e) {
            sender.sendMessage(Constants.getErrorPrefix(Plugin.platform) + "Usage: /gatekeeper setslots <slots>");
        } catch (ReflectiveOperationException e) {
            sender.sendMessage(Constants.getErrorPrefix(Plugin.platform) + "An error occurred while changing slots.");
            Plugin.logger.log(Level.SEVERE, "An error occurred while updating max players", e);
        }

        return true;
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin() == Plugin.instance) saveSlots();
    }

    private void changeSlots(int slots) throws ReflectiveOperationException {
        Method serverGetHandle = Plugin.instance.getServer().getClass().getDeclaredMethod("getHandle");

        Object playerList = serverGetHandle.invoke(getServer());
        Field maxPlayersField = playerList.getClass().getSuperclass().getDeclaredField("maxPlayers");

        maxPlayersField.setAccessible(true);
        maxPlayersField.set(playerList, slots);
    }

    private void saveSlots() {
        Properties properties = new Properties();
        File propertiesFile = new File("server.properties");

        try {
            try (InputStream is = new FileInputStream(propertiesFile)) {
                properties.load(is);
            }

            String maxPlayers = Integer.toString(getServer().getMaxPlayers());

            if (properties.getProperty("max-players").equals(maxPlayers)) {
                return;
            }

            properties.setProperty("max-players", maxPlayers);

            try (OutputStream os = new FileOutputStream(propertiesFile)) {
                properties.store(os, "");
            }
        } catch (IOException e) {
            Plugin.logger.log(Level.SEVERE, "An error occurred while updating the server properties", e);
        }
    }
}
