/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServer;

import Helper.EventHelperListener;
import Helper.LoggerHelper;
import HttpServer.SimpleHttpHandler;
import MyBashServer.ICommandListener;
import MyBashServer.InteractiveProcessCommunicator;
import MyBashServer.NewLineInStdOutEvent;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.EventObject;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michi
 */
public class GoogleChromePluginHandler extends SimpleHttpHandler implements EventHelperListener<NewLineInStdOutEvent>{

    private static final Logger logger = LoggerHelper.getLogger(GoogleChromePluginHandler.class);
    
    private ICommandListener commandListener = null;
    private InetSocketAddress address = null;
    
    public void setAdress(InetSocketAddress address){
        this.address = address;
    }
    
    public void addCommandListener(ICommandListener commandListener){
        deregister();
        
        this.commandListener = commandListener;
        
        if(commandListener != null && commandListener instanceof InteractiveProcessCommunicator)
            ((InteractiveProcessCommunicator) commandListener).getNewLineInStdOutEventHelper().addListener(this);
    }
    
    @Override
    protected Response createResponseFromQueryParams(URI uri) {
        String query = uri.getQuery();
        if(query.startsWith("mix=") || query.startsWith("single=") || query.startsWith("stop")){
            //valid
            String youtube_url = null;
            String modus;
            if(query.startsWith("mix")){
                youtube_url = query.substring(4);
                modus = "mix";
            }else if(query.startsWith("single")){
                youtube_url = query.substring(7);
                modus = "single";
            }else{
                modus = "stop";
                processCommand(youtube_url, modus);
                return new GCPResponse("Stopped song.", HTTP_OK_STATUS);
            }
            
            
            
            try{
                processCommand(youtube_url, modus);
                return new GCPResponse("Plays " + modus, HTTP_OK_STATUS);
            }catch(Exception ex){
                logger.log(Level.SEVERE, null, ex);
                return new GCPResponse("Exception occured: " + ex.toString(), HTTP_INTERNAL_SERVER_ERROR);
            }
            
            
            
            
        }else{
            // not valid
            logger.log(Level.SEVERE, "No 'youtube-url' command found");
            return new GCPResponse("Expects command: youtube-url", HTTP_BAD_REQUEST);
        }
        
            
    }
    
    private synchronized void processCommand(String url, String modus){
        if(modus.startsWith("stop")){
            commandListener.processCommand("killAll");
            return;
        }
        
        
        boolean mix = false;
        if(modus.equals("mix"))
            mix = true;
        
        newStdOutLinePrinted = false;
        commandListener.processCommand("url " + url);
        int k = 0;
        int maxK = 30;
        while( k < maxK && newStdOutLinePrinted == false){
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                logger.log(Level.WARNING, null, ex);
            }
        }
        
        if(mix){
            newStdOutLinePrinted = false;
            commandListener.processCommand("mix 1");
            k = 0;
            maxK = 30;
            while( k < maxK && newStdOutLinePrinted == false){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    logger.log(Level.WARNING, null, ex);
                }
            } 
        }
        
        newStdOutLinePrinted = false;
        commandListener.processCommand( mix ? "1-" : "1");
            
    }


    public void deregister(){
         if(commandListener != null && commandListener instanceof InteractiveProcessCommunicator)
            ((InteractiveProcessCommunicator) commandListener).getNewLineInStdOutEventHelper().removeListener(this);
    }
    
    private boolean newStdOutLinePrinted = false;
    @Override
    public void EventOccured(NewLineInStdOutEvent event) {
        newStdOutLinePrinted = true;
    }
    
    
    public class GCPResponse extends Response{

        public GCPResponse(String response, int httpStatus) {
            super(response, httpStatus);
            if(address != null)
                response += "\nFurther information is available at http://" + address.toString();
        }
        
    }
    
    
    
}


