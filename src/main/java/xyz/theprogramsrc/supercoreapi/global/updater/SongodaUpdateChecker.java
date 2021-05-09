package xyz.theprogramsrc.supercoreapi.global.updater;

import com.google.gson.JsonObject;
import xyz.theprogramsrc.supercoreapi.global.networking.ConnectionBuilder;
import xyz.theprogramsrc.supercoreapi.global.networking.CustomConnection;

import java.io.IOException;

public abstract class SongodaUpdateChecker implements IUpdateChecker{

    private final String apiEndpoint;

    public SongodaUpdateChecker(String product){
        this.apiEndpoint = "https://songoda.com/api/v2/products/" + product;
    }

    public void checkUpdates(){
        try{
            CustomConnection connection = ConnectionBuilder.connect(this.apiEndpoint);
            if(connection.isValidResponse() && connection.isResponseNotNull()){
                JsonObject json = connection.getResponseJson();
                if(json != null && !json.isJsonNull()){
                    String version = json.get("data").getAsJsonObject().get("versions").getAsJsonArray().get(0).getAsJsonObject().get("version").getAsString();
                    this.onSuccessCheck(version);
                }else{
                    this.onFailCheck();
                }
            }else{
                this.onFailCheck();
            }
        }catch (IOException ex){
            ex.printStackTrace();
            this.onFailCheck();
        }
    }
}
