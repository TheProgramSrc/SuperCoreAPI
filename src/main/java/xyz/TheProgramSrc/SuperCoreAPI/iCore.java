/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI;

import org.bukkit.event.Listener;
import xyz.TheProgramSrc.SuperCoreAPI.items.PreloadedItems;
import xyz.TheProgramSrc.SuperCoreAPI.skintexture.SkinTextureManager;
import xyz.TheProgramSrc.SuperCoreAPI.storage.SystemSettings;
import xyz.TheProgramSrc.SuperCoreAPI.translation.TranslationManager;
import xyz.TheProgramSrc.SuperCoreAPI.utils.tasks.TaskUtil;

import java.io.File;

public interface iCore {

    boolean isFirstStart();

    File getServerFolder();

    File getTranslationsFolder();

    File getLogsFolder();

    void log(String message);

    SystemSettings getSystemSettings();

    TranslationManager getTranslationManager();

    String getPrefix();

    String getLanguage();

    void debug(String text);

    void debug(Exception ex);

    void listener(Listener listener);

    TaskUtil getTaskUtil();

    PreloadedItems getPreloadedItems();

    SkinTextureManager getSkinManager();


}
