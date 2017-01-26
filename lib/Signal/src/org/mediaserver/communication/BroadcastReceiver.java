/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.communication;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import org.mediaserver.interfaces.Signalizable;

/**
 *
 * @author Tomek
 */
public class BroadcastReceiver implements Runnable {
    private Integer port;
    private Signalizable signal = null;
    
    public BroadcastReceiver(Integer commPort){
        port = commPort;
    }
    
     public void run(){
        try{
        DatagramSocket socket = new DatagramSocket(port);
        byte[] receiveData = new byte[1024];
        while(true){
            //System.out.println("Listening for packets");
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
            ObjectInputStream oos = new ObjectInputStream(bais);
            signal =(Signalizable) oos.readObject();
            SignalQueue.getSignalQueue().enqueue(new QueuePacket(null,signal));
        }
        } catch (SocketException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        
    }
    
}
