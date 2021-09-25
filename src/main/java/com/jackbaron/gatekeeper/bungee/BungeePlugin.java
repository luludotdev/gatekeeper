package com.jackbaron.gatekeeper.bungee;

import co.aikar.commands.BungeeCommandManager;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.logging.Logger;

public final class BungeePlugin extends Plugin {
    @Nullable
    public static Plugin INSTANCE;
    @Nullable
    public static Logger LOGGER;
    @Nullable
    public static BungeeCommandManager COMMAND_MANAGER;

    @Override
    public void onEnable() {
        INSTANCE = this;
        LOGGER = getLogger();

        registerCommands();
    }

    @Override
    public void onDisable() {
        COMMAND_MANAGER = null;
        INSTANCE = null;
        LOGGER = null;
    }

    private void registerCommands() {
        COMMAND_MANAGER = new BungeeCommandManager(this);
        COMMAND_MANAGER.addSupportedLanguage(Locale.ENGLISH);

        COMMAND_MANAGER.registerCommand(new BungeeCommand());
    }
}
