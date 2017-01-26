/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.signals;

import java.net.DatagramSocket;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.mediaserver.interfaces.Signalizable;

/**
 *
 * @author Tomek
 */
public class StreamRequestFromServerSignal extends Signalizable {
    private final String streamFile;
    private final Integer port;
    
    public StreamRequestFromServerSignal(Integer requestorId,Integer port, String streamFile){
        setId(requestorId);
        this.port = port;
        this.streamFile = streamFile; 
    }
    
    public Integer getServerPort(){
        return port;
    }
    
    
    public Path getPath(){
        return Paths.get(streamFile);
    }
}
