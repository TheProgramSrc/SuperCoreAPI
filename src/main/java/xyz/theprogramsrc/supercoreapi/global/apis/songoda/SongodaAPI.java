/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.global.apis.songoda;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;

public class SongodaAPI {

    public static String getLastVersion(String resourceId){
        String content = Utils.readWithInputStream("https://us-central-02.songoda.com/api/v2/products/id/" + resourceId);
        if(content == null) {
            return null;
        }
        JsonElement element = new JsonParser().parse(content).getAsJsonObject().get("data").getAsJsonObject().get("versions").getAsJsonArray().get(0);
        if(element.isJsonNull()){
            return null;
        }

        return element.getAsJsonObject().get("version").getAsString();
    }

    public static int getDownloads(String resourceId){
        String content = Utils.readWithInputStream("https://us-central-02.songoda.com/api/v2/products/id/" + resourceId);
        if(content == null) {
            return 0;
        }
        return new JsonParser().parse(content).getAsJsonObject().get("data").getAsJsonObject().get("downloads").getAsInt();
    }
}
