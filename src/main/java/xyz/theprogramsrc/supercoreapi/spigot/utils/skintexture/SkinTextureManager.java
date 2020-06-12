package xyz.theprogramsrc.supercoreapi.spigot.utils.skintexture;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SkinTextureManager {

    private final HashMap<String, SkinTexture> cache;

    public SkinTextureManager(){
        this.cache = new HashMap<>();
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

    /**
     * Clears the skin cache
     */
    public void clearCache(){
        this.cache.clear();
    }
}
