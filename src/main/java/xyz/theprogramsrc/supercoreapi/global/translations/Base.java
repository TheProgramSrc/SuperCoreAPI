package xyz.theprogramsrc.supercoreapi.global.translations;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Base implements TranslationPack{
    DISPLAY("Display", "English"),
    OK("General.Ok","Ok"),
    ADD("General.Add","Add"),
    REMOVE("General.Remove","Remove"),
    DONE("General.Done","Done"),
    COMPLETED("General.Completed","Completed"),
    CANCEL("General.Cancel","Cancel"),
    LEFT_CLICK("General.LeftClick","Left Click"),
    SHIFT_CLICK("General.ShiftClick","Shift Click"),
    MIDDLE_CLICK("General.MiddleClick","Middle Click"),
    RIGHT_CLICK("General.RightClick","Right Click"),
    LANGUAGES("General.Languages","Languages"),
    SELECT("General.Select","Select"),
    SETTINGS("General.Settings","Settings"),
    ENABLED("General.Enabled","Enabled"),
    DISABLED("General.Disabled","Disabled"),
    PLAYER("General.Player","Player"),
    PLAYERS("General.Players","Players"),
    CONSOLE("General.Console","Console"),
    CONFIRM("General.Confirm","Confirm"),
    DECLINE("General.Decline","Decline"),
    GENERAL("General.General","General"),

    /* MESSAGES */
    NO_PERMISSION("Commands.NoPermission","&cYou dont have permission."),
    NO_ACCESS("Commands.NoAccess","&cYou dont have access!"),
    NOT_SUPPORTED("Commands.NotSupported","&cThis command it's only for &e{Supported}"),
    INVALID_ARGUMENTS("Commands.InvalidArguments","&cInvalid Arguments!"),

    DIALOG_HOW_TO_CLOSE("Dialog.HowToClose","&7Use &aLeft Click &7to close the dialog."),
    DIALOG_CLOSED("Dialog.Closed","&7You &cclosed &7this dialog."),

    /* DIALOGS */
    DIALOG_SEARCH_TITLE("Dialog.Search.Title","&9Search"),
    DIALOG_SEARCH_SUBTITLE("Dialog.Search.Subtitle","&7Write a search term"),
    DIALOG_SEARCH_ACTIONBAR("Dialog.Search.Actionbar", "&aWrite in the chat a search term to find a object"),

    DIALOG_CHANGE_PREFIX_TITLE("Dialog.ChangePrefix.Title", "&9Prefix"),
    DIALOG_CHANGE_PREFIX_SUBTITLE("Dialog.ChangePrefix.Subtitle","&7Write a new prefix"),
    DIALOG_CHANGE_PREFIX_ACTIONBAR("Dialog.ChangePrefix.Actionbar","&aCurrent prefix: &r{Prefix}"),

    /* GUIs */
    SETTINGS_GUI_TITLE("GUI.Settings.Title","&cSettings"),
    SETTING_PANE_GUI_TITLE("GUI.Settings.PaneTitle","&cSettings &8> &9{Setting}"),

    SETTINGS_EDITOR_NAME("GUI.Settings.Items.Editor.Name","&aSettings Editor"),
    SETTINGS_EDITOR_DESCRIPTION("GUI.Settings.Items.Editor.Lore","&7Click to edit some settings"),

    SETTINGS_GENERAL_NAME("GUI.Settings.Items.General.Name", "&aGeneral Settings"),
    SETTINGS_GENERAL_DESCRIPTION("GUI.Settings.Items.General.Lore","&7Click to edit general settings"),

    LANGUAGE_SELECTOR_NAME("GUI.Settings.Items.Languages.Name", "&aLanguages"),
    LANGUAGE_SELECTOR_DESCRIPTION("GUI.Settings.Items.Languages.Lore","&7Click to select a new Language."),

    GENERAL_SET_PREFIX_NAME("GUI.GeneralSettings.Items.Prefix.Name","&aSet Prefix"),
    GENERAL_SET_PREFIX_DESCRIPTION("GUI.GeneralSettings.Items.Prefix.Lore","&7Current Prefix: &r{Prefix}"),

    GENERAL_TOGGLE_TRANSLATION_DOWNLOADER_NAME("GUI.GeneralSettings.Items.TranslationDownloader.Name","&aToggle Translation Downloader"),
    GENERAL_TOGGLE_TRANSLATION_DOWNLOADER_DESCRIPTION("GUI.GeneralSettings.Items.TranslationDownloader.Lore","&7Current Status: &9{Status}"),

    SETTINGS_TOGGLE_UPDATER_NAME("GUI.GeneralSettings.Items.ToggleUpdater.Name","&aToggle Updater"),
    SETTINGS_TOGGLE_UPDATER_DESCRIPTION("GUI.GeneralSettings.Items.ToggleUpdater.Lore","&7Current Status: &9{Status}"),

    SETTINGS_TOGGLE_SQL_NAME("GUI.GeneralSettings.Items.ToggleSQL.Name","&aToggle SQL"),
    SETTINGS_TOGGLE_SQL_DESCRIPTION("GUI.GeneralSettings.Items.ToggleSQL.Lore","&7Current Status: &9{Status}"),

    LANGUAGE_SELECT_DESCRIPTION("GUI.Languages.Items.Name","&7Click to select&a {Language}&7 as the current language."),
    LANGUAGE_SELECTED_DESCRIPTION("GUI.Languages.Items.Lore","&a{Language}&7 is selected as the current language."),

    MATERIAL_SELECTOR_TITLE("GUI.MaterialSelector.Title","&7Select a Material"),
    MATERIAL_SELECTOR_ITEM_NAME("GUI.MaterialSelector.Item.Name","&a{Material}"),
    MATERIAL_SELECTOR_ITEM_DESCRIPTION("GUI.MaterialSelector.Item.Lore","&7Click to select &a{Material}"),

    /* Items */
    ITEM_BACK_NAME("Items.Back.Name","&aBack"),
    ITEM_BACK_DESCRIPTION("Items.Back.Lore","&7Click to go Back."),

    ITEM_NEXT_NAME("Items.Next.Name","&aNext"),
    ITEM_NEXT_DESCRIPTION("Items.Next.Lore","&7Click to go to the next page."),

    ITEM_PREVIOUS_NAME("Items.Previous.Name","&aPrevious"),
    ITEM_PREVIOUS_DESCRIPTION("Items.Previous.Lore","&7Click to go to the previous page."),

    ITEM_SEARCH_NAME("Items.Search.Name","&aSearch"),
    ITEM_SEARCH_DESCRIPTION("Items.Search.Name","&7Click to search between objects."),

    ITEM_END_SEARCH_NAME("Items.EndSearch.Name","&aEnd Search"),
    ITEM_END_SEARCH_DESCRIPTION("Items.EndSearch.Name","&7Click to end with the search"),
    ;

    private final String path;
    private final String content;
    private TranslationManager manager;

    Base(String path, String content){
        this.content = content;
        this.path = path;
    }

    @Override
    public String getLanguage() {
        return "en";
    }

    @Override
    public Translation get() {
        return new Translation(this, this.path, this.content);
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
