package com.jackbaron.gatekeeper.bukkit;

import com.jackbaron.gatekeeper.common.Platform;

import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class Plugin extends JavaPlugin {
    public static JavaPlugin instance;
    public static FileConfiguration config;

    public static Platform platform = Platform.SPIGOT;

    @Override
    public void onEnable() {
        instance = this;

        config = getConfig();
        saveDefaultConfig();

        CommandExecutor about = new About();
        CommandExecutor reload = new Reload();

        PluginCommand gk = getCommand("gatekeeper");
        gk.setTabCompleter((sender, command, s, strings) -> Arrays.asList("reload", "re"));
        gk.setExecutor((sender, command, label, args) -> {
            if (args.length > 0) {
                String arg = args[0].toLowerCase();
                if (arg.equals("reload") || arg.equals("re")) {
                    return reload.onCommand(sender, command, label, args);
                }
            }

            return about.onCommand(sender, command, label, args);
        });

        getServer().getPluginManager().registerEvents(new Events(), this);
    }
}
