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
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michi
 */
public class MyHttpHandler extends SimpleHttpHandler{
    
    private static Logger logger = LoggerHelper.getLogger(MyHttpHandler.class);
    private Template htmlTemplate;
    
    public MyHttpHandler(){
        try{
            
        htmlTemplate = new Template(FileLocator.readTextFile(MyProperties.getPath_To_HTTPServer_HTML()));
        }catch(IOException e){
            logger.log(Level.SEVERE, null, e);
            System.exit(1);
        }
        
        
        
    }
    
    @Override
    protected Response createResponseFromQueryParams(URI uri) {
        Response r = null;
        
        String command;
        try {
            HashMap<String,String> map = splitSimpleQuery(uri);
            if(map.containsKey("command"))
                command = splitSimpleQuery(uri).get("command");
            else
                command = null;
        } catch (UnsupportedEncodingException ex) {
            command = "ERROR: Konnte Befehl nicht lesen. (UnsupportedEncodingException)";
        }
        
        
        r = new Response(htmlTemplate.GetTemplatedString("<p>Du hast folgenden Text geschickt: <i>"+
               command +"</i></p>"), HTTP_OK_STATUS);
        
        
        
        return r;
    }
    
    private class Template{
        
        private final String ReplaceString = "<!--REPLACEWITHCONTENT-->";
        private final int ReplaceStringLength = ReplaceString.length();
        
        private String start;
        private String end;
        
        public Template(String template){
            int idx = template.indexOf(ReplaceString);
            start = template.substring(0, idx);
            end = template.substring(idx+ReplaceStringLength, template.length());
        }
        
        public String GetTemplatedString(String str){
            return start + str + end;
        }
    }
    
}
