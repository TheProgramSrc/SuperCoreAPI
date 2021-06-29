package xyz.theprogramsrc.supercoreapi.global.data;

import xyz.theprogramsrc.supercoreapi.SuperPlugin;
import xyz.theprogramsrc.supercoreapi.global.files.JsonConfig;

import java.util.UUID;

public class PluginDataStorage extends JsonConfig {

    public PluginDataStorage(SuperPlugin<?> plugin){
        super(plugin.getPluginFolder(), "PluginData.json");
        this.add("stats_id", UUID.randomUUID().toString());
        this.add("low_resource_usage", false);
        this.add("share_stats", true);
        this.add("debug", "false");
    }

    public boolean isDebugEnabled(){
        return this.getBoolean("debug");
    }

    public boolean isLowResourceUsageEnabled(){
        return this.getBoolean("low_resource_usage");
    }

    public boolean isShareStatsEnabled(){
        return this.getBoolean("share_stats");
    }
}
