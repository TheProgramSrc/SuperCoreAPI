package xyz.theprogramsrc.supercoreapi.spigot.utils.skintexture;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;
import xyz.theprogramsrc.supercoreapi.spigot.items.Skulls;

import java.util.HashMap;

public class SkinTextureManager {

    private final HashMap<String, SkinTexture> cache;
    private final HashMap<String, SkinTexture> urls;
    private final HashMap<String, SkinTexture> db;
    public static SkinTextureManager INSTANCE;

    public SkinTextureManager(){
        this.cache = new HashMap<>();
        this.urls = new HashMap<>();
        this.db = new HashMap<>();
        INSTANCE = this;
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

    /**
     * Gets a skin from an url
     * @param url the url of the skin
     * @return the skin
     */
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
        this.db.clear();
    }

    /**
     * Gets a SkinTexture from the database
     * @param name the key/name of the SkinTexture
     * @return null if there is any error, otherwise the skin
     */
    public SkinTexture fromDataBase(String name){
        String key = Utils.encodeBase64(name);
        if(!this.db.containsKey(key)){
            this.db.put(key, Skulls.fromDataBase(name));
        }
        return this.db.get(key);
    }

    /**
     * Gets the cache of the player skulls
     * @return the cache of the players skulls
     */
    public HashMap<String, SkinTexture> getPlayersCache() {
        return this.cache;
    }

    /**
     * Gets the cache of the database skulls
     * @return the cache of the database skulls
     */
    public HashMap<String, SkinTexture> getDataBaseCache() {
        return this.db;
    }

    /**
     * Gets the cache of the URLs skulls
     * @return the cache of the URLs skulls
     */
    public HashMap<String, SkinTexture> getURLsCache() {
        return this.urls;
    }
}
