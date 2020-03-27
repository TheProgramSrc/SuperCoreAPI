/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.translation;

import java.util.List;
import java.util.Locale;

public interface TranslationPack {

    Locale getLanguage();

    Translation get();

    List<Translation> getTranslations();

    void setManager(TranslationManager manager);

    TranslationManager getManager();

    String translate();

    default Options options(){
        return this.get().options();
    }


}
