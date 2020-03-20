/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.database;

import org.bukkit.scheduler.BukkitTask;
import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.SuperModule;

import java.sql.ResultSet;
import java.sql.Statement;

public class SQLDataManager extends SuperModule {

    protected DataBase dataBase;

    public SQLDataManager(SuperCore core, DataBase dataBase) {
        super(core);
        this.dataBase = dataBase;
    }

    protected String getTablePrefix(){
        return this.getCore().getName().toLowerCase() + "_";
    }

    protected int lastInsertedId() {
        String query;
        if (this.dataBase instanceof SQLiteDataBase) {
            query = "SELECT last_insert_rowid()";
        } else {
            query = "SELECT LAST_INSERT_ID()";
        }

        try (Statement statement = this.dataBase.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery(query);
            result.next();
            return result.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public BukkitTask async(Runnable runnable) {
        return this.getTaskUtil().runAsyncTask(runnable);
    }


    public BukkitTask sync(Runnable runnable){
        return this.getTaskUtil().runTask(runnable);
    }


}
