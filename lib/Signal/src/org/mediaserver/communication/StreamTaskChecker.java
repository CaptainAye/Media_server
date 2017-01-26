/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.communication;

import client.Client;
import java.io.IOException;
import java.net.Socket;
import org.mediaserver.signals.CheckStreamTaskSignal;
import org.mediaserver.signals.GetFilesResponseSignal;

/**
 *
 * @author Tomek
 */
public class StreamTaskChecker implements Runnable {
    private final String ip;
    private final Integer port;
    public StreamTaskChecker(String serverIp, Integer serverPort){
        ip = serverIp;
        port = serverPort;
    }
    public void run(){
        while(true){
            try{
                Socket socket = new Socket(ip, port);
                    SignalReceiver.getSignalReceiver().connectSocket(socket);
                    DedicatedSender.getSender().send(socket, new CheckStreamTaskSignal(Client.getId()));
                Thread.sleep(5000);
            } catch ( InterruptedException | IOException e){
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }
    
}
