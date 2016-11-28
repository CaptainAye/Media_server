/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.communication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.mediaserver.exceptions.BroadcastSignalNotPresentException;
import org.mediaserver.exceptions.WrongSocketPortException;
import org.mediaserver.signals.BroadcastSignal;
import org.mediaserver.sockets.BroadcastSocket;

/**
 *
 * @author Tomek
 */
public class SignalBroadcaster extends SignalSender implements Runnable {
    private ArrayList<BroadcastSignal> broadcastList;
    
    public SignalBroadcaster(ArrayList<BroadcastSignal> list, Integer port) throws BroadcastSignalNotPresentException{
        if (list.size() == 0 || list == null) {
            throw new BroadcastSignalNotPresentException();
        }
        broadcastList = new ArrayList<>(list);
        setPort(port);
        socket = new BroadcastSocket(socketPort);
    }
    public void run(){
        int i = 0;
        while(true)
        {
            Iterator<BroadcastSignal> iter = broadcastList.iterator();
            while(iter.hasNext()){
                try {
                    BroadcastSignal sig = iter.next();
                    System.out.println("Iteration: " + i + " packet sent");
                    System.out.println("Packet sent on broadcast IP: " + sig.getIp() + " from server ip: " + sig.getLocalIp() + " on port: " + sig.getDestinationPort());
                    i++;
                    send(sig);
                } catch (WrongSocketPortException e){
                    e.printStackTrace();
                    //TODO write default unknown host exception handling
                }
                catch(NullPointerException e ){
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            
        }
    }
}
