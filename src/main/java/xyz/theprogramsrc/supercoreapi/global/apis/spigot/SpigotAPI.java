package xyz.theprogramsrc.supercoreapi.global.apis.spigot;

import xyz.theprogramsrc.supercoreapi.global.utils.Utils;

public class SpigotAPI {

    /**
     * Used to retrieve the last version using the SpigotWebAPI
     * @param pluginId Identifier of the plugin
     * @return Null if there is an error, otherwise you'll get the last version
     */
    public static String getLastVersion(String pluginId){
        String url = "https://api.spigotmc.org/legacy/update.php?resource=" + pluginId;
        return Utils.readWithInputStream(url);
    }
}
