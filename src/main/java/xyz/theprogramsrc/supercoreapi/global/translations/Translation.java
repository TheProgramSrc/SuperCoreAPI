package xyz.theprogramsrc.supercoreapi.global.translations;

import xyz.theprogramsrc.supercoreapi.global.utils.StringUtils;

/**
 * A representation of a translation
 */
public class Translation {

    private final TranslationPack pack;
    private final String path, value;

    public Translation(TranslationPack pack, String path, String value) {
        this.pack = pack;
        this.path = path;
        this.value = value;
    }

    public TranslationPack getPack() {
        return pack;
    }

    public String getPath() {
        return path;
    }

    public String getValue() {
        return value;
    }

    public String translate(){
        return this.pack.getManager().translate(this.path);
    }

    public StringUtils options(){
        return new StringUtils(this.translate());
    }
}