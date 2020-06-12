package xyz.theprogramsrc.supercoreapi.global.apis;

/**
 * The PluginInjector works with Songoda and Spigot Products,
 * this will help you to check information of a download, for example:
 * The Identifier of a user who downloaded this plugin, or the id of the resource,
 * or the nonce of the download.
 */
public class PluginInjector {

    private final String resource, nonce, userId;

    public PluginInjector() {
        this.resource = "%%__RESOURCE__%%";
        this.nonce = "%%__NONCE__%%";
        this.userId = "%%__USER__%%";
    }

    public String getResource() {
        return resource;
    }

    public String getNonce() {
        return nonce;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isCracked(){
        return this.resource.contains("%%__");
    }
}
