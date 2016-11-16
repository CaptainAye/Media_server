/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.communication;

import java.net.Socket;
import java.util.LinkedList;
import org.mediaserver.interfaces.Signalizable;

/**
 *
 * @author Tomek
 */
public class SignalQueue{
    
    private static SignalQueue signalQueue = new SignalQueue();
    private LinkedList<QueuePacket> queue = new LinkedList<>();
    private SignalQueue(){
    }
    public void enqueue(QueuePacket data){
        queue.push(data);
        synchronized(this){
            if (queue.size() == 1){
                notifyAll();
            }
        }
    }
    
    public QueuePacket dequeue(){
        synchronized(this){
            while(queue.size() == 0){
                try{
                wait();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            
            return queue.removeLast();
        }
    }
    
    public static synchronized SignalQueue getSignalQueue(){
        return signalQueue;
    }
    
}
