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
import org.mediaserver.signals.GetFilesRequestSignal;

/**
 *
 * @author Tomek
 */
public class AccessRequestSignalCommand implements Command{
    
    public void execute(QueuePacket data, Integer callerId){
        System.out.println("Access request received");
        /*try{
            Thread.sleep(5000);
        } catch(InterruptedException e){
                e.printStackTrace();
                }*/
        //TODO Send information signal when no id is set for client
        Integer clientId = data.getSignal().getId();
        try{
        if(ServerSideClientList.getClientList().clientExists(clientId)){
            System.out.println("Server sent AccessGrantedSignal");
            DedicatedSender.getSender().send(data.getSocket(), new AccessGrantedSignal(callerId));
        }
        else{
            System.out.println("Server sent GetFilesRequestSignal");
            DedicatedSender.getSender().send(data.getSocket(), new GetFilesRequestSignal(callerId));
        }
        } catch (IOException e){
            e.printStackTrace();
            
        }
        
        
        
    }
    
}
