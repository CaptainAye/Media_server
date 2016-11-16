/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import org.mediaserver.interfaces.Signalizable;

/**
 *
 * @author Tomek
 */
public class DedicatedSender {
    private DedicatedSender() {
    }
    private static DedicatedSender send;
    public static synchronized DedicatedSender getSender(){
        if ( send == null){
            send = new DedicatedSender();
        }
        return send;
    }
    public synchronized void send (Socket socket,Signalizable signal) throws IOException {
        if (socket == null || signal == null){
            return;
        }
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(signal);
        
    }
    
}
