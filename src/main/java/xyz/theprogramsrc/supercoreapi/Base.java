package xyz.theprogramsrc.supercoreapi;


import xyz.theprogramsrc.supercoreapi.global.updater.SongodaUpdateChecker;

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
        new SongodaUpdateChecker("supercoreapi-the-best-way-to-create-a-plugin"){
            @Override
            public void onFailCheck() {
                Base.this.plugin.log("&cError while checking &4SuperCoreAPI &cupdates.");
            }

            @Override
            public void onSuccessCheck(String lastVersion) {
                int latest = Integer.parseInt(lastVersion.split(" ")[0].replaceAll("\\.", "").replaceAll("[a-zA-Z]", ""));
                int current = Integer.parseInt(Base.this.plugin.getPluginVersion().split(" ")[0].replaceAll("\\.", "").replaceAll("[a-zA-Z]", ""));
                if(latest > current){
                    Base.this.plugin.log("&cNew update available for &3SuperCoreAPI &7("+lastVersion+")");
                    Base.this.plugin.log("&cIf you're the developer of the plugin update the API, if you're a customer of a plugin using &3SuperCoreAPI&c please notify to the Developer.");
                }else if(latest < current){
                    Base.this.plugin.log("&cIt seems like you're running a non-release version of &3SuperCoreAPI");
                    Base.this.plugin.log("&cUnless you're the developer, you must use the version &7" + lastVersion);
                }
            }
        }.checkUpdates();
    }
}
