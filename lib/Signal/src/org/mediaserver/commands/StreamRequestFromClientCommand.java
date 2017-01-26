/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.commands;

import java.nio.file.Paths;
import java.util.stream.Stream;
import org.mediaserver.communication.DedicatedSender;
import org.mediaserver.communication.QueuePacket;
import org.mediaserver.communication.StreamTaskQueue;
import org.mediaserver.files.FileType;
import org.mediaserver.interfaces.Command;
import org.mediaserver.lists.ServerSideClientList;
import org.mediaserver.signals.StreamRequestFromClientSignal;

/**
 *
 * @author Natalia
 */
public class StreamRequestFromClientCommand implements Command{
    
    //private void handleStream()
    
    public void execute(QueuePacket data, Integer callerId) {
        String fileName = ((StreamRequestFromClientSignal) data.getSignal()).getPath().toFile().getName();
        String path = ((StreamRequestFromClientSignal) data.getSignal()).getPath().toFile().getAbsolutePath();
        Integer clientPort = ((StreamRequestFromClientSignal) data.getSignal()).getClientPort();
        Integer hostClientId = ((StreamRequestFromClientSignal) data.getSignal()).getHostClientId();
        Integer id = data.getSignal().getId();
        
        StreamTaskQueue.QueueTask newTask = new StreamTaskQueue.QueueTask(id,hostClientId,clientPort, path,fileName );
        StreamTaskQueue.getSignalQueue().enqueue(newTask);
        System.out.println("Received request for:" + path + " located in Client: " +  hostClientId );
            
            
        
    }
    
}
