/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import xyz.TheProgramSrc.SuperCoreAPI.items.PreloadedItems;
import xyz.TheProgramSrc.SuperCoreAPI.skintexture.SkinTextureManager;
import xyz.TheProgramSrc.SuperCoreAPI.storage.SystemSettings;
import xyz.TheProgramSrc.SuperCoreAPI.translation.TranslationManager;
import xyz.TheProgramSrc.SuperCoreAPI.utils.tasks.TaskUtil;

import java.io.File;

public class SuperModule implements iCore, Listener {

    private SuperCore core;

    public SuperModule(SuperCore core){
        this(core, true);
    }

    public SuperModule(SuperCore core, boolean registerListener){
        this.core = core;
        if(registerListener){
            Bukkit.getPluginManager().registerEvents(this, core);
        }
        this.onModuleLoad();
    }

    public void onModuleLoad(){

    }

    @Override
    public void log(String message) {
        this.getCore().log(message);
    }

    @Override
    public boolean isFirstStart() {
        return getCore().isFirstStart();
    }

    @Override
    public File getServerFolder() {
        return getCore().getServerFolder();
    }

    @Override
    public File getTranslationsFolder() {
        return this.getCore().getTranslationsFolder();
    }

    @Override
    public File getLogsFolder() {
        return this.getCore().getLogsFolder();
    }

    @Override
    public SystemSettings getSystemSettings() {
        return this.getCore().getSystemSettings();
    }

    @Override
    public TranslationManager getTranslationManager() {
        return this.getCore().getTranslationManager();
    }

    @Override
    public String getPrefix() {
        return this.getCore().getPrefix();
    }

    @Override
    public String getLanguage() {
        return this.getCore().getLanguage();
    }

    @Override
    public void debug(String text) {
        this.getCore().debug(text);
    }

    @Override
    public void debug(Exception ex) {
        this.getCore().debug(ex);
    }

    @Override
    public void listener(Listener listener) {
        this.getCore().listener(listener);
    }

    @Override
    public TaskUtil getTaskUtil() {
        return this.getCore().getTaskUtil();
    }

    @Override
    public PreloadedItems getPreloadedItems() {
        return this.getCore().getPreloadedItems();
    }

    @Override
    public SkinTextureManager getSkinManager() {
        return this.getCore().getSkinManager();
    }

    public SuperCore getCore() {
        return core;
    }
}
