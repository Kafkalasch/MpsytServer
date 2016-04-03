/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymusicserver;

/**
 *
 * @author Michi
 */
public class OutputToSystemOutCommunicator extends OutputCommunicator {

    @Override
    public void println(String str) {
        System.out.println(str);
    }
    
}
