package com.jackbaron.gatekeeper.common;

public class Constants {
    public static String getPrefix(Platform platform) {
        String prefix = ">>> ";

        switch (platform) {
            case SPIGOT:
                return org.bukkit.ChatColor.BLUE + "" +
                        org.bukkit.ChatColor.BOLD + prefix +
                        org.bukkit.ChatColor.GRAY;

            case BUNGEE:
                return net.md_5.bungee.api.ChatColor.BLUE + "" +
                        net.md_5.bungee.api.ChatColor.BOLD + prefix +
                        net.md_5.bungee.api.ChatColor.GRAY;

            default:
                return prefix;
        }
    }

    public static String getErrorPrefix(Platform platform) {
        String prefix = ">>> ";

        switch (platform) {
            case SPIGOT:
                return org.bukkit.ChatColor.RED + "" +
                        org.bukkit.ChatColor.BOLD + prefix +
                        org.bukkit.ChatColor.GRAY;

            case BUNGEE:
                return net.md_5.bungee.api.ChatColor.RED + "" +
                        net.md_5.bungee.api.ChatColor.BOLD + prefix +
                        net.md_5.bungee.api.ChatColor.GRAY;

            default:
                return prefix;
        }
    }

    public static String prefixed(Platform platform, String message) {
        return getPrefix(platform) + message;
    }

    public static String errorPrefixed(Platform platform, String message) {
        return getErrorPrefix(platform) + message;
    }
}
