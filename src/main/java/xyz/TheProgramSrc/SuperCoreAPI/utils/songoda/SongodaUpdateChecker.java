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

    private String lastVersion, overrideID;
    private boolean useIO = true;

    public SongodaUpdateChecker(SuperCore core, String overrideID) {
        super(core, false);
    }

    public SongodaUpdateChecker setOverrideID(String overrideID) {
        this.overrideID = overrideID;
        return this;
    }

    public SongodaUpdateChecker setUseIO(boolean useIO) {
        this.useIO = useIO;
        return this;
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
                String url ="https://songoda.com/api/v2/products/id/{ID}";
                url = url.replace("{ID}", this.overrideID != null ? this.overrideID : "%%__RESOURCE__%%");
                String data;
                if(this.useIO){
                    data = Utils.readWithIO(url);
                }else{
                    data = Utils.readWithInputStream(url);
                }
                if(data == null) return UpdateResult.FAILED;
                JsonObject json = new JsonParser().parse(data).getAsJsonObject();
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
