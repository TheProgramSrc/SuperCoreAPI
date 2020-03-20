/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.utils;

import org.bukkit.GameMode;

public class GamemodeUtil{

    public static boolean equals(GameMode gameMode, GameMode anotherGameMode){
        return gameMode.equals(anotherGameMode);
    }

    public static int getValue(GameMode gameMode){
        switch(gameMode){
            case CREATIVE:
                return 1;
            case ADVENTURE:
                return 2;
            case SPECTATOR:
                return 3;
            default:
                return 0;
        }
    }
}
