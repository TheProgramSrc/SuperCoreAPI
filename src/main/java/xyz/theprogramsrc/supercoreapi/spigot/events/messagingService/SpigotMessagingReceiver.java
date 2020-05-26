package xyz.theprogramsrc.supercoreapi.spigot.events.messagingService;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin;

public abstract class SpigotMessagingReceiver extends SpigotModule implements PluginMessageListener {

    public SpigotMessagingReceiver(SpigotPlugin plugin) {
        super(plugin);
    }

}
