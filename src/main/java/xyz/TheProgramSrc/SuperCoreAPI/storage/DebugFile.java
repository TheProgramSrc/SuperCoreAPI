/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.storage;

import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.config.FlatFile;
import xyz.TheProgramSrc.SuperCoreAPI.utils.Utils;
import xyz.TheProgramSrc.SuperCoreAPI.utils.ZipUtils;

import java.io.File;

public class DebugFile {

    private SuperCore core;
    private FlatFile latestLog;
    private File file;

    public DebugFile(SuperCore core){
        this.core = core;
        this.file = new File(core.getLogsFolder(), "latest.log");
        if(this.file.exists()){
            File file = ZipUtils.zipFiles(this.core.getLogsFolder(), Utils.getActualTime("dd.MM.YYYY HH_mm_ss")+".zip", this.file);
            if(file == null) this.core.log("&cCannot zip latest log file!");
            this.file.delete();
        }
        this.latestLog = new FlatFile(this.file);
    }

    public void debug(String text){
        this.latestLog.add(text);
    }

    public void debug(Exception ex){
        this.latestLog.add(ex);
    }

    public SuperCore getCore() {
        return core;
    }
}
