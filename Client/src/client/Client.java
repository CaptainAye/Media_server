/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.nio.file.Path;
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
    
     private static HashMap<Path,String> sharedFilesFromServer;

    /**
     * @param args the command line arguments
     */
    /*public static void main(String[] args)*/
    
    private static Integer clientId;
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
    public static Integer getId(){
        return clientId;
    }
    
    public static void addSharedFiles(int myId, HashMap<Path,String> newSharedFiles){
        if(clientId == myId){
            sharedFilesFromServer.putAll(newSharedFiles);
        }
    }
    public static HashMap<Path,String> getSharedFilesFromServer(){
        return sharedFilesFromServer;
    }
    
}
