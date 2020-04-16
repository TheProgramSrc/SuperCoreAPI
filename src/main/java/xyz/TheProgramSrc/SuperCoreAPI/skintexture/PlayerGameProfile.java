/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.skintexture;

import com.mojang.authlib.GameProfile;
import org.bukkit.entity.Player;
import xyz.TheProgramSrc.SuperCoreAPI.utils.ReflectionUtils;
import xyz.TheProgramSrc.SuperCoreAPI.utils.Utils;

public class PlayerGameProfile{

    public static GameProfile get(Player player){
        try{
            Class<?> craftPlayer = ReflectionUtils.getOCBClass("entity.CraftPlayer");
            return ((GameProfile) Utils.requireNonNull(ReflectionUtils.getMethod(craftPlayer, "getProfile")).invoke(player));
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}
