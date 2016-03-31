/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymusicserver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URLDecoder;
import java.util.Properties;

/**
 *
 * @author Michi
 */
public abstract class PropertiesWrapper {
    
    protected static Properties GetPropertiesFromFile(String propertiesFile) throws FileNotFoundException, IOException{
        Properties props = new Properties();
        String path = new File(PropertiesWrapper.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();
       
        try(Reader reader = new FileReader(URLDecoder.decode(path, "UTF-8") + File.separator + propertiesFile)) {
            
            props.load(reader);
            return props;
        } 
    }
    
    
}
