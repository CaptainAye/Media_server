/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.commands;

import java.io.IOException;
import java.net.Socket;
import org.mediaserver.app.Server;
import org.mediaserver.communication.ContentReceiver;
import org.mediaserver.communication.DedicatedSender;
import org.mediaserver.communication.QueuePacket;
import org.mediaserver.communication.StreamTaskQueue;
import org.mediaserver.interfaces.Command;
import org.mediaserver.signals.StreamRequestFromServerSignal;

/**
 *
 * @author Tomek
 */
public class CheckStreamTaskSignalCommand implements Command{
    //SERVER SIDE SIGNAL HANDLING
    public synchronized void execute(QueuePacket data,Integer callerId){
        System.out.println("checkStreamTaskSignalCommand executed");
        Integer callingClientId = data.getSignal().getId();
        if (StreamTaskQueue.getSignalQueue().size() == 0){
            return;
        }
        StreamTaskQueue.QueueTask task = StreamTaskQueue.getSignalQueue().dequeue();
        Integer targetClientId = task.getTargetClientId();
        if (callingClientId.equals(targetClientId)){
            System.out.println("CallerId correct");
            Socket clientSocket = data.getSocket();
            Integer receivePort = 49999; // TODO more sophisticated port selection
            try{
                
                Thread receiver = new Thread(new ContentReceiver(callingClientId, receivePort, task.getFileName()));
                
                receiver.start();
                Thread.sleep(500);
                DedicatedSender.getSender().send(clientSocket, new StreamRequestFromServerSignal(Server.getId(),receivePort, task.getContentPath()));
                //ContentReceiver.receiveContent(callingClientId, receivePort, task.getFileName());
                System.out.println("StreamRequestFromServerSignal sent");
            //    Send further
            }catch (IOException e){
                e.printStackTrace();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            
        }else{
            StreamTaskQueue.getSignalQueue().enqueue(task);
        }
        // Otworz port datagram do odebrania pliku
        // przeslij 
        
    }
    
}
