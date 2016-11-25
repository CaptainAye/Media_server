/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.commands;

<<<<<<< HEAD
import client.FileSearcher;
import java.nio.file.Path;
import java.util.HashMap;
=======
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
>>>>>>> 34108e5797c6cfa73ea1866c9d6ebded31814075
import org.mediaserver.communication.QueuePacket;
import org.mediaserver.communication.SignalReceiver;
import org.mediaserver.interfaces.Command;
import org.mediaserver.sockets.BroadcastSocket;

/**
 *
 * @author Tomek
 */
public class BroadcastSignalCommand implements Command{
<<<<<<< HEAD
    private Boolean searched = false;
    private HashMap<String,Path> filesMap = new HashMap<String,Path>();
    public synchronized void execute(QueuePacket data){
        System.out.println("Listen to Host: " + data.getSignal().getLocalIp()+ " on port: " + data.getSignal().getSourcePort());
        if (searched == false){
            filesMap = FileSearcher.searchDirectories();
            searched = true;
        }
=======
    private Socket broadcastSocket;
    private ArrayList<String> servers;
    
    public void execute(QueuePacket data){
        System.out.println("Server :" + data.getSignal().getId());
        
        //stworzyć socket z adresemIP serwera i nr portu, na którym ten nasłuchuje
        String iP = data.getSignal().getLocalIp();
        Integer port = data.getSignal().getSourcePort();
        
        try {
            broadcastSocket = new Socket(iP, port);
            SignalReceiver.getSignalReceiver().connectSocket(broadcastSocket);
        } catch (IOException ex) {
            Logger.getLogger(BroadcastSignalCommand.class.getName()).log(Level.SEVERE, null, ex);
        }   
>>>>>>> 34108e5797c6cfa73ea1866c9d6ebded31814075
    }
}
