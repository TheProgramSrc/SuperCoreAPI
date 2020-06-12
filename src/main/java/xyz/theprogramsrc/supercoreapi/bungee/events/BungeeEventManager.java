package xyz.theprogramsrc.supercoreapi.bungee.events;

import xyz.theprogramsrc.supercoreapi.bungee.BungeeModule;
import xyz.theprogramsrc.supercoreapi.bungee.BungeePlugin;

/**
 * This manages the customized events
 */
public class BungeeEventManager extends BungeeModule {

    public BungeeEventManager(BungeePlugin plugin) {
        super(plugin);
        getProxy().registerChannel(this.getPlugin().getPluginMessagingChannelName());
    }

}
