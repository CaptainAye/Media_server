/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.communication;

import java.net.Socket;
import org.mediaserver.interfaces.Signalizable;
import org.mediaserver.interfaces.Socketable;

/**
 *
 * @author Tomek
 */
public class QueuePacket {
    private Signalizable signal;
    private Socket socket;
    
    public QueuePacket(Socket socket, Signalizable signal){
        this.socket = socket;
        this.signal = signal;
    }
    
    public void set(Socket socket, Signalizable signal){
        this.socket = socket;
        this.signal = signal;
    }
    
    public Signalizable getSignal(){
        return signal;
    }
    public Socket getSocket(){
        return socket;
    }
    
}
