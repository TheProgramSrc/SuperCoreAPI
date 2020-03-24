/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.translation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TranslationTemplate {

    private String lang, name;
    private HashMap<String, HashMap<String, String>> phrases;

    public TranslationTemplate(String lang, String name) {
        this.lang = lang;
        this.name = name;
        this.phrases = new HashMap<>();
    }

    public List<String> getLanguages(){
        return new ArrayList<>(this.phrases.keySet());
    }

    public void addPhrases(String lang, HashMap<String, String> phrases){
        this.phrases.put(lang, phrases);
    }

    public void clearPhrases(){
        this.phrases.clear();
    }

    public String getPhrase(String lang, String id){
        if(this.phrases.containsKey(lang)){
            HashMap<String, String> p = this.phrases.get(lang);
            return p.get(id);
        }

        if(!lang.equals(this.lang)){
            return this.getPhrase(this.lang, id);
        }else{
            try{
                throw new NullPointerException("Cannot find phrase '" + id + "' on language '" + lang + "'.");
            }catch (Exception ex){
                ex.printStackTrace();
                return null;
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getLang() {
        return lang;
    }
}
