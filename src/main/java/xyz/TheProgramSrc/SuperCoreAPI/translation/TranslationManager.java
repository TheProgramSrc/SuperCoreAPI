/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.translation;

import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.SuperModule;
import xyz.TheProgramSrc.SuperCoreAPI.config.YAMLConfig;
import xyz.TheProgramSrc.SuperCoreAPI.utils.InstanceCreator;
import xyz.TheProgramSrc.SuperCoreAPI.utils.Utils;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class TranslationManager extends SuperModule {

    private LinkedHashMap<Locale, LinkedHashMap<String, String>> phrases;

    public TranslationManager(SuperCore core){
        super(core);
    }

    public String translate(String lang, String id){
        Locale locale = new Locale(lang.split("_")[0], lang.split("_")[1]);
        if(!this.phrases.containsKey(locale)){
            if(!lang.equals("en_US")){
                return this.translate("en_US", id);
            }else{
                throw new NullPointerException("Cannot find translate '"+id+"' from '" + locale.toString() + "'");
            }
        }else{
            if(!this.phrases.get(locale).containsKey(id)){
                if(!this.getLanguage().equals("en_US")){
                    return this.translate("en_US", id);
                }else{
                    throw new NullPointerException("Cannot find Translation '" + id + "' in '" + lang + "'");
                }
            }else{
                return this.phrases.get(locale).get(id);
            }
        }
    }

    public String translate(String id){
        return this.translate(this.getLanguage(), id);
    }

    @Override
    public void onModuleLoad() {
        super.onModuleLoad();
        this.phrases = new LinkedHashMap<>();
        this.register(Base.class);
    }

    public void register(Class<? extends TranslationPack> tpack){
        try{
            TranslationPack pack = ((TranslationPack) InstanceCreator.create(tpack));
            if(pack != null){
                pack.getTranslations().forEach(t-> t.getPack().setManager(this));
                File file = new File(this.getTranslationsFolder(), pack.getLanguage().toString()+".lang");
                LinkedHashMap<String, String> map = new LinkedHashMap<>();
                if(file.exists()){
                    YAMLConfig cfg = new YAMLConfig(file);
                    cfg.getKeys(false).forEach(s-> map.put(s, cfg.getString(s)));
                    file.delete();
                }
                file.createNewFile();
                pack.getTranslations().forEach(t-> map.put(t.getId(), t.getContent()));
                YAMLConfig cfg = new YAMLConfig(file);
                map.forEach(cfg::set);
                this.loadTranslations();
            }
        }catch (Exception ex){
            this.debug(ex);
        }
    }

    public void loadTranslations(){
        try{
            File[] files = this.getTranslationsFolder().listFiles();
            if(files != null && files.length != 0){
                for (File file : files) {
                    if(file.isDirectory()) continue;
                    if(file.getName().endsWith(".lang") && file.getName().contains("_")){
                        String name = file.getName().replace(".lang","");
                        Locale locale = new Locale(name.split("_")[0], name.split("_")[1]);
                        LinkedHashMap<String, String> map = new LinkedHashMap<>();
                        this.phrases.remove(locale);
                        YAMLConfig cfg = new YAMLConfig(file);
                        cfg.getKeys(false).forEach(s-> map.put(s, cfg.getString(s)));
                        this.phrases.put(locale, map);
                    }
                }
            }
            Locale.setDefault(new Locale(this.getLanguage().split("_")[0], this.getLanguage().split("_")[1]));
        }catch (Exception ex){
            this.debug(ex);
        }
    }

    public List<String> getAvailableLanguages(){
        return this.phrases.keySet().stream().map(l-> l.getDisplayLanguage(Utils.toLocale(this.getLanguage()))).collect(Collectors.toList());
    }
}
