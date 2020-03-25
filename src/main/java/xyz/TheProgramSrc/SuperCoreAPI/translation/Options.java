/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.translation;

import xyz.TheProgramSrc.SuperCoreAPI.utils.Utils;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class Options {private String content;

    public Options(String content){
        this.content = content;
    }

    public Options lower(){
        this.content = this.content.toLowerCase();
        return this;
    }

    public Options upper(){
        this.content = this.content.toUpperCase();
        return this;
    }

    public Options capitalize(){
        this.content = Utils.capitalize(this.content);
        return this;
    }

    public Options vars(String... vars){
        return this.variables("$INDEX", vars);
    }

    public Options variables(String format, String... vars){
        this.content = Utils.vars(this.content, format, vars);
        return this;
    }

    public Options placeholders(HashMap<String, String> placeholders){
        AtomicReference<String> r = new AtomicReference<>(this.content);
        placeholders.forEach((k,v)-> r.set(r.get().replace(k,v)));
        return this;
    }

    public Options firstUpper(){
        this.content = Utils.firstUpperCase(this.content);
        return this;
    }

    public String get(){
        return this.content;
    }

    public String toString(){
        return this.get();
    }
    
}
