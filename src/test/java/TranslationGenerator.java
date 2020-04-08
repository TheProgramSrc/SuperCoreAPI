/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */


/*

GENERATE THE TRANSLATION FILE

 */

import org.apache.commons.io.FileUtils;
import xyz.TheProgramSrc.SuperCoreAPI.translation.Base;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class TranslationGenerator{
    public static void main(String[] args) {
        try{
            String translationName = new Locale("en","US").toString();
            File folder = new File(".", "SuperCoreAPI/");
            if(!folder.exists()) folder.mkdir();
            File file = new File(folder,  translationName+".lang");
            if(file.exists()) file.delete();
            file.createNewFile();
            List<String> values = Arrays.stream(Base.values()).map(t-> {
                if(t.get().getContent().contains("&")){
                    return t.name() + ": '" + t.get().getContent() + "'";
                }else{
                    return t.name() + ": " + t.get().getContent();
                }
            }).collect(Collectors.toList());
            FileUtils.writeLines(file, values);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
