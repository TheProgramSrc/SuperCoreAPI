package xyz.theprogramsrc.supercoreapi.spigot.update;

import xyz.theprogramsrc.supercoreapi.global.utils.Utils;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;

import java.io.File;

public class SpigotUpdater extends SpigotModule {

    /**
     * Downloads the new file from internet
     * 
     * To apply the changes you should restart the server
     * @param url Direct url to the file
     * @throws Exception if there is an exception (See {@link Utils#downloadFile(String, File)})
     */
    public void downloadUpdate(String url) throws Exception{
        File pluginsFolder = Utils.folder(new File(this.getServerFolder(), "plugins/"));
        File updateFolder = Utils.folder(new File(pluginsFolder, "update/"));
        File destination = new File(updateFolder, this.plugin.getPluginFile().getName());
        Utils.downloadFile(url, destination);
    }

    /**
     * This restarts the server
     * (Dont use automatically, use with user agreement)
     * @param script If is true the API will stop the server and start the bash script specified in the server configurations
     *               otherwise the API will execute the command "restart" through console
     */
    public void restartServer(boolean script){
        if(script){
            this.spigotPlugin.getServer().spigot().restart();
        }else{
            this.spigotPlugin.getServer().dispatchCommand((this.spigotPlugin).getServer().getConsoleSender(), "restart");
        }
    }

    /**
     * See {@link #restartServer(boolean)}
     * (By default is used the value <code>script</code> as false)
     */
    public void restartServer(){
        this.restartServer(false);
    }

}
