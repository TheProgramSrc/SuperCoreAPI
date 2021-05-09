package xyz.theprogramsrc.supercoreapi.global.updater;

import com.google.gson.JsonObject;
import xyz.theprogramsrc.supercoreapi.global.networking.ConnectionBuilder;
import xyz.theprogramsrc.supercoreapi.global.networking.CustomConnection;

import java.io.IOException;

public abstract class TheProgramSrcUpdateChecker implements IUpdateChecker{

    private final String apiEndpoint;

    public TheProgramSrcUpdateChecker(String productId){
        this.apiEndpoint = "https://theprogramsrc.xyz/api/v1/products/id/" + productId;
    }

    @Override
    public void checkUpdates() {
        try{
            CustomConnection connection = ConnectionBuilder.connect(this.apiEndpoint);
            if(connection.isValidResponse() && connection.isResponseNotNull()){
                JsonObject json = connection.getResponseJson();
                if(json != null && !json.isJsonNull()){
                    JsonObject versionJson = json.get("data").getAsJsonObject().get("versions").getAsJsonArray().get(0).getAsJsonObject();
                    String version = versionJson.get("version").getAsString();
                    this.onSuccessCheck(version);
                }else{
                    this.onFailCheck();
                }
            }else{
                this.onFailCheck();
            }
        }catch (IOException e){
            e.printStackTrace();
            this.onFailCheck();
        }
    }
}
