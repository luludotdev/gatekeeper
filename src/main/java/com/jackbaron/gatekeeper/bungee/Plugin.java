package com.jackbaron.gatekeeper.bungee;

import com.jackbaron.gatekeeper.common.Platform;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class Plugin extends net.md_5.bungee.api.plugin.Plugin {
    static net.md_5.bungee.api.plugin.Plugin instance;
    static Configuration config;

    public static Platform platform = Platform.BUNGEE;

    @Override
    public void onEnable() {
        instance = this;

        boolean loadError = loadConfig();
        if (loadError) {
            getLogger().severe("Failed to load config!");
            return;
        }

        getProxy().getPluginManager().registerCommand(this, new BaseCommand());
        getProxy().getPluginManager().registerListener(this, new Events());
    }

    private boolean loadConfig() {
        try {
            if (!getDataFolder().exists())
                getDataFolder().mkdir();

            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                try (InputStream in = getResourceAsStream("config-bungee.yml")) {
                    Files.copy(in, file.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                    return true;
                }
            }

            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }
}
