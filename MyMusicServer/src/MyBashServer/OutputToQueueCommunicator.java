/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyBashServer;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Michi
 */
public class OutputToQueueCommunicator extends AOutputCommunicator
{
    private final ConcurrentLinkedQueue<String> queue;

    public OutputToQueueCommunicator(){
        this.queue = new ConcurrentLinkedQueue<>();
    }
    
    public OutputToQueueCommunicator(ConcurrentLinkedQueue<String> queue){
        this.queue = queue;
    }
    
    public ConcurrentLinkedQueue<String> getQueue(){
        return queue;
    }
    
    @Override
    public void println(String str) {
        queue.add(str);
    }
    
}
