package xyz.theprogramsrc.supercoreapi.global.translations;

import sun.misc.Unsafe;
import xyz.theprogramsrc.supercoreapi.SuperPlugin;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

public class TranslationManager {

    private HashMap<Locale, HashMap<String, String>> translations;
    protected SuperPlugin<?> plugin;

    public TranslationManager(SuperPlugin<?> plugin) {
        this.plugin = plugin;
        this.translations = new HashMap<>();
        this.reloadTranslations();
    }

    /**
     * Register a new default translation
     * (The generated file will be replaced after every start)
     * @param clazz {@link TranslationPack} class to register
     */
    public void registerTranslation(Class<? extends TranslationPack> clazz){
        try{
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe unsafe = ((Unsafe)theUnsafe.get(null));
            TranslationPack pack = ((TranslationPack)unsafe.allocateInstance(clazz));
            List<Translation> translations = pack.translations();
            translations.forEach(t-> t.getPack().setManager(this));
            File file = new File(this.plugin.getTranslationsFolder(), pack.getLanguage().toString() + ".lang");
            final LinkedHashMap<String, String> translationsMap = new LinkedHashMap<>();
            if(file.exists()){
                Utils.readLines(file).stream().filter(l-> !l.startsWith("#") && l.contains("=")).forEach(l->{
                    String[] data = l.split("=",2);
                    String key = data[0];
                    String value = data[1];
                    translationsMap.put(key,value);
                });
                file.delete();
            }
            translations.forEach(t-> translationsMap.put(t.getId(), t.getValue()));
            file.createNewFile();
            String[] comments = {"# Default translation " + pack.getLanguage().toString(),"# If you want to edit the translations copy this file and rename it", "# The file name format is 'language_COUNTRY.lang'","# Also you can search about Java Locales", "#", "# If you're translating this file, remember to not translate placeholders nor keys!"};
            String[] translationsArray = translationsMap.entrySet().stream().map(e-> e.getKey() + "=" + e.getValue()).toArray(String[]::new);
            List<String> lines = new ArrayList<>();
            lines.addAll(Utils.toList(comments));
            lines.addAll(Utils.toList(translationsArray));
            Utils.writeFile(file, lines);
            this.reloadTranslations();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Clear the caches and load all the available translations
     */
    public void reloadTranslations(){
        if(this.translations == null) this.translations = new HashMap<>();
        this.translations.clear();
        File[] translationsFiles = this.plugin.getTranslationsFolder().listFiles();
        if(translationsFiles != null){
            if(translationsFiles.length != 0){
                for(File file : translationsFiles){
                    if(file.getName().endsWith(".lang") && file.getName().contains("_")){
                        String name = file.getName().replace(".lang", "");
                        String lang = name.split("_")[0];
                        String country = name.split("_")[1];
                        Locale locale = new Locale(lang, country);
                        HashMap<String, String> translations = this.translations.getOrDefault(locale, new HashMap<>());
                        try{
                            List<String> lines = Utils.readLines(file);
                            lines.stream().filter(l-> !l.startsWith("#") && l.contains("=")).forEach(l->{
                                String[] data = l.split("=",2);
                                String key = data[0];
                                String value = data[1];
                                translations.put(key,value);
                            });
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                        this.translations.put(locale, translations);
                    }
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
        String currentLanguage = this.plugin.getLanguage();
        Locale locale = new Locale(currentLanguage.split("_")[0], currentLanguage.split("_")[1]);
        if(!this.translations.containsKey(locale)){
            if(!locale.equals(new Locale("en", "US"))){
                return  this.translations.get(new Locale("en","US")).getOrDefault(id, def);
            }else{
                return def;
            }
        }else{
            return this.translations.get(locale).getOrDefault(id, def);
        }
    }
}
