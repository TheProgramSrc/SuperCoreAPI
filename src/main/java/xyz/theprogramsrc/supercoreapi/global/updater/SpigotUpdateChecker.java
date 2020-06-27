package xyz.theprogramsrc.supercoreapi.global.updater;

import xyz.theprogramsrc.supercoreapi.global.utils.Utils;

public abstract class SpigotUpdateChecker {

    private final String apiEndpoint;

    public SpigotUpdateChecker(String pluginId){
        this.apiEndpoint = "https://api.spigotmc.org/legacy/update.php?resource=" + pluginId;
    }

    public void check(){
        try{
            String content = Utils.readWithInputStream(this.apiEndpoint);
            if(content == null){
                this.onCheckFail();
            }else{
                this.onCheckSuccess(content);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            this.onCheckFail();
        }
    }

    public abstract void onCheckSuccess(String lastVersion);

    public abstract void onCheckFail();
}
