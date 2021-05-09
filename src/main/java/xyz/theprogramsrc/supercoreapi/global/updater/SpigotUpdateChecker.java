package xyz.theprogramsrc.supercoreapi.global.updater;

import xyz.theprogramsrc.supercoreapi.global.utils.Utils;

public abstract class SpigotUpdateChecker implements IUpdateChecker{

    private final String apiEndpoint;

    public SpigotUpdateChecker(String pluginId){
        this.apiEndpoint = "https://api.spigotmc.org/legacy/update.php?resource=" + pluginId;
    }

    public void check(){
        try{
            String content = Utils.readWithInputStream(this.apiEndpoint);
            if(content == null){
                this.onFailCheck();
            }else{
                this.onSuccessCheck(content);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            this.onFailCheck();
        }
    }

}
