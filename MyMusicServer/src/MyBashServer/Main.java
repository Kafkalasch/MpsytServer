/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyBashServer;

import Helper.IO.FileLocator;
import Helper.LoggerHelper;
import WebServer.HTTPServerWrapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Michi
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException, Exception {
              
        startAndRunMusicServer();
        
     
        
    }
    
    private static void startAndRunMusicServer() throws IOException, InterruptedException{
        File logFile = new File(MyProperties.getPath_To_Log_File());
        if(!logFile.exists())
             logFile.createNewFile();
        
        LoggerHelper.RegisterFileForLogging(logFile.getAbsolutePath());
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        InteractiveProcessCommunicator comm = new InteractiveProcessCommunicator(queue);
        comm.startProcess("mpsyt");
        startHTTPServer(queue).getMainHttpHandler().addCommandListener(comm);
        comm.waitForProcessToEnd();
        System.out.println("End this program");
    }
    
    private static HTTPServerWrapper startHTTPServer(ConcurrentLinkedQueue<String> queue){
        ArrayList<String> contexts = new ArrayList<>();
        contexts.add("/");
        HTTPServerWrapper w = new HTTPServerWrapper(contexts, queue, MyProperties.Path_To_HTTPServer_CSS());
        w.startServer();
        return w;
    }
    
    private static void testServer(){
        ArrayList<String> contexts = new ArrayList<>();
        contexts.add("/");
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        fillQueue(queue);
        HTTPServerWrapper w = new HTTPServerWrapper(contexts, queue, null);
        w.startServer();
    }
    
    private static void fillQueue(ConcurrentLinkedQueue<String> queue){
        new Thread()
        {
            public void run() {
                int i = 0;
                while(true){
                   i++;   
                   String msg = "Thread 'fillQueue' Durchlauf: " + i;
                   System.out.println(msg);
                   queue.add(msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();
    }
    
}
