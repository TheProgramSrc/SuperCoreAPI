/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.translation.downloader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.utils.FileUtils;
import xyz.TheProgramSrc.SuperCoreAPI.utils.Utils;

import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public class TranslationDownloader {

    public static boolean downloadFromGitHub(final SuperCore core, final String username, final String repository, final String folder){
        AtomicReference<Boolean> result = new AtomicReference<>(false);
        JsonParser parser = new JsonParser();
        core.getTaskUtil().runTaskLater(0,()->{
            try{
                String url = "https://api.github.com/repos/{Username}/{Repository}/contents/{Folder}";
                url = url.replace("{Username}", username);
                url = url.replace("{Repository}", repository);
                url = url.replace("{Folder}", folder);
                url = url.replace("{PluginName}", core.getName().toLowerCase());
                String content = Utils.readWithIO(url);
                if(content != null){
                    JsonArray availableFiles = parser.parse(content).getAsJsonArray();
                    for (JsonElement jsonElement : availableFiles) {
                        JsonObject json = jsonElement.getAsJsonObject();
                        String name = json.get("name").getAsString();
                        if(name.endsWith(".lang") && name.contains("_")){
                            String download = json.get("download_url").getAsString();
                            File file = new File(core.getTranslationsFolder(), name);
                            file.delete();
                            FileUtils.downloadUsingCommons(download, file);
                        }
                    }
                    result.set(true);
                }else{
                    result.set(false);
                }
            }catch (Exception ex){
                core.debug(ex);
                result.set(false);
            }
        });
        return result.get();
    }
}
