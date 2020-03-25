/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.translation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Base implements TranslationPack{

    /* WORDS */
    CONFIRM("Confirm"),
    DECLINE("Decline"),
    CLICK("Click"),
    LEFT("Left"),
    MIDDLE("Middle"),
    RIGHT("Right"),
    LANGUAGES("Languages"),
    SELECT("Select"),
    SETTINGS("Settings"),
    ENABLED("Enabled"),
    DISABLED("Disabled"),
    PLAYER("Player"),
    PLAYERS("Players"),
    CONSOLE("Console"),

    /* PHRASES */
    CLICK_TO("Click to"),

    /* MESSAGES */
    NO_PERMISSION("&cYou dont have permission."),
    NO_ACCESS("&cYou dont have access!"),
    NOT_SUPPORTED("&cThis command it's only for &e{Supported}"),
    INVALID_ARGUMENTS("&cInvalid Arguments!"),

    DIALOG_HOW_TO_CLOSE("&7To close the dialog write &c{CloseWord} &7in the chat."),
    DIALOG_CLOSED("&7You &cclosed &7this dialog."),

    TRANSLATION_DOWNLOAD_SUCCESS("&aSuccessfully downloaded Translations from the Cloud"),
    TRANSLATION_DOWNLOAD_FAILED("&cCannot fetch translations from the cloud."),

    /* DIALOGS */
    DIALOG_SEARCH_TITLE("&9Search"),
    DIALOG_SEARCH_SUBTITLE("&7Write a search term"),
    DIALOG_SEARCH_ACTIONBAR("&aWrite in the chat a search term to find a object"),

    DIALOG_CHANGE_VALUE_TITLE("&9New Value"),
    DIALOG_CHANGE_VALUE_SUBTITLE("&7Write the new Value"),
    DIALOG_CHANGE_VALUE_ACTIONBAR("&aCurrent Value: &r{CurrentValue}"),


    /* GUIs */

    SETTINGS_EDITOR_NAME("&aSettings Editor"),
    SETTINGS_EDITOR_DESCRIPTION("&7Click to edit some settings"),
    LANGUAGE_SELECTOR_DESCRIPTION("&7Click to select a new Language."),
    LANGUAGE_SELECT_DESCRIPTION("&7Click to select &athis &7language."),
    LANGUAGE_SELECTED_DESCRIPTION("&aThis &7language is selected."),

    SETTINGS_TOGGLE_UPDATER_NAME("&aToggle Updater"),
    SETTINGS_TOGGLE_UPDATER_DESCRIPTION("&7Current Status: &9$1"),


    SET_CLOSE_WORD_NAME("&aSet Close Word"),
    SET_CLOSE_WORD_DESCRIPTION("&7Current Word: &9$1"),

    MATERIAL_SELECTOR_TITLE("&7Select a Material"),
    MATERIAL_SELECTOR_ITEM_NAME("&a{Material}"),
    MATERIAL_SELECTOR_ITEM_DESCRIPTION("&7Click to select &a{Material}"),

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

    private String content;
    private TranslationManager translationManager;

    Base(String content){
        this.content = content;
    }

    @Override
    public String getLanguage() {
        return "English";
    }

    @Override
    public String getName() {
        return "Base";
    }

    @Override
    public Translation get() {
        return new Translation(this, name(), this.content);
    }

    @Override
    public List<Translation> getTranslations() {
        return Arrays.stream(values()).map(TranslationPack::get).collect(Collectors.toList());
    }

    @Override
    public void setManager(TranslationManager manager) {
        this.translationManager = manager;
    }

    @Override
    public TranslationManager getManager() {
        return this.translationManager;
    }

    @Override
    public String translate() {
        return this.get().translate();
    }

    @Override
    public String toString() {
        return this.translate();
    }
}
