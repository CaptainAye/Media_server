/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.commands;


import org.mediaserver.communication.FileSearcher;
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
import org.mediaserver.lists.ClientSideServerList;
import org.mediaserver.lists.ServerSideClientList;
import org.mediaserver.signals.AccessGrantedSignal;
import org.mediaserver.signals.AccessRequestSignal;
import org.mediaserver.signals.BroadcastSignal;
import org.mediaserver.sockets.BroadcastSocket;

/**
 *
 * @author Tomek
 */
public class BroadcastSignalCommand implements Command{

    private Boolean searched = false;
    private HashMap<Path, String> filesMap = new HashMap<Path, String>();
    private Socket broadcastSocket;
    private ArrayList<String> servers;
    private Boolean sent = false;
    public synchronized void execute(QueuePacket data){
        
        BroadcastSignal signal = (BroadcastSignal) data.getSignal();
        ClientSideServerList serverList = ClientSideServerList.getClientSideServerList();
        
        if (!serverList.serverExists(signal.getId())) // if the client receives server for the first time
        {
            serverList.addServer(signal.getLocalIp(), signal.getSourcePort(), signal.getId()); // localIp is localIp of part sending the signal - in the case of broadcast, sending part is server. The source port is the port from which the signal was sent, to the server port
        }
       /* try{
            if (!sent){
                Socket socket = new Socket(data.getSignal().getLocalIp(),data.getSignal().getSourcePort());
                DedicatedSender.getSender().send(socket, new AccessRequestSignal(1)); //TODO Client ID - change it
                sent = true;
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Listen to Host: " + data.getSignal().getLocalIp()+ " on port: " + data.getSignal().getSourcePort());
        if (searched == false){
            filesMap = FileSearcher.searchDirectories();
            searched = true;
        }*/
    }
}

