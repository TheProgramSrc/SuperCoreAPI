/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.spigot.packets.sky;

public enum SkyColor{

    NORMAL(0),
    RAIN(1),
    // FREEZE(0),
    // UNFREEZE(0),
    CRASH(3.4028236E38D),
    BLUE_SCREEN(7),
    YELLOW_SCREEN(15),
    BLACK_AND_RED_SKY(5),
    DARK_RED_SKY(4),
    RED_SKY(3),
    YELLOW_SKY(2),
    NIGHT(-1);

    private final int value;

    SkyColor(int value) {
        this.value = value;
    }

    SkyColor(Double value) {
        this.value = value.intValue();
    }

    public int getValue() {
        return this.value;
    }
}
