/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.database;

import xyz.TheProgramSrc.SuperCoreAPI.utils.Utils;

import java.io.File;

public enum DataBaseType {

    SQLite("jdbc:sqlite:$1"),
    MySQL("jdbc:mysql://$1:$2/$3?useSSL=$4")

    ;

    private String url;

    DataBaseType(String url){
        this.url = url;
    }

    public String getURL() {
        return url;
    }

    public String formatMySQL(String host, String port, String database, boolean ssl){
        return Utils.vars(this.getURL(), "$INDEX", host, port, database, ssl+"");
    }

    public String formatSQLite(File folder, String fileName){
        fileName = fileName.endsWith(".db") ? fileName : (fileName+".db");
        try{
            if(!folder.exists()) folder.mkdir();
            File file = new File(folder, fileName);
            if(!file.exists()) file.createNewFile();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        String location = folder + File.separator + fileName;
        return Utils.vars(this.getURL(), "$INDEX", location);
    }
}
