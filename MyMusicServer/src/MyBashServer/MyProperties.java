/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyBashServer;

import Properties.PropertiesWrapper;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michi
 */
public final class MyProperties extends PropertiesWrapper{
    
    private static int WebServer_Port;
    private static String Bash_ProgramNameToExecute;
    private static String Path_To_HTTPServer_HTML;
    
    static
    {
        Properties props;
            props = GetPropertiesFromFile("mymusicserver.properties");
            WebServer_Port = Integer.parseInt(props.getProperty("WebServer_Port"));
            Bash_ProgramNameToExecute = props.getProperty("Bash_ProgramNameToExecute");
            Path_To_HTTPServer_HTML = props.getProperty("Path_To_HTTPServer_HTML");
        
        
    }
    
    /***
     * Soll nicht von außen instiantiiert werden. Ist eine statische Klasse.
     */
    private MyProperties(){}

    public static String getPath_To_HTTPServer_HTML(){
        return Path_To_HTTPServer_HTML;
    }
    
    public static int getWebServer_Port() {
        return WebServer_Port;
    }
    
    public static String getBash_ProgramNameToExecute() {
        return Bash_ProgramNameToExecute;
    }
}
