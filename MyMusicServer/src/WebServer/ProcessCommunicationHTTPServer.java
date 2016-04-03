/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServer;

import java.io.BufferedReader;
import java.net.Socket;

/**
 *
 * @author Michi
 */
public class ProcessCommunicationHTTPServer extends HTTPServer {

    public ProcessCommunicationHTTPServer(Socket client) {
        super(client);
    }

    @Override
    protected String GetResponseStringForGET(String headerLine, BufferedReader inFromClient) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected String GetResponseStringForPOST(String headerLine, BufferedReader inFromClient) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
