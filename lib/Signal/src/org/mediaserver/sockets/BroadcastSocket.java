/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.sockets;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.NoRouteToHostException;
import java.net.SocketException;
import org.mediaserver.interfaces.Signalizable;
import org.mediaserver.interfaces.Socketable;

/**
 *
 * @author Tomek
 */
public class BroadcastSocket implements Socketable{
    private DatagramSocket socket;
    public BroadcastSocket(Integer port){
        try
        {
        socket = new DatagramSocket(port);
        } catch(SocketException e)
        {
            e.printStackTrace();
            //TODO handle socket already bind exception
            //System.exit(-1);
        }
    }
    public synchronized void open(Integer port){
        try
        {
        socket = new DatagramSocket(port);
        } catch(SocketException e)
        {
            e.printStackTrace();
            //TODO handle no socket exception
            System.exit(-1);
        }
    }
    
    public synchronized void send(DatagramPacket data)
    {
        try {
        socket.send(data);
        } catch (IOException e) {
            e.printStackTrace(); 
            //TODO IOException handling
        }
    }
    
    public synchronized void close()
    {
        socket.close();
    }
    
    public synchronized Boolean isClosed()
    {
        return socket.isClosed();
    }
    
    public Signalizable receive() throws IOException, ClassNotFoundException{
        Signalizable signal = null;
        try{
         byte[] receiveData = new byte[1024];
         DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
         socket.receive(receivePacket);
         ByteArrayInputStream baos = new ByteArrayInputStream(receiveData);
         ObjectInputStream oos = new ObjectInputStream(baos);
         signal =(Signalizable) oos.readObject();
        }
        finally{
            return signal;
        }
    }
    
    
}
