/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.config;

import org.apache.commons.io.FileUtils;
import xyz.TheProgramSrc.SuperCoreAPI.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class FlatFile {

    private File file;

    public FlatFile(File file){
        this.file = file;
        this.load();
    }

    public FlatFile(File folder, String fileName){
        if(!folder.exists()) folder.mkdir();
        this.file = new File(folder, fileName);
        this.load();
    }

    private void load() {
        try{
            if(!this.file.exists()) this.file.createNewFile();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void add(String line){
        add(line, true);
    }

    public void add(String line, boolean dated){
        try{
            List<String> lines = this.getLines();
            if(dated) {
                lines.add( "[" + Utils.getActualTime("dd/MM/YYYY HH_mm_ss") + "]:" + line);
            } else{
                lines.add(line);
            }
            FileUtils.writeLines(this.file, lines);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void add(Exception ex){
        add(ex, true);
    }

    public void add(Exception exception, boolean dated){
        try{
            PrintWriter printWriter = new PrintWriter(new FileOutputStream(this.file));
            if(dated) printWriter.write("["+Utils.getActualTime("dd/MM/YYYY HH_mm_ss")+"]:");
            exception.printStackTrace(printWriter);
            printWriter.flush();
            printWriter.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void remove(int index){
        try{
            List<String> lines = this.getLines();
            lines.remove(index);
            FileUtils.writeLines(this.file, lines);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public String getLastLine(){
        List<String> lines = this.getLines();
        return lines.get(lines.size()-1);
    }

    public List<String> getLines(){
        try{
            return FileUtils.readLines(this.file, Charset.defaultCharset());
        }catch (Exception ex){
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    public File getFile() {
        return file;
    }
}
