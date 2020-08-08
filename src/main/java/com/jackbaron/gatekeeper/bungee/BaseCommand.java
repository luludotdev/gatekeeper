package com.jackbaron.gatekeeper.bungee;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.Arrays;

public class BaseCommand extends Command implements TabExecutor {
    private About about = new About();
    private Reload reload = new Reload();
    private SetSlots setslots = new SetSlots();

    public BaseCommand() {
        super("gatekeeper", null, "gkb");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length > 0) {
            String arg = args[0].toLowerCase();

            if (arg.equals("reload") || arg.equals("re")) {
                reload.execute(sender, args);
                return;
            }

            if (arg.equals("setslots") || arg.equals("slots")) {
                setslots.execute(sender, args);
                return;
            }
        }

        about.execute(sender, args);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Arrays.asList("reload", "setslots");
    }
}
