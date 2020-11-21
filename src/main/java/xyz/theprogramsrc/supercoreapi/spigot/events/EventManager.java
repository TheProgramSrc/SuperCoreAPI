package xyz.theprogramsrc.supercoreapi.spigot.events;

import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;

public class EventManager extends SpigotModule {

    @Override
    public void onLoad() {
        this.spigotPlugin.getServer().getMessenger().registerOutgoingPluginChannel(this.spigotPlugin, "BungeeCord");
    }

}
