package com.jackbaron.gatekeeper.paper;

import co.aikar.commands.PaperCommandManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.logging.Logger;

public final class PaperPlugin extends JavaPlugin {
    @Nullable
    public static JavaPlugin INSTANCE;
    @Nullable
    public static Logger LOGGER;
    @Nullable
    public static PaperCommandManager COMMAND_MANAGER;

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
        COMMAND_MANAGER = new PaperCommandManager(this);
        COMMAND_MANAGER.addSupportedLanguage(Locale.ENGLISH);

        COMMAND_MANAGER.registerCommand(new PaperCommand());
    }
}
