package xyz.theprogramsrc.supercoreapi.global.apis.songoda;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;

public class SongodaAPI {

    /**
     * Gets the last version of a plugin
     * @param pluginId The id of the plugin
     * @return The last version of the plugin
     */
    public static String getLastVersion(String pluginId){
        String content = Utils.readWithInputStream("https://songoda.com/api/v2/products/id/" + pluginId);
        if(content == null) {
            return null;
        }
        JsonElement element = new JsonParser().parse(content).getAsJsonObject().get("data").getAsJsonObject().get("versions").getAsJsonArray().get(0);
        if(element.isJsonNull()){
            return null;
        }

        return element.getAsJsonObject().get("version").getAsString();
    }

    /**
     * Gets the amount of downloads of a plugin
     * @param pluginId The id of the plugin
     * @return The amount of downloads
     */
    public static int getDownloads(String pluginId){
        String content = Utils.readWithInputStream("https://songoda.com/api/v2/products/id/" + pluginId);
        if(content == null) {
            return 0;
        }
        return new JsonParser().parse(content).getAsJsonObject().get("data").getAsJsonObject().get("downloads").getAsInt();
    }
}
