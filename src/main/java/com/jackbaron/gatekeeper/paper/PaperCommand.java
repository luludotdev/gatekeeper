package com.jackbaron.gatekeeper.paper;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import com.jackbaron.gatekeeper.common.Platform;
import com.jackbaron.gatekeeper.common.SlotManager;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static com.jackbaron.gatekeeper.paper.PaperPlugin.LOGGER;

@CommandAlias("gatekeeperbungee")
public final class PaperCommand extends BaseCommand {
    @Default
    @CommandPermission("gatekeeper.about")
    public void onDefault(final @NotNull CommandSender sender) {
        // TODO
    }

    @Subcommand("reload")
    @CommandPermission("gatekeeper.reload.server")
    public void onReload(final @NotNull CommandSender sender) {
        // TODO
    }

    @Subcommand("setslots")
    @CommandPermission("gatekeeper.setslots")
    public void onSetSlots(final @NotNull CommandSender sender, final int slots) {
        assert LOGGER != null;

        // TODO
        try {
            SlotManager.SetSlots(Platform.PAPER, slots);
        } catch (ReflectiveOperationException e) {
            LOGGER.info("Failed to change slots!");
            e.printStackTrace();
        }
    }
}
