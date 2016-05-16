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
        ArrayList<String> contexts = new ArrayList<>();
        contexts.add("/");
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        fillQueue(queue);
        HTTPServerWrapper w = new HTTPServerWrapper(contexts, queue);
        w.startServer();
//        System.out.println("Enter something here : ");
//
//        try{
//            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
//            String s = bufferRead.readLine();
//
//            System.out.println(s);
//        }
//        catch(IOException e)
//        {
//                e.printStackTrace();
//        }

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
