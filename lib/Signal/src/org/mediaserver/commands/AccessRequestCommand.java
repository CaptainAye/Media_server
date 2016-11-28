/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.commands;

import com.sun.corba.se.spi.activation.Server;
import java.io.IOException;
import org.mediaserver.communication.DedicatedSender;
import org.mediaserver.communication.QueuePacket;
import org.mediaserver.interfaces.Command;
import org.mediaserver.queues.ClientList;
import org.mediaserver.signals.AccessGrantedSignal;

/**
 *
 * @author Natalka
 */
public class AccessRequestCommand implements Command {
    
    //przesyłanie drzewa z kompa klienta
    public void execute(QueuePacket data) {
        System.out.println("Access request has been received from client no " + data.getSignal().getId() );
        try{
        DedicatedSender.getSender().send(data.getSocket(), new AccessGrantedSignal());
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    
}
