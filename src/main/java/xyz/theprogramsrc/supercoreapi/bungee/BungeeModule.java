package xyz.theprogramsrc.supercoreapi.bungee;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import xyz.theprogramsrc.supercoreapi.SuperModule;
import xyz.theprogramsrc.supercoreapi.bungee.storage.Settings;
import xyz.theprogramsrc.supercoreapi.bungee.utils.tasks.BungeeTasks;

public class BungeeModule extends SuperModule<Listener> implements Listener {

    protected BungeePlugin bungeePlugin;

    public BungeeModule(boolean registerListener){
        super(BungeePlugin.i);
        this.bungeePlugin = BungeePlugin.i;
        if(registerListener) this.listener(this);
        this.onLoad();
    }

    public BungeeModule(){
        this(true);
    }

    @Override
    protected void listener(Listener... listeners) {
        this.bungeePlugin.listener(listeners);
    }

    /**
     * Gets the BungeeCord Plugin Settings
     * @return The Plugin Settings
     */
    protected Settings getSettings(){
        return this.bungeePlugin.getSettings();
    }

    /**
     * Gets the proxy
     * @return The proxy
     */
    protected ProxyServer getProxy(){
        return this.bungeePlugin.getProxy();
    }

    /**
     * Gets the BungeeTasks util
     * @return the BungeeTasks util
     */
    protected BungeeTasks getBungeeTasks(){
        return this.bungeePlugin.getBungeeTasks();
    }

}
