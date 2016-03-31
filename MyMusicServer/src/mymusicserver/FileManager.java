/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymusicserver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Michi
 */
public class FileManager {
    
    private File topDir;
    
    public FileManager(){
        
        topDir = new File(MyMusicServerProperties.getDownloadedFilesOutputDir());
        if(!topDir.exists()){
            topDir.mkdir();
        }
        
    }
    
    public File getTopDir(){
        return topDir;
    }
    
    public File getNewPlaylistFolder(String title){
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        File newFolder = new File(topDir, strDate + " " + title);
        int k = 1;
        while(newFolder.exists()){
            newFolder = new File(topDir, strDate + " " + title + " " + k++);
        }
        newFolder.mkdir();
        return newFolder;
    }
    
    public File getNewPlaylistFolder(){
        return getNewPlaylistFolder("Playlist");
    }
    
}
