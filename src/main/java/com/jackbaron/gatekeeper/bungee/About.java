package com.jackbaron.gatekeeper.bungee;

import com.jackbaron.gatekeeper.common.Constants;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.PluginDescription;

public class About extends Command {
    public About() {
        super("about");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("gatekeeper.about")) {
            TextComponent msg = new TextComponent(Constants.getErrorPrefix(Plugin.platform) + "You do not have permission to do that!");
            sender.sendMessage(msg);
        }

        PluginDescription desc = Plugin.instance.getDescription();
        String prefix = Constants.getPrefix(Plugin.platform);

        boolean isEnabled = Plugin.config.getBoolean("enabled", true);
        int reserved = Plugin.config.getInt("reserved", 0);
        String disabled = ChatColor.RED + "Disabled";
        String enabled = ChatColor.GREEN + "Enabled";

        String[] lines = new String[]{
                prefix + "Gatekeeper v" + desc.getVersion(),
                prefix + "By " + desc.getAuthor(),
                "",
                prefix + "Status: " + (isEnabled ? enabled : disabled),
                prefix + "Reserved Slots: " + Integer.toString(reserved, 10),
        };

        TextComponent msg = new TextComponent(String.join("\n", lines));
        sender.sendMessage(msg);
    }
}
