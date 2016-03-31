/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymusicserver;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michi
 */
public final class MyMusicServerProperties extends PropertiesWrapper{
    
    private static String DownloadedFilesOutputDir;
    
    
    static
    {
        Properties props;
        try {
            props = GetPropertiesFromFile("mymusicserver.properties");
            DownloadedFilesOutputDir = props.getProperty("DownloadedFilesOutputDir");
        } catch (IOException ex) {
            Logger.getLogger(MyMusicServerProperties.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        
    }
    
    /***
     * Soll nicht von außen instiantiiert werden. Ist eine statische Klasse.
     */
    private MyMusicServerProperties(){}

    /**
     * @return the DownloadedFilesOutputDir
     */
    public static String getDownloadedFilesOutputDir() {
        return DownloadedFilesOutputDir;
    }
}
