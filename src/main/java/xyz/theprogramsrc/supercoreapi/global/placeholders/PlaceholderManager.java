package xyz.theprogramsrc.supercoreapi.global.placeholders;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

public class PlaceholderManager {

    protected final LinkedList<Placeholder> placeholders;

    public PlaceholderManager(){
        this.placeholders = new LinkedList<>();
    }

    public PlaceholderManager registerPlaceholder(Placeholder placeholder){
        this.placeholders.add(placeholder);
        return this;
    }

    public PlaceholderManager registerPlaceholder(String placeholder, String replacement){
        this.registerPlaceholder(new Placeholder(placeholder, replacement));
        return this;
    }

    public PlaceholderManager registerPlaceholders(Map<String, String> placeholders){
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            this.registerPlaceholder(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public PlaceholderManager registerPlaceholders(Collection<Placeholder> placeholders){
        for (Placeholder placeholder : placeholders) {
            this.registerPlaceholder(placeholder);
        }
        return this;
    }

    public PlaceholderManager registerPlaceholders(Placeholder... placeholders){
        for (Placeholder placeholder : placeholders) {
            this.registerPlaceholder(placeholder);
        }
        return this;
    }

    public String applyPlaceholders(String string){
        return applyPlaceholders(this.placeholders, string);
    }

    public static String applyPlaceholders(Collection<Placeholder> placeholders, String string){
        String result = string;
        for (Placeholder placeholder : placeholders) {
            result = placeholder.apply(result);
        }

        return result;
    }
}
