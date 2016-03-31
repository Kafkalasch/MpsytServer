/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymusicserver;

import java.io.File;

/**
 *
 * @author Michi
 */
public class YoutubeDownloader {
    
    private FileManager fileManager;
    
    public YoutubeDownloader(FileManager fileManager){
        this.fileManager = fileManager;
    }
    
    public void downloadUrl(String url){
        new File(fileManager.getTopDir(), url).mkdir();
    }
}
