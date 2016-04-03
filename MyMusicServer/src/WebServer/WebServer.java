/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michi
 */
public final class WebServer {
    
    private static final Logger logger = Logger.getLogger(WebServer.class.getName());
    private static final String log_newConn = "HTTP Server Waiting for client on port ";
    private static final String log_couldNotCreateSocket = "Could not create Socket. Waiting for 60 Seconds...";
    
    
    public static void ServeConnections( Class<? extends HTTPServer> type, int port ) throws Exception {
        
        String log_newConn_withPort = log_newConn + port;
        
        new Thread(new Runnable() {

            @Override
            public void run() {
                ServerSocket Server = null;
                while(Server == null){
                    
                    try {
                        Server = new ServerSocket(port, 10, InetAddress.getByName("127.0.0.1"));
                    } catch (IOException ex) {
                        logger.log(Level.SEVERE, log_couldNotCreateSocket, ex);
                        try {
                            
                            Thread.sleep(60000);
                        } catch (InterruptedException ex1) {
                            logger.log(Level.SEVERE, null, ex1);
                        }
                    }
                }
                
                
                
                logger.log(Level.INFO, log_newConn_withPort);
                
                while (true) {
                    Socket connected;
                    try {
                        connected = Server.accept();
                        type.getConstructor(Socket.class).newInstance(connected).start();
                    } catch (IOException|
                            NoSuchMethodException|
                            SecurityException|
                            InstantiationException|
                            IllegalAccessException|
                            IllegalArgumentException|
                            InvocationTargetException ex) {
                        logger.log(Level.SEVERE, null, ex);
                    }
                    
                }
            }
        }).start();
    }
}
