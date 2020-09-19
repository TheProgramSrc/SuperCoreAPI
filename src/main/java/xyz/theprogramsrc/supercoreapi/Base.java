package xyz.theprogramsrc.supercoreapi;


import xyz.theprogramsrc.supercoreapi.global.updater.SpigotUpdateChecker;

/**
 * This is a set of utils that need to be loaded
 */
public class Base {

    private final SuperPlugin<?> plugin;

    public Base(SuperPlugin<?> plugin){
        this.plugin = plugin;
        this.loadUpdateChecker();
    }

    private void loadUpdateChecker(){
        new SpigotUpdateChecker("77096"){
            @Override
            public void onCheckFail() {
                Base.this.plugin.log("&cError while checking &4SuperCoreAPI &cupdates.");
            }

            @Override
            public void onCheckSuccess(String lastVersion) {
                String currentVersion = "4.2.8";
                int latest = Integer.parseInt(lastVersion.split(" ")[0].replaceAll("\\.", ""));
                int current = Integer.parseInt(currentVersion.split(" ")[0].replaceAll("\\.", ""));
                if(latest > current){
                    Base.this.plugin.log("&cNew update available for &3SuperCoreAPI &7("+lastVersion+")");
                    Base.this.plugin.log("&cIf you're the developer of the plugin update the API, if you're a customer of a plugin using &3SuperCoreAPI&c please notify to the Developer.");
                }else if(latest < current){
                    Base.this.plugin.log("&cIt seems like you're running a non-release version of &3SuperCoreAPI");
                    Base.this.plugin.log("&cUnless you're the developer, you must use the version &7" + lastVersion);
                }
            }
        }.check();
    }
}
