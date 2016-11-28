/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.commands;


import client.FileSearcher;
import java.nio.file.Path;
import java.util.HashMap;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mediaserver.communication.DedicatedSender;

import org.mediaserver.communication.QueuePacket;
import org.mediaserver.communication.SignalReceiver;
import org.mediaserver.interfaces.Command;
import org.mediaserver.signals.AccessGrantedSignal;
import org.mediaserver.signals.AccessRequest;
import org.mediaserver.sockets.BroadcastSocket;

/**
 *
 * @author Tomek
 */
public class BroadcastSignalCommand implements Command{

    private Boolean searched = false;
    private HashMap<String,Path> filesMap = new HashMap<String,Path>();
    private Socket broadcastSocket;
    private ArrayList<String> servers;
    private Boolean sent = false;
    public synchronized void execute(QueuePacket data){
        try{
            if (!sent){
                String myIP = "192.168.0.18";
                Socket socket = new Socket(myIP,10500);
                DedicatedSender.getSender().send(socket, new AccessRequest(1)); //TODO Client ID - change it
                sent = true;
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Listen to Host: " + data.getSignal().getLocalIp()+ " on port: " + data.getSignal().getSourcePort());
        if (searched == false){
            filesMap = FileSearcher.searchDirectories();
            searched = true;
        }
    }
}

