/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServer;


import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class HTTPServer extends Thread {

    private static final Logger logger = Logger.getLogger(HTTPServer.class.getName());
    
    
    protected final static String HTML_TITLE = "Titel";

    protected final static String HTML_START
            = "<html>"
            + "<title>" + HTML_TITLE + "</title>"
            + "<body>";

    protected static final String HTML_END
            = "</body>"
            + "</html>";

    private Socket connectedClient = null;
    private BufferedReader inFromClient = null;
    private DataOutputStream outToClient = null;

    public HTTPServer(Socket client) {
        connectedClient = client;
    }

    @Override
    public final void run() {

        
        try {

            logger.log(Level.FINE, "The Client " + connectedClient.getInetAddress() + ":" + connectedClient.getPort() + " is connected");

            inFromClient = new BufferedReader(new InputStreamReader(connectedClient.getInputStream()));
            outToClient = new DataOutputStream(connectedClient.getOutputStream());

            String headerLine = inFromClient.readLine();
            StringTokenizer tokenizer = new StringTokenizer(headerLine);
            String httpMethod = tokenizer.nextToken();
            String httpQueryString = tokenizer.nextToken();

            logger.log(Level.FINER,headerLine);

            if (httpMethod.equals("GET")) {
                sendResponse(GetResponseStringForGET(headerLine, inFromClient));
            } else {
                //POST request
                sendResponse(GetResponseStringForPOST(headerLine, inFromClient));                   
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }

    protected abstract String GetResponseStringForGET(String headerLine, BufferedReader inFromClient);
    protected abstract String GetResponseStringForPOST(String headerLine, BufferedReader inFromClient);
    
    private void sendResponse(String responseString) throws IOException{
        outToClient.writeBytes(decorateWithHTML(responseString));
        outToClient.close();
    }
    
    private String decorateWithHTML(String msg){
        return HTML_START + msg + HTML_END;
    }

    
}
