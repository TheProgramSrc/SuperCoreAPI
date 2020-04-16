/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.events.timer;

public enum Time{
    TICK(1L),
    TWO_TICKS(2L),
    FASTER(5L),
    FAST(10L),
    SEC(20L),
    TWO_SEC(40L),
    SLOWEST(50L),
    THREE_SEC(60L),
    SLOWER(100L),
    QUARTER_MIN(300L),
    HALF_MIN(600L),
    MIN(1200L),
    QUARTER_HOUR(18000L),
    HALF_HOUR(36000L),
    HOUR(72000L);

    private long time;

    Time(long time) {
        this.time = time;
    }

    public long getTime() {
        return this.time;
    }
}
