package xyz.theprogramsrc.supercoreapi.global.apis.groupez;

import xyz.theprogramsrc.supercoreapi.global.utils.Utils;

public class GroupeZAPI {

	  /**
     * Used to retrieve the last version using the GroupeZ API
     * 
     * @param pluginId Identifier of the plugin
     * @return Null if there is an error, otherwise you'll get the last version
     */
    public static String getLastVersion(String pluginId){
        String url = "https://groupez.dev/api/v1/resource/version/" + pluginId;
        return Utils.readWithInputStream(url);
    }
}
