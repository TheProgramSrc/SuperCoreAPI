/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.spigot.utils.skintexture;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SkinTextureManager {

    private final HashMap<String, SkinTexture> cache;


    public SkinTextureManager(){
        this.cache = new HashMap<>();
    }

    public SkinTexture getSkin(Player player) {
        if(!this.cache.containsKey(player.getName())){
            SkinTexture skinTexture = Bukkit.getOnlineMode() ? SkinTexture.fromPlayer(player) : SkinTexture.fromMojang(player.getName());
            if(skinTexture == null){
                return null;
            }
            this.cache.put(player.getName(), skinTexture);
        }
        return this.cache.get(player.getName());
    }

    public void clearCache(){
        this.cache.clear();
    }
}
