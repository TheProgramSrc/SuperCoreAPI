/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.utils;

import org.bukkit.World;

public class EnvironmentUtils {

    public static int getId(World.Environment environment){
        switch(environment){
            case NETHER:
                return -1;
            case THE_END:
                return 1;
            default:
                return 0;
        }
    }
}
