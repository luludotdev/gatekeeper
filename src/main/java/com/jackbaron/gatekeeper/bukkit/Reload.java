package com.jackbaron.gatekeeper.bukkit;

import com.jackbaron.gatekeeper.common.Constants;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.jetbrains.annotations.NotNull;

public class Reload implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!sender.hasPermission("gatekeeper.reload.server")) {
            sender.sendMessage(Constants.getErrorPrefix(Plugin.platform) + "You do not have permission to do that!");
            return true;
        }

        Plugin.instance.reloadConfig();
        Plugin.config = Plugin.instance.getConfig();
        sender.sendMessage(Constants.getPrefix(Plugin.platform) + "Gatekeeper config reloaded!");

        return true;
    }
}
