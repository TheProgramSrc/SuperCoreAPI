/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.utils.songoda;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.SuperModule;
import xyz.TheProgramSrc.SuperCoreAPI.utils.Utils;

@SuppressWarnings("unused")
public class SongodaUpdateChecker extends SuperModule {

    private String product, lastVersion;

    public SongodaUpdateChecker(SuperCore core, String product) {
        super(core, false);
    }

    public String getLastVersion() throws NullPointerException{
        if(this.lastVersion != null){
            return this.lastVersion;
        }else{
            throw new NullPointerException("Last version not found!");
        }
    }

    public UpdateResult checkUpdates(){
        if(!Utils.isConnected()){
            return UpdateResult.NO_CONNECTION;
        }else{
            try{
                JsonObject json = new JsonParser().parse(Utils.readWithIO("https://songoda.com/api/products/" + this.product)).getAsJsonObject();
                JsonArray jars = json.get("jars").getAsJsonArray();
                JsonObject jar = jars.get(0).getAsJsonObject();
                this.lastVersion = jar.get("version").getAsString();
                return UpdateResult.SUCCESS;
            }catch (Exception ex){
                UpdateResult fail = UpdateResult.FAILED;
                fail.setException(ex);
                return fail;
            }
        }
    }
}
