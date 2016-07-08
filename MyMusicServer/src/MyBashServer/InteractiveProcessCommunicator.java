/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyBashServer;

import Helper.EventHelper;
import Helper.LoggerHelper;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;

/**
 *
 * @author Michi
 */
public class InteractiveProcessCommunicator implements ICommandListener {
    
    private final ProcessBuilder builder;
    private Process process = null;
    private PrintWriter writerToStdin = null;
    //private PrintWriter printStdOutTo;
    private AbstractQueue<String> printStdOutTo;
    private ArrayList<Writer> Writers = new ArrayList<>();
    private final EventHelper<NewLineInStdOutEvent> eventHelper = new EventHelper<>();
    
    public InteractiveProcessCommunicator(AbstractQueue<String> queue) throws IOException{
        //this.printStdOutTo = new PrintWriter(outputCommunicator.GetOutputStream());
        builder = new ProcessBuilder("/bin/bash");
        builder.redirectErrorStream(true); // vereint stderr und stdout
        //builder.inheritIO()
        this.printStdOutTo = queue;
    }
    
    public void AddWriter(Writer writer){
        Writers.add(writer);
    }
    
    public EventHelper<NewLineInStdOutEvent> getNewLineInStdOutEventHelper(){
        return eventHelper;
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
                String nextLine = scan.nextLine();
                if(CheckIfUserHasAccessToBash(nextLine)){
                    System.exit(-1);
                }
                printStdOutTo.add(nextLine);
                eventHelper.fireEvent(new NewLineInStdOutEvent(this, nextLine));
            }
         }
        }).start();
        
        writerToStdin = new PrintWriter(stdin);
        
        this.println(processName);
    }
    
    private boolean CheckIfUserHasAccessToBash(String output){
        if(output.startsWith("/bin/bash"))
            return true;
        else
            return false;
    }
    
    public void println(String line){
        printStdOutTo.add(line);
        writerToStdin.println(line);
        writerToStdin.flush();
        for(Writer w : Writers){
            try {
                w.write(line+"\n");
            } catch (IOException ex) {
                LoggerHelper.getLogger(this).log(Level.WARNING, null, ex);
            }
        }
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
