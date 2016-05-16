/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServer;

import DataStructures.LinkedList;
import Helper.IO.FileLocator;
import Helper.LoggerHelper;
import HttpServer.SimpleHttpHandler;
import MyBashServer.ICommandListener;
import MyBashServer.MyProperties;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michi
 */
public class MainHttpHandler extends SimpleHttpHandler{
    
    
    
    private static Logger logger = LoggerHelper.getLogger(MainHttpHandler.class);
    private Template htmlTemplate;
    
    private final LinkedList<String> BufferedLines = new LinkedList<>();
    private final ConcurrentLinkedQueue<String> newOutputLines;
    private final QueryParams queryParams = new QueryParams();
    
    public MainHttpHandler(ConcurrentLinkedQueue<String> output){
        this. newOutputLines = output;
        try{
            
        htmlTemplate = new Template(FileLocator.readTextFile(MyProperties.getPath_To_HTTPServer_HTML()));
        }catch(IOException e){
            logger.log(Level.SEVERE, null, e);
            System.exit(1);
        }
        
        
        
    }
    
    @Override
    protected Response createResponseFromQueryParams(URI uri) {
        ProcessQueryParams(uri);
        return new Response(htmlTemplate.GetTemplatedString(GetOutputLines()), HTTP_OK_STATUS);

    }
    
    private final int defaultLength = 100;
    private int ProcessLength(HashMap<String,String> map){
        int length = defaultLength;
        if(map.containsKey("length")){
            try
            {
                length = Integer.parseInt(map.get("length"));
            }catch(NumberFormatException e){
                
            }
        }
        return length;
    }
    
    private void ProcessQueryParams(URI uri){
        String command;
        int length = defaultLength;
        try {
            HashMap<String,String> map = splitSimpleQuery(uri);
            if(map.containsKey("command"))
                command = map.get("command");
            else
                command = null;
            
            length = ProcessLength(map);
            
        } catch (UnsupportedEncodingException ex) {
            command = "ERROR: Konnte Befehl nicht lesen. (UnsupportedEncodingException)";
        }
        
        
        queryParams.command = command;
        
        processCommand(command);
        
        queryParams.length = length;
        
    }
    
    private final List<ICommandListener> _CommandListeners = new ArrayList<>();
    
    public synchronized void addCommandListener( ICommandListener l ) {
        _CommandListeners.add( l );
    }
    
    public synchronized void removeCommandListener( ICommandListener l ) {
        _CommandListeners.remove( l );
    }

    
    private synchronized void processCommand(String command){
        if(command != null){
            for(ICommandListener l : _CommandListeners){
                l.processCommand(command);
            }
        }
    }
    
    
    private String GetOutputLines(){
        
        
        String tmp = newOutputLines.poll();
        while(tmp != null){
            BufferedLines.add(tmp);
            tmp = newOutputLines.poll();
        }
        
        
        int i = 0;
        LinkedList<String>.Node<String> tmpNode = BufferedLines.endNode;
        LinkedList<String> buildText = new LinkedList<>();
        while(i < queryParams.length && tmpNode != null){
            buildText.add(tmpNode.element);
            tmpNode = tmpNode.previousNode;
            i++;
        }
        
        if(tmpNode != null && i >= queryParams.length){
            BufferedLines.removeAllNodesBefore(tmpNode.nextNode == null ? tmpNode : tmpNode.nextNode);
        }
        
        StringBuilder builder = new StringBuilder();
        for(Iterator<String> iter = buildText.reverseIterator(); iter.hasNext();){
            String s = iter.next();
            builder.append(s);
            builder.append("<br>");
        }
        
        
        return builder.toString();

    }
    
    private class QueryParams
    {
        public String command;
        public int length;
        
        
    }
    
    private class Template{
        
        private final String ReplaceString = "<!--REPLACEWITHCONTENT-->";
        private final int ReplaceStringLength = ReplaceString.length();
        
        private String start;
        private String end;
        
        public Template(String template){
            int idx = template.indexOf(ReplaceString);
            start = template.substring(0, idx);
            end = template.substring(idx+ReplaceStringLength, template.length());
        }
        
        public String GetTemplatedString(String str){
            
            return start.replaceFirst("<input type=\"number\" name=\"length\" value=(.*?>)","<input type=\"number\" name=\"length\" value=\""+queryParams.length+"\">" ) + str + end;
        }
    }
    
}
