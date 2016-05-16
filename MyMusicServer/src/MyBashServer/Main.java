/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyBashServer;

import WebServer.HTTPServerWrapper;
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
     */
    public static void main(String[] args) throws IOException, InterruptedException, Exception {
               
        //OutputToQueueCommunicator otc = new OutputToQueueCommunicator();
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        InteractiveProcessCommunicator comm = new InteractiveProcessCommunicator(queue);
        comm.startProcess("mpsyt");
        startHTTPServer(queue).getMainHttpHandler().addCommandListener(comm);
        
        comm.waitForProcessToEnd();
        System.out.println("End this program");
        
////        InteractiveProcessCommunicator comm = new InteractiveProcessCommunicator(new OutputToFileCommunicator("ProcessOutpur", true).GetOutputStream());
////        comm.startProcess();
//        String input = "";
////        System.out.println("Enter exit or quit to end the program.");
//        Scanner scan = new Scanner(System.in);
////        //Thread.sleep(1000);
////        //comm.println("/kiesza");
////        while(true)
////        {
//            
//            input = scan.nextLine();
////            if(input.equals("exit") || input.equals("quit"))
////                    break;
////            
////            comm.println(input);
////
////        }
////        comm.killProcess();
////        comm.waitForProcessToEnd();
        
        
    }
    
    private static HTTPServerWrapper startHTTPServer(ConcurrentLinkedQueue<String> queue){
        ArrayList<String> contexts = new ArrayList<>();
        contexts.add("/");
        HTTPServerWrapper w = new HTTPServerWrapper(contexts, queue);
        w.startServer();
        return w;
    }
    
    private static void testServer(){
        ArrayList<String> contexts = new ArrayList<>();
        contexts.add("/");
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        fillQueue(queue);
        HTTPServerWrapper w = new HTTPServerWrapper(contexts, queue);
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
