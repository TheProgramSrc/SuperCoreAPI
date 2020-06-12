package xyz.theprogramsrc.supercoreapi.global.translations;

import xyz.theprogramsrc.supercoreapi.global.utils.StringUtils;

/**
 * A representation of a translation
 */
public class Translation {

    private final TranslationPack pack;
    private final String id, value;

    public Translation(TranslationPack pack, String id, String value) {
        this.pack = pack;
        this.id = id;
        this.value = value;
    }

    public TranslationPack getPack() {
        return pack;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public String translate(){
        return this.pack.getManager().translate(this.id);
    }

    public StringUtils options(){
        return new StringUtils(this.translate());
    }
}
