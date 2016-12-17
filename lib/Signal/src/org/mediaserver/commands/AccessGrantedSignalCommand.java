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
public class AccessGrantedSignalCommand implements Command {
    
    public void execute(QueuePacket data, Integer callerId){
        
    }
    
}
