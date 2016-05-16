/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyBashServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author Michi
 */
public class InteractiveProcessCommunicator implements ICommandListener {
    
    private ProcessBuilder builder;
    private Process process = null;
    private PrintWriter writerToStdin = null;
    private PrintWriter printStdOutTo;
    
    public InteractiveProcessCommunicator(AOutputCommunicator outputCommunicator) throws IOException{
        this.printStdOutTo = new PrintWriter(outputCommunicator.GetOutputStream());
        builder = new ProcessBuilder("/bin/bash");
        builder.redirectErrorStream(true); // vereint stderr und stdout
        //builder.inheritIO()
    }
    
    public void startProcess(String processName) throws IOException{
        process = builder.start();
        OutputStream stdin = process.getOutputStream();
        InputStream stdout = process.getInputStream();
        
        // Printed stdout in einem separatem Thread
        new Thread(new Runnable() {
         public void run() {
            InputStreamReader reader = new InputStreamReader(stdout);
            Scanner scan = new Scanner(reader);
            while (scan.hasNextLine()) {
               printStdOutTo.println(scan.nextLine());
               printStdOutTo.flush();
            }
         }
        }).start();
        
        writerToStdin = new PrintWriter(stdin);
        
        this.println(processName);
    }
    
    public void println(String line){
        printStdOutTo.println(line);
        writerToStdin.println(line);
        writerToStdin.flush();
    }
    
    public void killProcess(){
        process.destroy();
    }
    
    public void waitForProcessToEnd() throws InterruptedException{
        process.waitFor();
    }

    @Override
    public void processCommand(String command) {
        println(command);
    }
    
}
