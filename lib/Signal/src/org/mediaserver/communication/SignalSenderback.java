/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.communication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import org.mediaserver.interfaces.Signalizable;

/**
 *
 * @author Tomek
 */
public class SignalSenderback {
    
    DatagramSocket socket;
    DatagramPacket signalToBeSent;
    
    public synchronized Boolean send(Signalizable signal)
    {   
        try {
            InetAddress group = InetAddress.getByName(signal.getIp());
            //Object serialization
            final ByteArrayOutputStream buf = new ByteArrayOutputStream();
            final ObjectOutputStream oos = new ObjectOutputStream(buf);
            oos.writeObject(signal);
            final byte[] data = buf.toByteArray();
            
            //Preparing datagram for sending
            socket = new DatagramSocket(signal.getSourcePort());
            signalToBeSent = new DatagramPacket(data,data.length,group,signal.getDestinationPort());
            System.out.println("Packet sent");
            socket.setBroadcast(true);
            socket.send(signalToBeSent);
            socket.close();
        } catch (UnknownHostException e)
        {
            e.printStackTrace();
            return false;
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
            
        }
        return true;
    }
    
}
