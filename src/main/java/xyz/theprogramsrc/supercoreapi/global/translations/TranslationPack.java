package xyz.theprogramsrc.supercoreapi.global.translations;

import xyz.theprogramsrc.supercoreapi.global.utils.StringUtils;

import java.util.List;

/**
 * This util is designed for the Enum class
 */
public interface TranslationPack {

    /**
     * Gets the language of the file
     * @return Language locale of the file
     */
    String getLanguage();

    /**
     * Gets a single translation
     * @return Current Translation
     */
    Translation get();

    /**
     * Gets all the available translations
     * @return Available translations
     */
    List<Translation> translations();

    /**
     * Sets the translation manager
     * @param manager Translation Manager
     */
    void setManager(TranslationManager manager);

    /**
     * Gets the translation manager
     * @return Translation manager
     */
    TranslationManager getManager();

    /**
     * String options for the current translation
     * @return String utils
     */
    default StringUtils options(){
        return this.get().options();
    }
}
