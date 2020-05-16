/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.spigot.utils.skintexture;

import com.mojang.authlib.GameProfile;
import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;
import xyz.theprogramsrc.supercoreapi.spigot.utils.ReflectionUtils;

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
