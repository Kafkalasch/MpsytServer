/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyBashServer;

/**
 *
 * @author Michi
 */
public class OutputToSystemOutCommunicator extends AOutputCommunicator {

    @Override
    public void println(String str) {
        System.out.println(str);
    }
    
}
