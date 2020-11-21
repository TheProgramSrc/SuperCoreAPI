package xyz.theprogramsrc.supercoreapi.global.placeholders;

public class Placeholder {

    private final String placeholder, replacement;

    public Placeholder(String placeholder, String replacement) {
        this.placeholder = placeholder;
        this.replacement = replacement;
    }

    public String getPlaceholder() {
        return this.placeholder;
    }

    public String getReplacement() {
        return this.replacement;
    }

    public String apply(String text){
        return text.replace(this.getPlaceholder(), this.getReplacement());
    }
}
