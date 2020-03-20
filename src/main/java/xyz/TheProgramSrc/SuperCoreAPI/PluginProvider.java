/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI;

public interface PluginProvider {

    void onPluginEnable();

    void onPluginDisable();

    default boolean isPaid(){
        return true;
    }

    default void onPluginLoad(){}
}
