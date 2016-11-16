/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.commands;

import org.mediaserver.communication.QueuePacket;
import org.mediaserver.interfaces.Command;

/**
 *
 * @author Tomek
 */
public class BroadcastSignalCommand implements Command{
    public void execute(QueuePacket data){
        System.out.println("Server :" + data.getSignal().getId());
    }
    
}
