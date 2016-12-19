/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.app;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import org.mediaserver.communication.ContentSender;
import org.mediaserver.communication.ServerReceiver;
import org.mediaserver.communication.SignalBroadcaster;
import org.mediaserver.communication.SignalParser;
import org.mediaserver.exceptions.BroadcastSignalNotPresentException;
import org.mediaserver.exceptions.IncorrectCommandException;
import org.mediaserver.exceptions.IncorrectIPFormatException;
import org.mediaserver.generator.IdentificationNumberGenerator;
import org.mediaserver.interfaces.Signalizable;
import org.mediaserver.signals.BroadcastSignal;
import org.mediaserver.tools.Validator;

/**
 *
 * @author Tomek
 */
public class Server {
    private ArrayList<BroadcastSignal> broadcastList = new ArrayList<>();
    private static Integer serverId = IdentificationNumberGenerator.generateId();
    private SignalBroadcaster sigBroad;
    private Integer dedicatedPort = 10500;
    private Integer clientPort = 10502;
    public static Integer getId(){
        return serverId;
    }
    private void readNetworkInterfaces(){
        Enumeration<NetworkInterface> nets = null;
        try{
            nets = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e){
            e.printStackTrace();
            System.exit(-1);
        }
        for (NetworkInterface netint : Collections.list(nets)){
            for(InterfaceAddress interfaceAddr : netint.getInterfaceAddresses()){
                if (interfaceAddr.getBroadcast() != null && interfaceAddr.getAddress() != null){
                    String broadcastAddress = interfaceAddr.getBroadcast().getHostAddress();
                    String localAddress = interfaceAddr.getAddress().getHostAddress();
                    broadcastList.add(new BroadcastSignal(broadcastAddress,localAddress,dedicatedPort,clientPort,serverId));
                }
            }
            
        }
        
    }

    public Server(){
        try {
            //readConfigFile();
            readNetworkInterfaces();
            sigBroad = new SignalBroadcaster(broadcastList,dedicatedPort);
            ServerReceiver receiver = new ServerReceiver(dedicatedPort); // port = 10500
            
            SignalParser parser = SignalParser.getParser();
            Thread parserThread = new Thread(parser);
            parserThread.start();
            
            Thread signalBroadcasterThread = new Thread(sigBroad);
            Thread serverReceiverThread = new Thread(receiver);
            
            serverReceiverThread.start();
            signalBroadcasterThread.start();
        }
        catch(BroadcastSignalNotPresentException e){
            e.printStackTrace();
            System.exit(-1);
        }
        /*catch(IncorrectCommandException e){
            e.printStackTrace();
            System.exit(-1);
        }
        catch(IncorrectIPFormatException e){
            e.printStackTrace();
            System.exit(-1);
        }*/
    } 
    public static void main(String[] args) {
        Path file = Paths.get("Zwierzogrod.avi");
        //ContentSender.send("192.168.0.115", 25000, file);
        //ContentSender.streamForwardVideo("192.168.0.115", 8555, file );
        Server instance = new Server();
        
    }
    
}
