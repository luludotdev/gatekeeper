package com.jackbaron.gatekeeper.bukkit;

import com.jackbaron.gatekeeper.common.Constants;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import org.jetbrains.annotations.NotNull;

public class About implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!sender.hasPermission("gatekeeper.about")) {
            sender.sendMessage(Constants.getErrorPrefix(Plugin.platform) + "You do not have permission to do that!");
            return true;
        }

        PluginDescriptionFile desc = Plugin.instance.getDescription();
        String prefix = Constants.getPrefix(Plugin.platform);

        boolean isEnabled = Plugin.config.getBoolean("enabled", true);
        int reserved = Plugin.config.getInt("reserved", 0);
        String disabled = ChatColor.RED + "Disabled";
        String enabled = ChatColor.GREEN + "Enabled";

        sender.sendMessage(new String[]{
                prefix + "Gatekeeper v" + desc.getVersion(),
                prefix + "By " + String.join(",", desc.getAuthors()),
                "",
                prefix + "Status: " + (isEnabled ? enabled : disabled),
                prefix + "Reserved Slots: " + Integer.toString(reserved, 10),
        });

        return true;
    }
}
