/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.items;

import xyz.TheProgramSrc.SuperCoreAPI.skintexture.SkinTexture;

public enum Skulls {

    BACK(null, "http://textures.minecraft.net/texture/9c042597eda9f061794fe11dacf78926d247f9eea8ddef39dfbe6022989b8395"),
    ARROW_LEFT("MHF_ArrowBack","http://textures.minecraft.net/texture/f7aacad193e2226971ed95302dba433438be4644fbab5ebf818054061667fbe2"),
    ARROW_RIGHT("MHF_ArrowRight", "http://textures.minecraft.net/texture/a5fb343b2e7822c7de47abac4c3679f6ad1fa12efc5866c033c7584d6848"),

    ;

    private String name, url;

    Skulls(String name, String url){
        this.name = name;
        this.url = url;
    }

    public String asName(){
        return this.name;
    }

    public String asUrl(){
        return this.url;
    }

    public SkinTexture asSkinTexture(){
        return SkinTexture.fromURL(this.asUrl());
    }
}
