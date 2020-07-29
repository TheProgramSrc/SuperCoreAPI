package xyz.theprogramsrc.supercoreapi.spigot.events;

import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin;

public class EventManager extends SpigotModule {

    public EventManager(SpigotPlugin plugin){
        super(plugin);
        ((SpigotPlugin) this.plugin).getServer().getMessenger().registerOutgoingPluginChannel(((SpigotPlugin)this.plugin), "BungeeCord");
    }

}
