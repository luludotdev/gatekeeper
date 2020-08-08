package com.jackbaron.gatekeeper.bungee;

import com.jackbaron.gatekeeper.common.Constants;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class Reload extends Command {
    public Reload() {
        super("reload");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("gatekeeper.reload.proxy")) {
            TextComponent msg = new TextComponent(Constants.getErrorPrefix(Plugin.platform) + "You do not have permission to do that!");
            sender.sendMessage(msg);

            return;
        }

        boolean loadError = Plugin.instance.loadConfig();
        if (loadError) {
            Plugin.logger.severe("Failed to reload config!");

            TextComponent msg = new TextComponent(Constants.getErrorPrefix(Plugin.platform) + "Failed to reload config!");
            sender.sendMessage(msg);

            return;
        }

        TextComponent msg = new TextComponent(Constants.getPrefix(Plugin.platform) + "Gatekeeper config reloaded!");
        sender.sendMessage(msg);
    }
}
