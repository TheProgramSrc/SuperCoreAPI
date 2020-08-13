package xyz.theprogramsrc.supercoreapi.spigot.items;

import xyz.theprogramsrc.supercoreapi.global.utils.Utils;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin;
import xyz.theprogramsrc.supercoreapi.spigot.utils.skintexture.SkinTexture;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public enum Skulls {

    BACK("http://textures.minecraft.net/texture/9c042597eda9f061794fe11dacf78926d247f9eea8ddef39dfbe6022989b8395"),
    ARROW_LEFT("http://textures.minecraft.net/texture/f7aacad193e2226971ed95302dba433438be4644fbab5ebf818054061667fbe2"),
    ARROW_RIGHT("http://textures.minecraft.net/texture/a5fb343b2e7822c7de47abac4c3679f6ad1fa12efc5866c033c7584d6848"),

    ;

    private static LinkedHashMap<String, String> cache = new LinkedHashMap<>();

    private final String url;

    Skulls(String url){
        this.url = url;
    }

    /**
     * Gets the url of the texture
     * @return the url of the texture
     */
    public String asUrl(){
        return this.url;
    }

    /**
     * Gets the SkinTexture of this skull
     * @return the texture of this skull
     */
    public SkinTexture asSkinTexture(){
        return SkinTexture.fromURL(this.asUrl());
    }

    /**
     * Gets a SkinTexture from the GitHub Heads DataBase
     * @param key the key/name of the SkinTexture
     * @return the texture of the specified key
     */
    public static SkinTexture fromDataBase(String key){
        if(cache == null) cache = new LinkedHashMap<>();
        return new SkinTexture(cache.getOrDefault(key, "http://textures.minecraft.net/texture/badc048a7ce78f7dad72a07da27d85c0916881e5522eeed1e3daf217a38c1a"));
    }

    /**
     * Gets a URL from the GitHub Heads DataBase
     * @param key the key/name of the Texture URL
     * @return the URL of the texture
     */
    public static String urlFromDataBase(String key){
        if(cache == null) cache = new LinkedHashMap<>();
        return cache.getOrDefault(key, "http://textures.minecraft.net/texture/badc048a7ce78f7dad72a07da27d85c0916881e5522eeed1e3daf217a38c1a");
    }

    /**
     * Gets all the keys/names of the GitHub Heads DataBase
     * @return all the keys/names of the GitHub Heads DataBase
     */
    public static LinkedList<String> getDataBaseKeys(){
        if(cache == null) cache = new LinkedHashMap<>();
        return new LinkedList<>(cache.keySet());
    }

    /**
     * Caches the Heads DataBase from GitHub
     */
    public static void loadFromGitHub(){
        if(cache == null) cache = new LinkedHashMap<>();
        try {
            List<String> content = Utils.readLinesWithInputStream("https://raw.githubusercontent.com/TheProgramSrc/PluginsResources/master/SuperCoreAPI/Heads");
            for (String line : content) {
                String[] data = line.split(":", 2);
                cache.put(data[0], data[1]);
            }
            SpigotPlugin.i.log("Successfully loaded " + cache.size() + " Heads from the GitHub DataBase");
        } catch (Exception e) {
            SpigotPlugin.i.log("&cError while loading Heads DataBase from GitHub");
            e.printStackTrace();
        }
    }
}
