/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.global.files.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    public static File zipFiles(File folder, String fileName, File... files){
        if(!folder.exists()) folder.mkdir();
        File zipFile = new File(folder, fileName);
        try{
            if(!zipFile.exists()) zipFile.createNewFile();
        }catch (Exception ignored){}

        try{
            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
            for(File file : files){
                if(file != null){
                    if(file.isDirectory()){
                        addZipFolder(file, zipOutputStream);
                    }else{
                        addZipFile(file, zipOutputStream);
                    }
                }
            }
            zipOutputStream.close();
            return zipFile;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    private static void addZipFile(File file, ZipOutputStream out){
        byte[] buf = new byte[2048];
        try{
            FileInputStream in = new FileInputStream(file);
            out.putNextEntry(new ZipEntry(file.getPath()));
            int len;
            while((len = in.read(buf)) > 0){
                out.write(buf, 0, len);
            }
            out.closeEntry();
            in.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private static void addZipFolder(File dir, ZipOutputStream out){
        File[] files = dir.listFiles();
        if(files != null){
            if(files.length > 0){
                for(File f : files){
                    if(f.isDirectory()){
                        addZipFolder(f, out);
                    }else{
                        addZipFile(f, out);
                    }
                }
            }
        }
    }

    public static void unzip(File zipFile, File outputFolder) throws Exception{
        if(!outputFolder.exists()) outputFolder.mkdirs();
        FileInputStream fileInputStream;
        byte[] buffer = new byte[1024];
        fileInputStream = new FileInputStream(zipFile);
        ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        while(zipEntry != null){
            String fileName = zipEntry.getName();
            File newFile = new File(outputFolder + File.separator + fileName);
            new File(newFile.getParent()).mkdirs();
            FileOutputStream fileOutputStream = new FileOutputStream(newFile);
            int len;
            while ((len = zipInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, len);
            }
            fileOutputStream.close();
            zipInputStream.closeEntry();
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.closeEntry();
        zipInputStream.close();
        fileInputStream.close();
    }
}
