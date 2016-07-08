/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyBashServer;

import java.util.EventObject;

/**
 *
 * @author Michi
 */
public class NewLineInStdOutEvent extends EventObject{

    private String newLine;
    
    public NewLineInStdOutEvent(Object source, String newLine) {
        super(source);
        this.newLine = newLine;
    }
    
    public String getNewLine(){
        return newLine;
    }
    
}
