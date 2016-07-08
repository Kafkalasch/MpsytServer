/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServer;

import Helper.LoggerHelper;
import HttpServer.SimpleHttpServer;
import MyBashServer.ICommandListener;
import MyBashServer.MyProperties;
import java.util.ArrayList;
import java.util.logging.Logger;
import com.sun.net.httpserver.HttpHandler;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.javatuples.Pair;

/**
 *
 * @author Michi
 */
public class HTTPServerWrapper {
    
    private final Logger logger = LoggerHelper.getLogger(HTTPServerWrapper.class);
    private final static ArrayList<String> contexts = new ArrayList<>();
    
    private final static String logmsg_Server_Started = "SimpleHttpServer started on port " + MyProperties.getWebServer_Port();
    
    private final SimpleHttpServer httpServer;
    private final MainHttpHandler handler;
    private final GoogleChromePluginHandler gchromeHandler = null;
    
    public HTTPServerWrapper(Iterable<String> contexts, ConcurrentLinkedQueue<String> OutputLines,
            String css_context, String GoogleChromePlugin_context){
        handler = new MainHttpHandler(OutputLines);
        ArrayList<Pair<String, HttpHandler>> handlers = new ArrayList<>();
        for(String c : contexts){
            handlers.add(new Pair<>(c, handler));
        }
        
        if(css_context != null)
            handlers.add(new Pair<>(css_context.startsWith("/") ? css_context : "/"+css_context, new CssFileHandler()));
        
        GoogleChromePluginHandler gcph = null;
        if(GoogleChromePlugin_context != null){
            gcph = new GoogleChromePluginHandler();
            
            handlers.add(new Pair<>(GoogleChromePlugin_context.startsWith("/") ? GoogleChromePlugin_context : "/"+GoogleChromePlugin_context, gcph));
        }
        
        httpServer = new HttpServer.SimpleHttpServer(MyProperties.getWebServer_Port(),handlers);
        
        if(gcph != null)
            gcph.setAdress(httpServer.GetAdress());
    }
    
    public void AddCommandListener(ICommandListener comm){
        this.handler.addCommandListener(comm);
        if(gchromeHandler != null)
            gchromeHandler.addCommandListener(comm);
    }
    
    
    public void startServer(){
        httpServer.start();
        //logger.log(Level.INFO, logmsg_Server_Started);
        //System.out.println(logmsg_Server_Started);
    }
    
}
