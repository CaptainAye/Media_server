/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.commands;

import java.nio.file.Path;
import org.mediaserver.communication.ContentSender;
import org.mediaserver.communication.QueuePacket;
import org.mediaserver.interfaces.Command;
import org.mediaserver.signals.StreamRequestFromClientSignal;
import org.mediaserver.signals.StreamRequestFromServerSignal;

/**
 *
 * @author Natalia
 */
public class StreamRequestFromServerCommand implements Command{
    
    @Override
    public void execute(QueuePacket data, Integer callerId) {
        
        String remoteIp = data.getSocket().getInetAddress().getHostAddress().toString(); // TODO check if this returns remote address
        StreamRequestFromServerSignal signal = (StreamRequestFromServerSignal) data.getSignal(); 
        
        Integer portForFile = signal.getServerPort();
        Path file = signal.getPath();
        System.out.println("sending content file: " + file.toString() + " to ip: " + remoteIp + "on port: " + portForFile);
        ContentSender.send(remoteIp,portForFile ,file );
    }
    
}
