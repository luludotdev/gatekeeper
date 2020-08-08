package com.jackbaron.gatekeeper.bukkit;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import org.jetbrains.annotations.NotNull;

public class Events implements Listener {
    @EventHandler
    public void onPlayerLoginEvent(@NotNull PlayerLoginEvent event) {
        FileConfiguration config = Plugin.config;
        boolean enabled = config.getBoolean("enabled", true);
        int reservedSlots = config.getInt("reserved", 0);

        if (!enabled || reservedSlots < 0)
            return;

        Server server = Plugin.instance.getServer();
        Player player = event.getPlayer();

        int playerCount = server.getOnlinePlayers().size();
        int maxPlayers = server.getMaxPlayers();
        int newMax = maxPlayers - reservedSlots;

        boolean hasBypass = player.hasPermission("gatekeeper.bypass");
        boolean canUseReserved = player.hasPermission("gatekeeper.usereserved");

        if (playerCount >= newMax && !canUseReserved) {
            PlayerLoginEvent.Result r = PlayerLoginEvent.Result.KICK_FULL;

            String msgRaw = config.getString("kickMessage", "&oServer is full!");
            String kickMessage = ChatColor.translateAlternateColorCodes('&', msgRaw);

            event.disallow(r, kickMessage);
        }

        if (event.getResult() == PlayerLoginEvent.Result.KICK_FULL && hasBypass) {
            event.allow();
        }
    }
}
