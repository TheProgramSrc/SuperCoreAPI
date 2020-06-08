/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.bungee;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import xyz.theprogramsrc.supercoreapi.SuperModule;
import xyz.theprogramsrc.supercoreapi.bungee.storage.Settings;

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

    protected Settings getSettings(){
        return ((BungeePlugin)this.plugin).getSettings();
    }

    protected ProxyServer getProxy(){
        return ((BungeePlugin)this.plugin).getProxy();
    }

}
