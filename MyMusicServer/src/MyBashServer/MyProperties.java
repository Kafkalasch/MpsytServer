/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyBashServer;

import Properties.PropertiesWrapper;
import java.util.Properties;

/**
 *
 * @author Michi
 */
public final class MyProperties extends PropertiesWrapper {

    private static final int WebServer_Port;
    private static final String Bash_ProgramNameToExecute;
    private static final String Path_To_HTTPServer_HTML;
    private static final String Path_To_Log_File;
    private static final String Path_To_HTTPServer_CSS;
    
    static {
        Properties props;
        props = GetPropertiesFromFile("mymusicserver.properties");
        WebServer_Port = Integer.parseInt(props.getProperty("WebServer_Port"));
        Bash_ProgramNameToExecute = props.getProperty("Bash_ProgramNameToExecute");
        Path_To_HTTPServer_HTML = props.getProperty("Path_To_HTTPServer_HTML");
        Path_To_Log_File = props.getProperty("Path_To_Log_File");
        Path_To_HTTPServer_CSS = props.getProperty("Path_To_HTTPServer_CSS");

    }

    /**
     * *
     * Soll nicht von außen instiantiiert werden. Ist eine statische Klasse.
     */
    private MyProperties() {
    }

    public static String Path_To_HTTPServer_CSS(){
        return Path_To_HTTPServer_CSS;
    }
    
    public static String getPath_To_Log_File(){
        return Path_To_Log_File;
    }
    
    public static String getPath_To_HTTPServer_HTML() {
        return Path_To_HTTPServer_HTML;
    }

    public static int getWebServer_Port() {
        return WebServer_Port;
    }

    public static String getBash_ProgramNameToExecute() {
        return Bash_ProgramNameToExecute;
    }
}
