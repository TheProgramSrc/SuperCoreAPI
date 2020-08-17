package xyz.theprogramsrc.supercoreapi.global.utils;

import xyz.theprogramsrc.supercoreapi.global.utils.serverutils.BungeeServerUtils;
import xyz.theprogramsrc.supercoreapi.global.utils.serverutils.SpigotServerUtils;

public class ServerUtils {
    
    public SpigotServerUtils spigot(){
        return new SpigotServerUtils();
    }

    public BungeeServerUtils bungee(){
        return new BungeeServerUtils();
    }

}

