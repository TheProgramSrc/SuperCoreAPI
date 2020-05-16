/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.global.files;

import org.apache.commons.io.FileUtils;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * This represents a flat file without a specific format
 *
 * This is usually used for debug/log purposes
 */
public class FlatFile {

    private final File file;

    /**
     * Constructor of the FlatFile, If the file doesnt exists it will be created
     * @param file File where will be stored all the data
     */
    public FlatFile(File file){
        this.file = file;
        this.load();
    }

    /**
     * Constructor of the FlatFile, If the file doesnt exists it will be created
     * @param folder Folder where will be located the file
     * @param fileName Name of the file to store all the data
     */
    public FlatFile(File folder, String fileName){
        this(new File(Utils.folder(folder), fileName));
    }

    private void load() {
        try{
            if(!this.file.exists()) this.file.createNewFile();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Insert a line at the end of the file
     * (By default it's dated See {@link #add(String, boolean)})
     * @param line Content of the line to add
     */
    public void add(String line){
        add(line, true);
    }

    /**
     * Insert a line at the end of the file
     *
     * If dated is true the current time will be added at the start of the content
     * otherwise it wont have the date
     * @param line Content of the line to add
     * @param dated Whether the content of the line will be with the current time or not
     */
    public void add(String line, boolean dated){
        try{
            List<String> lines = this.getLines();
            if(dated) {
                lines.add( "[" + Utils.getActualTime("MMM, dd YYYY HH:mm:ss") + "]:" + line);
            } else{
                lines.add(line);
            }
            FileUtils.writeLines(this.file, lines);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Same as the add line but in this case you can add an exception
     * (By default it's dated See {@link #add(Exception, boolean)})
     * @param ex Exception to add
     */
    public void add(Exception ex){
        add(ex, true);
    }

    /**
     * Same as the add line but in this case you can add an exception
     *
     * If dated is true the current time will be added at the start of the content
     * otherwise it wont have the date
     * @param exception Exception to add
     * @param dated Whether the content of the line will be with the current time or not
     */
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

    /**
     * Removes a line from the file at the specified index
     * @param index Index to remove from the file
     */
    public void remove(int index){
        try{
            List<String> lines = this.getLines();
            lines.remove(index);
            FileUtils.writeLines(this.file, lines);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Gets the last line from the file
     * @return The last stored line in the file
     */
    public String getLastLine(){
        List<String> lines = this.getLines();
        return lines.get(lines.size()-1);
    }

    /**
     * Gets all the lines inside the file
     * @return All the lines stored in the file
     */
    public List<String> getLines(){
        try{
            return FileUtils.readLines(this.file, Charset.defaultCharset());
        }catch (Exception ex){
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Gets the file where is stored all the data
     * @return File where is stored the data
     */
    public File getFile() {
        return file;
    }
}
