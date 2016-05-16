/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyBashServer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author Michi
 */
public abstract class AOutputCommunicator {
    
    private PipedOutputStream outputStream = null;
    
    
    public OutputStream GetOutputStream() throws IOException{
        if(outputStream == null)
            CreateWriter();
        return outputStream;
    }
    
    private void CreateWriter() throws IOException{
        outputStream = new PipedOutputStream();
        PipedInputStream pis = new PipedInputStream(outputStream);
        
        new Thread(new Runnable() {
         public void run() {
            Scanner scan = new Scanner(pis);
            while (scan.hasNextLine()) {
               println(scan.nextLine());
            }
         }
        }).start();
        
    }
    
    public abstract void println(String str);
    
    
    
    
}
