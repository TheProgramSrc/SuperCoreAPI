package xyz.theprogramsrc.supercoreapi.global.updater;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;

public abstract class SongodaUpdateChecker {

    private final String apiEndpoint;

    public SongodaUpdateChecker(String product){
        this.apiEndpoint = "https://songoda.com/api/v2/products/" + product;
    }

    public void checkUpdates(){
        try{
            String content = Utils.readWithInputStream(this.apiEndpoint);
            if(content == null){
                this.onFailCheck();
            }else{
                JsonObject data = new JsonParser().parse(content).getAsJsonObject().get("data").getAsJsonObject();
                JsonObject json = data.get("versions").getAsJsonArray().get(0).getAsJsonObject();
                String version = json.get("version").getAsString();
                this.onSuccessCheck(version);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            this.onFailCheck();
        }
    }

    public abstract void onFailCheck();

    public abstract void onSuccessCheck(String lastVersion);
}
