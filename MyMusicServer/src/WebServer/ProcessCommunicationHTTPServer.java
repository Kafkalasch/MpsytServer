/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServer;

import MyBashServer.MyProperties;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michi
 */
public class ProcessCommunicationHTTPServer extends HTTPServer {

    private static final Logger logger = Logger.getLogger(ProcessCommunicationHTTPServer.class.getName());
    private static String HTML_Template = null;
    
    public ProcessCommunicationHTTPServer(Socket client) {
        super(client);
        
    }

    @Override
    protected String GetResponseStringForGET(String headerLine, BufferedReader inFromClient) {
        return "Dies ist nur ein Test";
    }

    @Override
    protected String GetResponseStringForPOST(String headerLine, BufferedReader inFromClient) {
        return "Dies ist nur ein Test";
    }
    
    private void ReadTemplate(){
        if(HTML_Template != null)
            return;
        try
        {
            String parentPath = new File(ProcessCommunicationHTTPServer.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();
            File templateFile = new File(URLDecoder.decode(parentPath, "UTF-8") + File.separator + MyProperties.getPath_To_HTTPServer_HTML());
            BufferedReader reader = new BufferedReader(new FileReader(templateFile));
            StringBuilder builder = new StringBuilder();
            String str = reader.readLine();
            while(str != null){
                builder.append(str);
                str = reader.readLine();
            }
            HTML_Template = builder.toString();
            
        }catch(IOException ex){
            logger.log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
}
