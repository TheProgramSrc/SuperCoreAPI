package xyz.theprogramsrc.supercoreapi;


import xyz.theprogramsrc.supercoreapi.global.updater.SongodaUpdateChecker;

/**
 * This is a set of utils that need to be loaded before everything
 */
public class Base {

    private final SuperPlugin<?> plugin;

    public Base(SuperPlugin<?> plugin){
        this.plugin = plugin;
        this.updateChecker();
    }

    public void updateChecker(){
        new SongodaUpdateChecker("supercoreapi-the-best-way-to-create-a-plugin"){
            @Override
            public void onFailCheck() {
                Base.this.plugin.log("&cError while checking &4SuperCoreAPI &cupdates.");
            }

            @Override
            public void onSuccessCheck(String lastVersion) {
                StringBuilder latest = new StringBuilder(lastVersion.split(" ")[0].replaceAll("\\.", "").replaceAll("[a-zA-Z]", "")),
                current = new StringBuilder(Base.this.plugin.SUPER_CORE_API_VERSION.split(" ")[0].replaceAll("\\.", "").replaceAll("[a-zA-Z]", ""));
                if(latest.length() > current.length()){
                    int amount = latest.length() - current.length();
                    for(int i = 0; i < amount; ++i){
                        current.append("0");
                    }
                }else if(current.length() > latest.length()){
                    int amount = current.length() - latest.length();
                    for(int i = 0; i < amount; ++i){
                        latest.append("0");
                    }
                }
                int l = Integer.parseInt(latest.toString()), c = Integer.parseInt(current.toString());
                if(l > c){
                    Base.this.plugin.log("&cNew update available for &3SuperCoreAPI &7("+lastVersion+")");
                    Base.this.plugin.log("&cIf you're the developer of the plugin update the API, if you're a customer of a plugin using &3SuperCoreAPI&c please notify to the Developer.");
                }else if(l < c){
                    Base.this.plugin.log("&cIt seems like you're running a non-release version of &3SuperCoreAPI");
                    Base.this.plugin.log("&cUnless you're the developer, you must use the version &7" + lastVersion);
                }
            }
        }.checkUpdates();
    }
}
