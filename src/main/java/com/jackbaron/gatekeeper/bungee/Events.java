package com.jackbaron.gatekeeper.bungee;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

public class Events implements Listener {
    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        Configuration config = Plugin.config;
        boolean enabled = config.getBoolean("enabled", true);
        int reservedSlots = config.getInt("reserved", 0);

        if (!enabled || reservedSlots < 0)
            return;

        ProxyServer server = Plugin.instance.getProxy();
        ProxiedPlayer player = event.getPlayer();

        int playerCount = server.getOnlineCount() - 1;
        int maxPlayers = server.getConfig().getPlayerLimit();
        if (maxPlayers == -1) {
            return;
        }

        int newMax = maxPlayers - reservedSlots;
        boolean canUseReserved = player.hasPermission("gatekeeper.usereserved");

        if (playerCount >= newMax && !canUseReserved) {
            String msgRaw = config.getString("kickMessage", "&oServer is full!");
            String kickMessage = ChatColor.translateAlternateColorCodes('&', msgRaw);

            player.disconnect(new TextComponent(kickMessage));
        }
    }
}
