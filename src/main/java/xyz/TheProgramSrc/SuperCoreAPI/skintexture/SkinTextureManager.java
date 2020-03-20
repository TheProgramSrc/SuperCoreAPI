package xyz.TheProgramSrc.SuperCoreAPI.skintexture;
/*
    Created by TheProgramSrc at 05-11-2019
*/

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.SuperModule;

import java.util.HashMap;

public class SkinTextureManager extends SuperModule {

    private HashMap<String, SkinTexture> cache;


    public SkinTextureManager(SuperCore core){
        super(core);
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
