/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.bungee;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import xyz.theprogramsrc.supercoreapi.SuperPlugin;
import xyz.theprogramsrc.supercoreapi.SuperUtils;
import xyz.theprogramsrc.supercoreapi.bungee.storage.Settings;
import xyz.theprogramsrc.supercoreapi.bungee.utils.BungeeUtils;
import xyz.theprogramsrc.supercoreapi.global.translations.Base;
import xyz.theprogramsrc.supercoreapi.global.translations.TranslationManager;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BungeePlugin extends Plugin implements SuperPlugin<Plugin> {

    private boolean firstStart;
    private List<Runnable> disableHooks;
    private File translationsFolder;

    private Settings settings;
    private TranslationManager translationManager;

    @Override
    public void onLoad() {
        long start = System.currentTimeMillis();
        this.log("Loading plugin &3v"+this.getPluginVersion());
        this.disableHooks = new ArrayList<>();
        this.firstStart = !this.getDataFolder().exists();
        Utils.folder(this.getDataFolder());
        this.onPluginLoad();
        this.log("Loaded plugin in " + (System.currentTimeMillis() - start) + "ms");
    }

    @Override
    public void onEnable() {
        this.log("Enabling plugin &3v" + this.getPluginVersion());
        this.settings = new Settings(this);
        this.translationsFolder = Utils.folder(new File(this.getDataFolder(), "translations/"));
        this.translationManager = new TranslationManager(this);
        this.getTranslationManager().registerTranslation(Base.class);
        this.onPluginEnable();
        this.log("Enabled plugin");
        if(this.isFirstStart()){
            if(this.isPaid()){
                this.log("Thanks for buying this plugin from &3" + this.getPluginAuthor());
            }else{
                this.log("Thanks for downloading this plugin from &3" + this.getPluginAuthor());
            }
            this.log("If you need help you can contact the developer");
            this.log("Consider leaving a positive rating");
        }
    }

    @Override
    public void onDisable() {
        this.log("Disabling plugin &3v" + this.getPluginVersion());
        this.getDisableHooks().forEach(Runnable::run);
        this.onPluginDisable();
        this.log("Disabled plugin");
    }

    @Override
    public void sendConsoleMessage(String message) {
        this.getProxy().getConsole().sendMessage(new TextComponent(this.getSuperUtils().color(message)));
    }

    @Override
    public SuperUtils getSuperUtils() {
        return new BungeeUtils();
    }

    @Override
    public String getPluginName() {
        return this.getDescription().getName();
    }

    @Override
    public String getPluginVersion() {
        return this.getDescription().getVersion();
    }

    @Override
    public String getPluginAuthor() {
        return this.getDescription().getAuthor();
    }

    @Override
    public boolean isFirstStart() {
        return this.firstStart;
    }

    @Override
    public File getServerFolder() {
        return Utils.folder(new File("."));
    }

    @Override
    public File getPluginFolder() {
        return Utils.folder(this.getDataFolder());
    }

    @Override
    public File getTranslationsFolder() {
        return this.translationsFolder;
    }

    @Override
    public File getPluginFile() {
        return this.getFile();
    }

    @Override
    public TranslationManager getTranslationManager() {
        return this.translationManager;
    }

    @Override
    public String getLanguage() {
        return this.settings.getLanguage();
    }

    @Override
    public List<Runnable> getDisableHooks() {
        return disableHooks;
    }

    @Override
    public Plugin getPlugin() {
        return this;
    }

    public void listener(Listener... listeners) {
        Arrays.stream(listeners).forEach(l->this.getProxy().getPluginManager().registerListener(this, l));
    }

    public Settings getSettings() {
        return this.settings;
    }
}