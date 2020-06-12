package xyz.theprogramsrc.supercoreapi.global.translations;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import xyz.theprogramsrc.supercoreapi.SuperPlugin;
import xyz.theprogramsrc.supercoreapi.global.utils.FileUtils;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;

import java.io.File;

public class TranslationDownloader {

    /**
     * Used to download all translations from github
     * @param core The Plugin
     * @param username The GitHub Username
     * @param repository The GitHub Repository
     * @param folder The folder of the Translations
     */
    public static void downloadFromGitHub(final SuperPlugin<?> core, final String username, final String repository, final String folder){
        JsonParser parser = new JsonParser();
        try{
            String url = "https://api.github.com/repos/{Username}/{Repository}/contents/{Folder}";
            url = url.replace("{Username}", username);
            url = url.replace("{Repository}", repository);
            url = url.replace("{Folder}", folder);
            url = url.replace("{PluginName}", core.getPluginName().toLowerCase());
            String content = Utils.readWithInputStream(url);
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
            }
            core.getTranslationManager().reloadTranslations();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Download a specific translation from web
     * @param core The Plugin
     * @param directUrl The DirectURl (This API doesn't support redirection)
     * @param fileName The name of the file to save
     */
    public static void downloadTranslation(final SuperPlugin<?> core, String directUrl, String fileName){
        try{
            File file = new File(core.getTranslationsFolder(), fileName.endsWith(".lang") ? fileName : (fileName+".lang"));
            file.delete();
            FileUtils.downloadUsingCommons(directUrl, file);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
