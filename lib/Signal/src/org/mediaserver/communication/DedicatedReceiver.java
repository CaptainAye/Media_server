/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import org.mediaserver.interfaces.Signalizable;

/**
 *
 * @author Natalka
 */
public class DedicatedReceiver {
    
    private static DedicatedReceiver receiver;
    public static synchronized DedicatedReceiver getDedicatedReceiver(){
        if ( receiver == null){
            receiver = new DedicatedReceiver();
        }
        return receiver;
    }
   public synchronized void connectSocket(Socket socket){
        //new Thread(new ClientThread(socket)).start();
    }
}
