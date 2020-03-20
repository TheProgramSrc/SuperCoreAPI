/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

@SuppressWarnings("unused")
public class LocationUtils{

    private static String SEPARATOR = ":split:";

    public static String serialize(Location location){
        return location.getWorld().getName() + SEPARATOR + location.getBlockX() + SEPARATOR + location.getBlockY() + SEPARATOR + location.getBlockZ() + SEPARATOR + location.getYaw() + SEPARATOR + location.getPitch();
    }

    public static Location deserialize(String serialized){
        String[] s = serialized.split(SEPARATOR);
        return new Location(Bukkit.getWorld(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]), Float.parseFloat(s[4]), Float.parseFloat(s[5]));
    }
}
