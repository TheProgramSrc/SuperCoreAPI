package xyz.theprogramsrc.supercoreapi.spigot.utils.skintexture;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;
import xyz.theprogramsrc.supercoreapi.spigot.items.Skulls;

import java.util.HashMap;

public class SkinTextureManager {

    private final HashMap<String, SkinTexture> cache;
    private final HashMap<String, SkinTexture> urls;

    public SkinTextureManager(){
        this.cache = new HashMap<>();
        this.urls = new HashMap<>();
    }

    /**
     * Gets a skin from cache
     * @param player the player
     * @return the skin
     */
    public SkinTexture getSkin(Player player) {
        String key = Utils.encodeBase64(player.getName());
        if(!this.cache.containsKey(key)){
            SkinTexture skinTexture = Bukkit.getOnlineMode() ? SkinTexture.fromPlayer(player) : SkinTexture.fromMojang(player.getName());
            if(skinTexture == null){
                return null;
            }
            this.cache.put(key, skinTexture);
        }
        return this.cache.get(key);
    }

    public SkinTexture getSkin(String url){
        String key = Utils.encodeBase64(url);
        if(!this.urls.containsKey(key)){
            SkinTexture texture = SkinTexture.fromURL(url);
            this.urls.put(key, texture);
        }

        return this.urls.get(key);
    }

    /**
     * Clears the skin cache
     */
    public void clearCache(){
        this.cache.clear();
        this.urls.clear();
    }

    /**
     * Gets a SkinTexture from the database
     * @param key the key of the SkinTexture
     * @return null if there is any error, otherwise the skin
     */
    public SkinTexture fromDataBase(String key){
        return Skulls.fromDataBase(key);
    }
}
