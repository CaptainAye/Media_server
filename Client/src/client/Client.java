/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import client.GUI.Controller;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import org.mediaserver.communication.BroadcastReceiver;
import org.mediaserver.communication.DedicatedSender;
import org.mediaserver.communication.ServerReceiver;
import org.mediaserver.communication.SignalParser;
import org.mediaserver.communication.SignalReceiver;
import org.mediaserver.generator.IdentificationNumberGenerator;
import org.mediaserver.signals.BroadcastSignal;

/**
 *
 * @author Tomek
 */


public class Client {
    
    

    /**
     * @param args the command line arguments
     */
    /*public static void main(String[] args)*/
    
    private static Integer clientId;
    private static HashMap<String,Integer> sharedFilesFromServer;
    private static Controller control;
    Path pt;
    
    public Client(){
        clientId = IdentificationNumberGenerator.generateId();
        BroadcastReceiver receiver = new BroadcastReceiver(10502); // port = 10500
        SignalParser parser = SignalParser.getParser();
        Thread parserThread = new Thread(parser);
        parserThread.start();
        Thread serverReceiverThread = new Thread(receiver);
        serverReceiverThread.start();
        sharedFilesFromServer = new HashMap<>();
    }
    public static void setController(Controller controller){
        control = controller;
    }
    public static Integer getId(){
        return clientId;
    }
    public static void addSharedFiles(HashMap<Path,Integer> newSharedFiles){
        HashMap<String, Integer> transformedMap = new HashMap<>();
        for (Path path : newSharedFiles.keySet()){
            Integer client = newSharedFiles.get(path);
            transformedMap.put(path.toString(), client);
        }
        System.out.println(transformedMap.size());
        sharedFilesFromServer.putAll(transformedMap);
    }
    
    public static HashMap<String, Integer> getStringSharedFilesFromServer(){
        return sharedFilesFromServer;
    }
    public static HashMap<Path,Integer> getSharedFilesFromServer(){
        HashMap<Path, Integer> transformedMap = new HashMap<>();
        for (String path : sharedFilesFromServer.keySet()){
            Integer client = sharedFilesFromServer.get(path);
            transformedMap.put(Paths.get(path), client);
        }
        return transformedMap;
        
    }
    
}
