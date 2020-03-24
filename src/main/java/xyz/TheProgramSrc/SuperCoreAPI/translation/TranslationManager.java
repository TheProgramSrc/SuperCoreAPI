/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.translation;

import org.apache.commons.io.FileUtils;
import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.SuperModule;
import xyz.TheProgramSrc.SuperCoreAPI.config.YAMLConfig;
import xyz.TheProgramSrc.SuperCoreAPI.utils.InstanceCreator;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TranslationManager extends SuperModule {

    private HashMap<String, TranslationTemplate> translations;

    public TranslationManager(SuperCore core) {
        super(core);
    }

    @Override
    public void onModuleLoad() {
        this.translations = new HashMap<>();
        this.register(Base.class);
    }

    public String translate(String name, String identifier){
        return this.translations.get(name).getPhrase(this.getLanguage(), identifier);
    }

    public void register(Class<? extends TranslationPack> clazz){
        try{
            TranslationPack pack = ((TranslationPack) InstanceCreator.create(clazz));
            if(pack != null){
                List<Translation> translations = pack.getTranslations();
                translations.forEach(t-> t.getPack().setManager(this));
                String lang = pack.getLanguage();
                String name = pack.getName();
                File file = new File(this.getTranslationsFolder(), name + "_" + lang + ".lang");
                if(file.exists()){
                    file.delete();
                }
                YAMLConfig cfg = new YAMLConfig(file);
                translations.forEach(t-> cfg.set(t.getId(), t.getContent()));
                this.loadTranslations();
            }
        }catch (Exception ex){
            this.debug(ex);
        }
    }

    private void loadTranslations() {
        try{
            File[] files = this.getTranslationsFolder().listFiles();
            if(files != null){
                for(File file : files){
                    if(file.getName().contains(".lang") && file.getName().contains("_")){
                        String fullName = file.getName().replace(".lang", "");
                        String name = fullName.split("_")[0];
                        String lang = fullName.split("_")[1];
                        if(!this.translations.containsKey(name)){
                            this.translations.put(name, new TranslationTemplate(lang+"", name+""));
                        }
                        TranslationTemplate template = this.translations.get(name);
                        HashMap<String, String> phrases = new HashMap<>();
                        FileUtils.readLines(file, Charset.defaultCharset()).stream().filter(s-> !s.startsWith("#") && s.contains(":")).forEach(s->{
                            phrases.put(s.split(": ")[0], s.split(": ")[1]);
                        });
                        template.addPhrases(lang, phrases);
                        this.translations.put(name, template);
                    }

                }
            }
        }catch (Exception ex){
            this.debug(ex);
        }
    }

    public List<String> getTemplatesNames(){
        return new ArrayList<>(this.translations.keySet());
    }

    public List<String> getAvailableLanguages(){
        return this.translations.values().stream().flatMap((t) -> t.getLanguages().stream()).distinct().sorted().collect(Collectors.toList());
    }
}
