/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.Socket;
import org.mediaserver.interfaces.Signalizable;
import org.mediaserver.interfaces.Socketable;

/**
 *
 * @author Tomek
 */
public class TCPSocket implements Socketable {
    private Socket socket;
    
    public TCPSocket(Socket socket)
    {
        this.socket = socket;
    }
    
    public Boolean isClosed(){
        return socket.isClosed();
    }
    
    public void open(Integer port){
    } 
    public void close(){
        try{
        socket.close();
        } catch( IOException e){
            e.printStackTrace();
        }
    }
    
    public void send(DatagramPacket data){
    }
    
    public Signalizable receive() throws IOException, ClassNotFoundException{
        ObjectInputStream receiverStream = new ObjectInputStream(socket.getInputStream());
        Signalizable signal = (Signalizable) receiverStream.readObject();
        return signal;
    }
    
}
