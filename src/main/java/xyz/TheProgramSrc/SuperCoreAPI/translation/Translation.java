/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.translation;

public class Translation {

    private TranslationPack pack;
    private String id, content;

    public Translation(TranslationPack pack, String id, String content) {
        this.pack = pack;
        this.id = id;
        this.content = content;
    }

    public TranslationPack getPack() {
        return pack;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Options options(){
        return new Options(this.translate());
    }

    public String translate(){
        return this.getPack().getManager().translate(this.getPack().getName()+"", this.getId()+"");
    }

    @Override
    public String toString() {
        return this.translate();
    }
}
