package com.jackbaron.gatekeeper.bungee;

import com.jackbaron.gatekeeper.common.Constants;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;

public class SetSlots extends Command implements Listener {
    public SetSlots() {
        super("setslots");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("gatekeeper.setslots")) {
            TextComponent msg = new TextComponent(Constants.getErrorPrefix(Plugin.platform) + "You do not have permission to do that!");
            sender.sendMessage(msg);

            return;
        }

        if (args.length < 2) {
            TextComponent msg = new TextComponent(Constants.getErrorPrefix(Plugin.platform) + "Usage: /gatekeeper setslots <slots>");
            sender.sendMessage(msg);

            return;
        }

        try {
            int slots = Integer.parseInt(args[1]);
            changeSlots(slots);
            saveSlots(slots);

            TextComponent msg = new TextComponent(Constants.getPrefix(Plugin.platform) + "Slots set to "+ slots +".");
            sender.sendMessage(msg);
        } catch (NumberFormatException e) {
            TextComponent msg = new TextComponent(Constants.getErrorPrefix(Plugin.platform) + "Usage: /gatekeeper setslots <slots>");
            sender.sendMessage(msg);
        } catch (ReflectiveOperationException e) {
            TextComponent msg = new TextComponent(Constants.getErrorPrefix(Plugin.platform) + "An error occurred while changing slots.");
            sender.sendMessage(msg);

            Plugin.logger.log(Level.SEVERE, "An error occurred while updating max players", e);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onProxyPing(ProxyPingEvent e) {
        e.getResponse().getPlayers().setMax(Plugin.instance.getProxy().getConfig().getPlayerLimit());
    }


    private void changeSlots(int slots) throws ReflectiveOperationException {
        Class<?> configClass = Plugin.instance.getProxy().getConfig().getClass();

        if (!configClass.getSuperclass().equals(Object.class)) {
            configClass = configClass.getSuperclass();
        }

        Field playerLimitField = configClass.getDeclaredField("playerLimit");
        playerLimitField.setAccessible(true);
        playerLimitField.set(Plugin.instance.getProxy().getConfig(), slots);
    }

    private void saveSlots(int slots) throws ReflectiveOperationException {
        Method setMethod = Plugin.instance.getProxy().getConfigurationAdapter().getClass().getDeclaredMethod("set", String.class, Object.class);
        setMethod.setAccessible(true);
        setMethod.invoke(Plugin.instance.getProxy().getConfigurationAdapter(), "player_limit", slots);
    }
}
