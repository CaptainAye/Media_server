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
import org.mediaserver.interfaces.Signalizable;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mediaserver.communication.SignalSenderback;
import org.mediaserver.signals.BroadcastSignal;
import org.mediaserver.tools.Validator;
/**
 *
 * @author Tomek
 */
class SignalBroadcasterBack implements Runnable {
    private ArrayList<BroadcastSignal> broadcastList;
    private SignalSenderback sender;
    
    public void run()
    {
        
        while(true)
        {
            Iterator<BroadcastSignal> iter = broadcastList.iterator();
            while(iter.hasNext())
            {
                sender.send(iter.next());
            }
            try
            {
                Thread.sleep(1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            
        }
        
        
    }
    
    public SignalBroadcasterBack(SignalSenderback sender, ArrayList<BroadcastSignal> broadList)
    {
        this.sender = sender;
        broadcastList = new ArrayList<>();
        broadcastList = broadList;
    }
    
}
