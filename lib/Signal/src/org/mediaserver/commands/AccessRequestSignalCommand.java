/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.commands;

import java.io.IOException;
import org.mediaserver.communication.DedicatedSender;
import org.mediaserver.communication.QueuePacket;
import org.mediaserver.interfaces.Command;
import org.mediaserver.lists.ServerSideClientList;
import org.mediaserver.signals.AccessGrantedSignal;
import org.mediaserver.signals.AccessRequestSignal;

/**
 *
 * @author Natalka
 */
public class AccessRequestSignalCommand implements Command {
    private ServerSideClientList clientList;
    private AccessRequestSignal signal;
    private Boolean ClientIsAllowed(){
        if (signal.getFilesToIndex() != null){
            return true;
        }
        return false;
    }
    //przesyłanie drzewa z kompa klienta
    public void execute(QueuePacket data,Integer callerId) {
        signal =(AccessRequestSignal) data.getSignal();
        Integer clientId = signal.getId();
        clientList = ServerSideClientList.getClientList();
        System.out.println("Access request has been received from client no " + clientId + " from ip: " + data.getSocket().getInetAddress().getHostAddress().toString() );
        if (ClientIsAllowed()){
            if (!clientList.clientExists(clientId)){
                clientList.add(signal.getFilesToIndex(), clientId);
            }
            try{
            DedicatedSender.getSender().send(data.getSocket(), new AccessGrantedSignal());
            } catch(IOException e){
                e.printStackTrace();
            }
        }
        else { //TODO Client sent no files to index 
        }
    
    }
}