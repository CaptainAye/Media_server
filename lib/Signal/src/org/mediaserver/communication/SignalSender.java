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
import java.net.SocketException;
import java.net.UnknownHostException;
import org.mediaserver.exceptions.WrongSocketPortException;
import org.mediaserver.interfaces.Signalizable;
import org.mediaserver.interfaces.Socketable;

/**
 *
 * @author Tomek
 */
public abstract class SignalSender {
    
    //private static DatagramSocket socket;
    protected static Integer socketPort;
    protected Socketable socket; 
    //private static Sender send = Sender.getSender();
    
    public static void setPort(Integer port){
        socketPort = port;        
    }
    public void send(Signalizable signal)throws WrongSocketPortException, NullPointerException{
        if (socket == null)
        {
            throw new NullPointerException();
        }
        try {
            if (socketPort == null || socketPort < 1 || socketPort > 49151)
            {
                throw new WrongSocketPortException();
            }
        InetAddress group = InetAddress.getByName(signal.getIp());
        //Object serialization
        final ByteArrayOutputStream buf = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(buf);
        oos.writeObject(signal);
        final byte[] data = buf.toByteArray();
        DatagramPacket signalToBeSent = new DatagramPacket(data,data.length,group,signal.getDestinationPort());
        if(socket.isClosed())
        {
            socket.open(socketPort);
        }
        socket.send(signalToBeSent);
        } catch (UnknownHostException e)
        {
            e.printStackTrace();
            //TODO set default behavior for unknown host
        } catch (IOException e)
        {
            e.printStackTrace();
            //TODO set default behavior for unknown host
        }
        

        
    }
    
}
