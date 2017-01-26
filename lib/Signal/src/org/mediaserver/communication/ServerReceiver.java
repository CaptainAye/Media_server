/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.communication;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import org.mediaserver.sockets.BroadcastSocket;

/**
 *
 * @author Tomek
 */
public class ServerReceiver implements Runnable {
    
    private ServerSocket socket;
    //private DatagramSocket datagramSocket;
    private Integer port;
    
    public ServerReceiver(Integer communicationPort){
        port = communicationPort;
        try{
        socket = new ServerSocket(port);
        //datagramSocket = new DatagramSocket(port);
        } catch (IOException e)
        {
            e.printStackTrace();
            //TODO handle IOException in ServerReceiver constructor
        }
    }
    
    public void run(){
        //Thread parser = new Thread(SignalParser.getParser());
        //parser.start();
        
        while(true){
            try{
                // Opens dedicated communication through TCP
                //System.out.println("Opening server listening channel");
                Socket clientSocket = socket.accept();
                SignalReceiver.getSignalReceiver().connectSocket(clientSocket);
            } catch (IOException e){
                e.printStackTrace();
                //Handle IOException in run() of ServerReceiver
            }
        }
    }
    
    
    
}
