/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServer;

import Helper.IO.FileLocator;
import Helper.LoggerHelper;
import HttpServer.SimpleHttpHandler;
import MyBashServer.MyProperties;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michi
 */
public class CssFileHandler extends SimpleHttpHandler{

    private Response CSS = null;
    
    public CssFileHandler(){
        try {
            String CSS_String =  FileLocator.readTextFile(MyProperties.Path_To_HTTPServer_CSS());
            CSS = new Response(CSS_String, HTTP_OK_STATUS);
        } catch (IOException ex) {
            LoggerHelper.getLogger(this).log(Level.WARNING, "CSS-Datei konnte nicht gefunden oder gelesen werden.", ex);
        }
    }
    
    
    @Override
    protected Response createResponseFromQueryParams(URI uri) {
        if(CSS == null){
            return new Response("", HTTP_NOT_FOUND_STATUS);
            
        }else{
            return CSS;
        }
    }
    
}
