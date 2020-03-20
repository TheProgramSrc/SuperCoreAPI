/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.translation;

import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.SuperModule;
import xyz.TheProgramSrc.SuperCoreAPI.config.YAMLConfig;
import xyz.TheProgramSrc.SuperCoreAPI.utils.InstanceCreator;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class TranslationManager extends SuperModule {

    private HashMap<String, HashMap<String, String>> translations;

    public TranslationManager(SuperCore core) {
        super(core);
    }

    @Override
    public void onModuleLoad() {
        this.translations = new HashMap<>();
        this.register(Base.class);
    }

    public String translate(String identifier){
        return this.translations.get(this.getLanguage()).get(identifier);
    }

    public String translate(String identifier, String def){
        return this.translations.get(this.getLanguage()).getOrDefault(identifier, def);
    }

    public void register(Class<? extends TranslationPack> clazz){
        try{
            TranslationPack pack = ((TranslationPack) InstanceCreator.create(clazz));
            if(pack != null){
                List<Translation> translations = pack.getTranslations();
                translations.forEach(t-> t.getPack().setManager(this));
                String lang = pack.getLanguage();
                File file = new File(this.getTranslationsFolder(), lang + ".lang");
                if(file.exists()) file.delete();
                YAMLConfig config = new YAMLConfig(file);
                translations.forEach(t-> config.set(t.getId(), t.getContent()));
                this.loadTranslations();
            }
        }catch (Exception ex){
            this.debug(ex);
        }
    }

    private void loadTranslations() {
        File[] files = this.getTranslationsFolder().listFiles();
        if(files != null){
            for(File file : files){
                String lang = file.getName().replace(".lang", "");
                YAMLConfig config = new YAMLConfig(file);
                if(!this.translations.containsKey(lang)){
                    this.translations.put(lang, new HashMap<>());
                }

                HashMap<String, String> map = this.translations.get(lang);
                config.getKeys(false).forEach(s-> map.put(s, config.getString(s)));
                this.translations.put(lang, map);
            }
        }
    }

    public Set<String> getLanguages(){
        return this.translations.keySet();
    }

    public int getTranslationsAmount(String language){
        return this.translations.get(language).keySet().size();
    }
}
