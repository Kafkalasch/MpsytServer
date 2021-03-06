/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyBashServer;

import Helper.LoggerHelper;
import WebServer.HTTPServerWrapper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
        
            System.out.println("starte programm.");
            ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
            InteractiveProcessCommunicator comm = new InteractiveProcessCommunicator(queue);
            comm.AddWriter(GetStdOutFileWriter());
            comm.startProcess(MyProperties.getBash_ProgramNameToExecute());
            startHTTPServer(queue).AddCommandListener(comm);
            comm.waitForProcessToEnd();
            stopHTTPServer();
            comm.killProcess();
            System.out.println("program beendet.");
            System.exit(0);
    }
    
    
    private static BufferedWriter _StdOutFileWriter = null;
    private static BufferedWriter GetStdOutFileWriter(){
        if(_StdOutFileWriter != null)
            return _StdOutFileWriter;
        
        File logfile = new File(MyProperties.getPath_To_StdOut_File());
        try {
            _StdOutFileWriter = new BufferedWriter(new FileWriter(logfile, true));
        } catch (IOException ex) {
            LoggerHelper.getLogger(Main.class).log(Level.SEVERE, null, ex);
        }
        return _StdOutFileWriter;
    }
    
    private static HTTPServerWrapper w = null;
    private static void stopHTTPServer(){
        if(w != null)
            w.stopServer();
        w = null;
    }
    
    private static HTTPServerWrapper startHTTPServer(ConcurrentLinkedQueue<String> queue){
        stopHTTPServer();
        ArrayList<String> contexts = new ArrayList<>();
        contexts.add("/");
        w = new HTTPServerWrapper(contexts, queue, MyProperties.getPath_To_HTTPServer_CSS(), MyProperties.getPath_To_HTTPServer_GoogleChromePlugin());
        w.startServer();
        return w;
    }
    
    private static void testServer(){
        ArrayList<String> contexts = new ArrayList<>();
        contexts.add("/");
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        fillQueue(queue);
        HTTPServerWrapper w = new HTTPServerWrapper(contexts, queue, null, null);
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
