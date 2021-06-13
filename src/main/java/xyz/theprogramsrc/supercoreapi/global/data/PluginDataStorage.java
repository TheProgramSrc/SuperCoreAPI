package xyz.theprogramsrc.supercoreapi.global.data;

import xyz.theprogramsrc.supercoreapi.SuperPlugin;
import xyz.theprogramsrc.supercoreapi.global.files.JsonConfig;

import java.util.UUID;

public class PluginDataStorage extends JsonConfig {

    public PluginDataStorage(SuperPlugin<?> plugin){
        super(plugin.getPluginFolder(), "PluginData.json");
        this.add("stats_id", UUID.randomUUID().toString());
        this.add("debug", "false");
    }

    public boolean isDebugEnabled(){
        return this.getBoolean("debug");
    }
}
