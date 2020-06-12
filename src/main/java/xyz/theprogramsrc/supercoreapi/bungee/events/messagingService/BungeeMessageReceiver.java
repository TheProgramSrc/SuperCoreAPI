package xyz.theprogramsrc.supercoreapi.bungee.events.messagingService;

import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.event.EventHandler;
import xyz.theprogramsrc.supercoreapi.bungee.BungeeModule;
import xyz.theprogramsrc.supercoreapi.bungee.BungeePlugin;

public abstract class BungeeMessageReceiver extends BungeeModule {

    public BungeeMessageReceiver(BungeePlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onReceive(PluginMessageEvent event){
        this.onMessageReceived(event, event.getReceiver(), event.getSender(), event.getData(), event.getTag());
    }

    /**
     * Used to receive messages
     * @param event PluginMessageEvent
     * @param receiver The receiver
     * @param sender The sender
     * @param data The data
     * @param tag The tag
     */
    public abstract void onMessageReceived(PluginMessageEvent event, Connection receiver, Connection sender, byte[] data, String tag);
}
