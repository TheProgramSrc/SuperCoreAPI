package xyz.theprogramsrc.supercoreapi.global.translations;

import sun.misc.Unsafe;
import xyz.theprogramsrc.supercoreapi.SuperPlugin;
import xyz.theprogramsrc.supercoreapi.global.files.yml.YMLConfig;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class TranslationManager {

    protected SuperPlugin<?> plugin;
    private final File langFolder;
    private final LinkedHashMap<String, LinkedHashMap<String, String>> CACHE;

    public TranslationManager(SuperPlugin<?> plugin) {
        this.plugin = plugin;
        this.langFolder = Utils.folder(new File(this.plugin.getPluginFolder(), "lang/"));
        CACHE = new LinkedHashMap<>();
        this.loadTranslations();
    }

    /**
     * Register a new default translation
     * (The generated file will be replaced after every start)
     * @param clazz {@link TranslationPack} class to register
     */
    public void registerTranslation(Class<? extends TranslationPack> clazz){
        this.plugin.debug("Registering translation '" + clazz.getName() + "'");
        try{
            // Here we initialize the class
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe unsafe = ((Unsafe)theUnsafe.get(null));
            TranslationPack pack = ((TranslationPack)unsafe.allocateInstance(clazz));

            // Now we setup the manager for every translation
            List<Translation> defaultTranslations = pack.translations();
            defaultTranslations.forEach(t-> t.getPack().setManager(this));

            // Now we load the save translations without replacing existing ones (to allow customization)
            YMLConfig lang = new YMLConfig(this.langFolder, pack.getLanguage() + ".yml");
            for(Translation translation : defaultTranslations){
                translation.getPack().setManager(this);
                lang.add(translation.getPath(), translation.getValue());
            }
            this.loadTranslations();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Clear the caches and load all the available translations
     */
    public void loadTranslations(){
        this.plugin.debug("Reloading translations");
        this.CACHE.clear(); // We clear the cache
        File[] files = this.langFolder.listFiles();
        if(files != null && files.length != 0){
            for(File file : files){ // Now we list all the files and filter by only the .yml files
                if(file.getName().endsWith(".yml")){
                    String lang = file.getName().replace(".yml", ""); // Now we parse the language
                    YMLConfig cfg = new YMLConfig(file); // Generate the Config
                    LinkedHashMap<String, String> translations = new LinkedHashMap<>(); // Generate the translations
                    Set<String> keys = cfg.getKeys(true); // Retrieve the IDs
                    for(String key : keys){
                        translations.put(key, cfg.getString(key, "")); // Retrieve and save the key and value
                    }
                    this.CACHE.put(lang, translations); // Save to cache
                }
            }
        }
    }

    /**
     * See {@link #translate(String, String)}
     * @param id Identifier of the translation
     * @return Translated identifier
     */
    public String translate(String id) {
        return this.translate(id, "");
    }

    /**
     * Translates a string into current language
     * @param id Identifier of the translation
     * @param def Default value in case that the id doesnt exists
     * @return Translated identifier
     */
    public String translate(String id, String def){
        String currentLang = this.plugin.getLanguage();
        if(!this.CACHE.containsKey(currentLang)){
            currentLang = "en";
        }

        LinkedHashMap<String, String> translations = this.CACHE.get(currentLang);
        return translations.getOrDefault(id, def);
    }

    /**
     * Gets the available translations
     * @return the available translations
     */
    public String[] getTranslations() {
        return this.CACHE.keySet().toArray(new String[0]);
    }
}
