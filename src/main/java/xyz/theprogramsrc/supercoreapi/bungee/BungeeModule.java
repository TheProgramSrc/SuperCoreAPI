package xyz.theprogramsrc.supercoreapi.bungee;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import xyz.theprogramsrc.supercoreapi.SuperModule;
import xyz.theprogramsrc.supercoreapi.bungee.storage.Settings;
import xyz.theprogramsrc.supercoreapi.bungee.utils.tasks.BungeeTasks;

public class BungeeModule extends SuperModule<Listener> implements Listener {

    public BungeeModule(BungeePlugin plugin, boolean registerListener){
        super(plugin);
        if(registerListener) this.listener(this);
        this.onLoad();
    }

    public BungeeModule(BungeePlugin plugin){
        this(plugin, true);
    }

    @Override
    protected void listener(Listener... listeners) {
        ((BungeePlugin)this.plugin).listener(listeners);
    }

    /**
     * Gets the BungeeCord Plugin Settings
     * @return The Plugin Settings
     */
    protected Settings getSettings(){
        return ((BungeePlugin)this.plugin).getSettings();
    }

    /**
     * Gets the proxy
     * @return The proxy
     */
    protected ProxyServer getProxy(){
        return ((BungeePlugin)this.plugin).getProxy();
    }

    /**
     * Gets the BungeeTasks util
     * @return the BungeeTasks util
     */
    protected BungeeTasks getBungeeTasks(){
        return ((BungeePlugin)this.plugin).getBungeeTasks();
    }

}
