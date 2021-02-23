package xyz.theprogramsrc.supercoreapi.global.translations;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public enum Base implements TranslationPack{
    OK("Ok"),
    ADD("Add"),
    REMOVE("Remove"),
    DONE("Done"),
    COMPLETED("Completed"),
    CANCEL("Cancel"),
    LEFT_CLICK("Left Click"),
    SHIFT_CLICK("Shift Click"),
    MIDDLE_CLICK("Middle Click"),
    RIGHT_CLICK("Right Click"),
    LANGUAGES("Languages"),
    SELECT("Select"),
    SETTINGS("Settings"),
    ENABLED("Enabled"),
    DISABLED("Disabled"),
    PLAYER("Player"),
    PLAYERS("Players"),
    CONSOLE("Console"),
    CONFIRM("Confirm"),
    DECLINE("Decline"),
    GENERAL("General"),

    /* MESSAGES */
    NO_PERMISSION("&cYou dont have permission."),
    NO_ACCESS("&cYou dont have access!"),
    NOT_SUPPORTED("&cThis command it's only for &e{Supported}"),
    INVALID_ARGUMENTS("&cInvalid Arguments!"),

    DIALOG_HOW_TO_CLOSE("&7Use &aLeft Click &7to close the dialog."),
    DIALOG_CLOSED("&7You &cclosed &7this dialog."),

    /* DIALOGS */
    DIALOG_SEARCH_TITLE("&9Search"),
    DIALOG_SEARCH_SUBTITLE("&7Write a search term"),
    DIALOG_SEARCH_ACTIONBAR("&aWrite in the chat a search term to find a object"),

    DIALOG_CHANGE_PREFIX_TITLE("&9Prefix"),
    DIALOG_CHANGE_PREFIX_SUBTITLE("&7Write a new prefix"),
    DIALOG_CHANGE_PREFIX_ACTIONBAR("&aCurrent prefix: &r{Prefix}"),

    /* GUIs */
    SETTINGS_GUI_TITLE("&cSettings"),
    SETTING_PANE_GUI_TITLE("&cSettings &8> &9{Setting}"),

    SETTINGS_EDITOR_NAME("&aSettings Editor"),
    SETTINGS_EDITOR_DESCRIPTION("&7Click to edit some settings"),

    SETTINGS_GENERAL_NAME("&aGeneral Settings"),
    SETTINGS_GENERAL_DESCRIPTION("&7Click to edit general settings"),

    LANGUAGE_SELECTOR_NAME("&aLanguages"),
    LANGUAGE_SELECTOR_DESCRIPTION("&7Click to select a new Language."),

    GENERAL_SET_PREFIX_NAME("&aSet Prefix"),
    GENERAL_SET_PREFIX_DESCRIPTION("&7Current Prefix: &r{Prefix}"),

    GENERAL_TOGGLE_TRANSLATION_DOWNLOADER_NAME("&aToggle Translation Downloader"),
    GENERAL_TOGGLE_TRANSLATION_DOWNLOADER_DESCRIPTION("&7Current Status: &9{Status}"),

    LANGUAGE_SELECT_DESCRIPTION("&7Click to select&a {Language}&7 as the current language."),
    LANGUAGE_SELECTED_DESCRIPTION("&a{Language}&7 is selected as the current language."),

    SETTINGS_TOGGLE_UPDATER_NAME("&aToggle Updater"),
    SETTINGS_TOGGLE_UPDATER_DESCRIPTION("&7Current Status: &9{Status}"),

    SETTINGS_TOGGLE_SQL_NAME("&aToggle SQL"),
    SETTINGS_TOGGLE_SQL_DESCRIPTION("&7Current Status: &9{Status}"),

    MATERIAL_SELECTOR_TITLE("&7Select a Material"),
    MATERIAL_SELECTOR_ITEM_NAME("&a{Material}"),
    MATERIAL_SELECTOR_ITEM_DESCRIPTION("&7Click to select &a{Material}"),

    /* Items */
    ITEM_BACK_NAME("&aBack"),
    ITEM_BACK_DESCRIPTION("&7Click to go Back."),

    ITEM_NEXT_NAME("&aNext"),
    ITEM_NEXT_DESCRIPTION("&7Click to go to the next page."),

    ITEM_PREVIOUS_NAME("&aPrevious"),
    ITEM_PREVIOUS_DESCRIPTION("&7Click to go to the previous page."),

    ITEM_SEARCH_NAME("&aSearch"),
    ITEM_SEARCH_DESCRIPTION("&7Click to search between objects."),

    ITEM_END_SEARCH_NAME("&aEnd Search"),
    ITEM_END_SEARCH_DESCRIPTION("&7Click to end with the search"),
    ;

    private final String content;
    private TranslationManager manager;

    Base(String content){
        this.content = content;
    }

    @Override
    public Locale getLanguage() {
        return new Locale("en","US");
    }

    @Override
    public Translation get() {
        return new Translation(this, this.name(), this.content);
    }

    @Override
    public List<Translation> translations() {
        return Arrays.stream(values()).map(Base::get).collect(Collectors.toList());
    }

    @Override
    public void setManager(TranslationManager manager) {
        this.manager = manager;
    }

    @Override
    public TranslationManager getManager() {
        return this.manager;
    }

    @Override
    public String toString() {
        return this.get().translate();
    }
}
