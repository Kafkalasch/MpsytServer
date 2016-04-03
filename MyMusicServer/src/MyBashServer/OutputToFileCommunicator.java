/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyBashServer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michi
 */
public class OutputToFileCommunicator extends OutputCommunicator{

    private FileWriter fileWriter;
    private boolean autoFlush;
    
    public OutputToFileCommunicator(String filename, boolean autoFlush) throws IOException{
        fileWriter = new FileWriter(new File(filename), false);
        this.autoFlush = autoFlush;
    }
    
    @Override
    public void println(String str) {
        try {
            fileWriter.append(str);
            if(autoFlush)
                fileWriter.flush();
            
        } catch (IOException ex) {
            Logger.getLogger(OutputToFileCommunicator.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    
}
