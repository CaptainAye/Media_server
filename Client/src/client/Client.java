/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.util.Scanner;
import org.mediaserver.communication.BroadcastReceiver;
import org.mediaserver.communication.DedicatedSender;
import org.mediaserver.communication.ServerReceiver;
import org.mediaserver.communication.SignalParser;
import org.mediaserver.communication.SignalReceiver;
import org.mediaserver.signals.BroadcastSignal;

/**
 *
 * @author Tomek
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BroadcastReceiver receiver = new BroadcastReceiver(10502); // port = 10500
        SignalParser parser = SignalParser.getParser();
        Thread parserThread = new Thread(parser);
        parserThread.start();
        Thread serverReceiverThread = new Thread(receiver);
        serverReceiverThread.start();
    }
    
}
