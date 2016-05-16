/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServer;

import Helper.LoggerHelper;
import HttpServer.SimpleHttpHandler;
import HttpServer.SimpleHttpServer;
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
    
    public HTTPServerWrapper(Iterable<String> contexts, ConcurrentLinkedQueue<String> OutputLines,
            String css_context){
        handler = new MainHttpHandler(OutputLines);
        ArrayList<Pair<String, HttpHandler>> handlers = new ArrayList<>();
        for(String c : contexts){
            handlers.add(new Pair<>(c, handler));
        }
        
        if(css_context != null)
            handlers.add(new Pair<>(css_context.startsWith("/") ? css_context : "/"+css_context, new CssFileHandler()));
        
        httpServer = new HttpServer.SimpleHttpServer(MyProperties.getWebServer_Port(),handlers);
    }
    
    public MainHttpHandler getMainHttpHandler(){
        return handler;
    }
    
    public void startServer(){
        httpServer.start();
        //logger.log(Level.INFO, logmsg_Server_Started);
        //System.out.println(logmsg_Server_Started);
    }
    
}
