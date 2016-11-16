/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 * @author Tomek
 */
public class ClientReceiver implements Runnable {
    public void run(){
        try{
        DatagramSocket socket = new DatagramSocket(10500);
        byte[] receiveData = new byte[8];
        while(true){
            System.out.println("Start listening");
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            System.out.println("Signal received");
        }
        } catch (SocketException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        
    }
    
    
    
}
