package com.jackbaron.gatekeeper.bungee;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import net.md_5.bungee.api.CommandSender;
import org.jetbrains.annotations.NotNull;

@CommandAlias("gatekeeperbungee")
public final class BungeeCommand extends BaseCommand {
    @Default
    @CommandPermission("gatekeeper.about")
    public void onDefault(final @NotNull CommandSender sender) {
        // TODO
    }

    @Subcommand("reload")
    @CommandPermission("gatekeeper.reload.proxy")
    public void onReload(final @NotNull CommandSender sender) {
        // TODO
    }

    @Subcommand("setslots")
    @CommandPermission("gatekeeper.setslots")
    public void onSetSlots(final @NotNull CommandSender sender) {
        // TODO
    }
}
