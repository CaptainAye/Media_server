/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.communication;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.sound.sampled.Port;
import org.mediaserver.interfaces.Signalizable;
import org.mediaserver.interfaces.Socketable;

/**
 *
 * @author Tomek
 */
public class SignalReceiver{

    
    private static SignalReceiver instance = new SignalReceiver();
    
    private SignalReceiver(){};
    public static synchronized SignalReceiver getSignalReceiver(){
        return instance;
    }
    public class ClientThread implements Runnable{
        
        private Socket socket;
        
        public ClientThread(Socket socket){
            this.socket = socket;
        }
        public void run(){
            try{
                if (socket == null)
                {
                    throw new NullPointerException();
                }
                ObjectInputStream receiverStream = new ObjectInputStream(socket.getInputStream());
                while(!socket.isClosed()){
                    Signalizable signal = (Signalizable) receiverStream.readObject();
                    QueuePacket message = new QueuePacket(socket, signal);
                    SignalQueue.getSignalQueue().enqueue(message);      
                }
            } catch (IOException e)
            {
                //e.printStackTrace();
                //TODO handle IOException in run() of ClientThread class in SignalReceiver
            } catch (ClassNotFoundException e){
                e.printStackTrace();
                //TODO handle ClassNotFoundException in run() of ClientThread class in SignalReceiver
            }
            
        }
        
    }
    
    public synchronized void connectSocket(Socket socket){
        new Thread(new ClientThread(socket)).start();
    }
    
}
