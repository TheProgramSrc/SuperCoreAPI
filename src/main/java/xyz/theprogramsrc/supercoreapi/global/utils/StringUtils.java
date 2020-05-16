/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.global.utils;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A set of utils for strings
 */
public class StringUtils {
    private String content;

    /**
     * String utils constructor
     * @param content String to use
     */
    public StringUtils(String content){
        this.content = content;
    }

    /**
     * Make all the characters lower case
     * @return This class
     */
    public StringUtils lower(){
        this.content = this.content.toLowerCase();
        return this;
    }

    /**
     * Make all the characters upper case
     * @return This class
     */
    public StringUtils upper(){
        this.content = this.content.toUpperCase();
        return this;
    }

    /**
     * Capitalize the content with Camel Case
     * @return This class
     */
    public StringUtils capitalize(){
        this.content = Utils.capitalize(this.content);
        return this;
    }

    /**
     * Replace variables with format ($1,$2,$3...)
     * @param vars Placeholders replacement in order
     * @return This class
     */
    public StringUtils vars(String... vars){
        return this.variables("$INDEX", vars);
    }

    /**
     * Replace custom vars
     * @param format Format to replace (INDEX will be replaced with the index, See {@link #vars(String...)}) 
     * @param vars The value of every format
     * @return This class
     */
    public StringUtils variables(String format, String... vars){
        String r = this.content;
        for(int i = 0; i < vars.length; ++i){
            String var = vars[i];
            String replace = format.replace("INDEX", (i+1)+"");
            r = r.replace(replace, var);
        }
        this.content = r;
        return this;
    }

    /**
     * Inject placeholders to the content
     * @param placeholders Placeholders to inject
     * @return This class
     */
    public StringUtils placeholders(HashMap<String, String> placeholders){
        AtomicReference<String> r = new AtomicReference<>(this.content);
        placeholders.forEach((k,v)-> r.set(r.get().replace(k,v)));
        this.content = r.get();
        return this;
    }

    /**
     * Inject a single placeholder
     * @param key Placeholder to replace
     * @param value Placeholder replacement
     * @return This class
     */
    public StringUtils placeholder(String key, String value){
        this.content = this.content.replace(key, value);
        return this;
    }

    /**
     * Makes the first character uppercase
     * @return This class
     */
    public StringUtils firstUpper(){
        this.content = this.content.substring(0, 1).toUpperCase() + this.content.substring(1);
        return this;
    }

    /**
     * Gets the final string
     * @return the final string
     */
    public String get(){
        return this.content;
    }

    /**
     * Gets the final string
     * @return the final string
     */
    public String toString(){
        return this.get();
    }

}
