/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.utils;

import org.bukkit.Difficulty;

public class DifficultyUtils{

    public static int getValue(Difficulty difficulty){
        switch(difficulty){
            case EASY:
                return 1;
            case NORMAL:
                return 2;
            case HARD:
                return 3;
            default:
                return 0;
        }
    }
}
