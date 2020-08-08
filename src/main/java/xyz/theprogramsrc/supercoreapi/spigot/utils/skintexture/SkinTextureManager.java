package xyz.theprogramsrc.supercoreapi.spigot.utils.skintexture;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
        if(!this.cache.containsKey(player.getName())){
            SkinTexture skinTexture = Bukkit.getOnlineMode() ? SkinTexture.fromPlayer(player) : SkinTexture.fromMojang(player.getName());
            if(skinTexture == null){
                return null;
            }
            this.cache.put(player.getName(), skinTexture);
        }
        return this.cache.get(player.getName());
    }

    public SkinTexture getSkin(String url){
        if(!this.urls.containsKey(url)){
            SkinTexture texture = SkinTexture.fromURL(url);
            this.urls.put(url, texture);
        }

        return this.urls.get(url);
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
