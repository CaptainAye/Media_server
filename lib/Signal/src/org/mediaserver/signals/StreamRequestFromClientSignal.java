/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.signals;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.mediaserver.interfaces.Signalizable;

/**
 *
 * @author Tomek
 */
public class StreamRequestFromClientSignal extends Signalizable {
    private final String streamFile;
    private final Integer hostClientId;
    private final Integer clientPort;
    
    public StreamRequestFromClientSignal(Integer requestorId,Integer hostClientId, Integer clientPort, Path streamFile){   
        setId(requestorId);
        this.streamFile = streamFile.toString();
        this.hostClientId = hostClientId;
        this.clientPort = clientPort;
    }
    //The client which physically owns a file
    public Integer getHostClientId(){
        return hostClientId;
    }
    
    public Integer getClientPort(){
        return clientPort;
    }
    
    public Path getPath(){
        return Paths.get(streamFile);
    }
}
