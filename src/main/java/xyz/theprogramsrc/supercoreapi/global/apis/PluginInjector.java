/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.global.apis;

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
