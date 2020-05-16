/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.global.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUtils {

    public static void downloadUsingCommons(String url, File file) throws Exception {
        org.apache.commons.io.FileUtils.copyURLToFile(new URL(url), file);
    }

    public static boolean downloadUsingStream(String urlLocation, File output){
        try{
            URL url = new URL(followRedirects(urlLocation));
            BufferedInputStream bis = new BufferedInputStream(url.openStream());
            FileOutputStream fis = new FileOutputStream(output);
            byte[] buffer = new byte[1024];
            int count=0;
            while((count = bis.read(buffer,0,1024)) != -1) {
                fis.write(buffer, 0, count);
            }
            fis.close();
            bis.close();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    private static String followRedirects(String url) {
        URL urlTmp;
        String redUrl;
        HttpURLConnection connection;
        try { urlTmp = new URL(url); } catch (Exception e1) { return url; }
        try { connection = (HttpURLConnection) urlTmp.openConnection(); } catch (Exception e) { return url; }
        try { connection.getResponseCode(); } catch (Exception e) { return url; }
        redUrl = connection.getURL().toString();
        connection.disconnect();
        if(redUrl.equals(url)) {
            try {
                URL obj = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
                conn.setReadTimeout(5000);
                conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                conn.addRequestProperty("User-Agent", "Mozilla");
                conn.addRequestProperty("Referer", "google.com");
                boolean redirect = false;
                int status = conn.getResponseCode();
                if (status != HttpURLConnection.HTTP_OK) {
                    if (status == HttpURLConnection.HTTP_MOVED_TEMP
                            || status == HttpURLConnection.HTTP_MOVED_PERM
                            || status == HttpURLConnection.HTTP_SEE_OTHER)
                        redirect = true;
                }
                if (redirect) {
                    return conn.getHeaderField("Location");
                }
            } catch (Exception e) {}
        } else {
            return redUrl;
        }
        return url;
    }
}
