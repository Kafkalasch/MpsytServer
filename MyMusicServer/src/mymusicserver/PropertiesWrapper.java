/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymusicserver;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 *
 * @author Michi
 */
public class PropertiesWrapper {
    
    public static Properties GetmymusicserverProperties() throws FileNotFoundException, IOException{
        Properties props = new Properties();
        try(Reader reader = new FileReader("mymusicserver.properties")) {
            
            props.load(reader);
            return props;
        } 
    }
    
    
}
