/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.signals;

import java.net.DatagramSocket;
import java.nio.file.Path;
import org.mediaserver.interfaces.Signalizable;

/**
 *
 * @author Tomek
 */
public class StreamRequestFromServerSignal extends Signalizable {
    private Path streamFile;
    
    public StreamRequestFromServerSignal(Integer requestorId){
        setId(requestorId);
    }
    
    public void setPath(Path streamFile){
        this.streamFile = streamFile;
    }
    
    public Path getPath(){
        return streamFile;
    }
}
