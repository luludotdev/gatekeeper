package com.jackbaron.gatekeeper.common;

import com.jackbaron.gatekeeper.bungee.BungeePlugin;
import com.jackbaron.gatekeeper.paper.PaperPlugin;
import net.md_5.bungee.api.ProxyServer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.logging.Level;

public class SlotManager {
    private static Field maxPlayersField;

    public static void SetSlots(final @NotNull Platform platform, final int slots) throws ReflectiveOperationException {
        if (platform.equals(Platform.PAPER)) {
            setSlotsPaper(slots);
        } else if (platform.equals(Platform.BUNGEE)) {
            setSlotsBungee(slots);
        }
    }

    private static void setSlotsPaper(final int slots) throws ReflectiveOperationException {
        assert PaperPlugin.INSTANCE != null;
        assert PaperPlugin.LOGGER != null;

        // Set Slots
        final Server server = PaperPlugin.INSTANCE.getServer();
        final Method serverGetHandle = server.getClass().getDeclaredMethod("getHandle");
        final Object playerList = serverGetHandle.invoke(server);

        if (maxPlayersField == null) {
            maxPlayersField = getMaxPlayersField(playerList);
        }

        maxPlayersField.setInt(playerList, slots);

        // Save Slots
        final Properties properties = new Properties();
        final File propertiesFile = new File("server.properties");

        try {
            try (InputStream is = new FileInputStream(propertiesFile)) {
                properties.load(is);
            }

            final String maxPlayers = Integer.toString(server.getMaxPlayers());
            if (properties.getProperty("max-players").equals(maxPlayers)) {
                return;
            }

            PaperPlugin.LOGGER.info("Saving max players to server.properties...");
            properties.setProperty("max-players", maxPlayers);

            try (OutputStream os = new FileOutputStream(propertiesFile)) {
                properties.store(os, "Minecraft server properties");
            }
        } catch (IOException e) {
            PaperPlugin.LOGGER.log(Level.SEVERE, "An error occurred while updating the server properties", e);
        }
    }

    private static void setSlotsBungee(final int slots) throws ReflectiveOperationException {
        assert BungeePlugin.INSTANCE != null;

        // Set Slots
        final ProxyServer proxy = BungeePlugin.INSTANCE.getProxy();
        Class<?> configClass = proxy.getConfig().getClass();

        if (!configClass.getSuperclass().equals(Object.class)) {
            configClass = configClass.getSuperclass();
        }

        final Field playerLimitField = configClass.getDeclaredField("playerLimit");
        playerLimitField.setAccessible(true);
        playerLimitField.setInt(proxy.getConfig(), slots);

        // Save Slots
        final Method setMethod = proxy.getConfigurationAdapter().getClass().getDeclaredMethod("set", String.class, Object.class);
        setMethod.setAccessible(true);
        setMethod.invoke(proxy.getConfigurationAdapter(), "player_limit", slots);
    }

    private static Field getMaxPlayersField(Object playerList) throws ReflectiveOperationException {
        final Class<?> playerListClass = playerList.getClass().getSuperclass();

        try {
            final Field field = playerListClass.getDeclaredField("maxPlayers");
            field.setAccessible(true);

            return field;
        } catch (NoSuchFieldException e) {
            for (final Field field : playerListClass.getDeclaredFields()) {
                if (field.getType() != int.class) {
                    continue;
                }

                field.setAccessible(true);

                if (field.getInt(playerList) == Bukkit.getMaxPlayers()) {
                    return field;
                }
            }

            throw new NoSuchFieldException("Unable to find maxPlayers field in " + playerListClass.getName());
        }
    }
}
