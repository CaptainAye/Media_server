/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.communication;

import java.util.HashMap;
import org.mediaserver.commands.AccessRequestCommand;
import org.mediaserver.commands.BroadcastSignalCommand;
import org.mediaserver.exceptions.KeyNotFoundException;
import org.mediaserver.exceptions.NotASignalizableTypeException;
import org.mediaserver.interfaces.Command;
import org.mediaserver.signals.AccessRequest;
import org.mediaserver.signals.BroadcastSignal;

/**
 *
 * @author Tomek
 */
public class SignalParser implements Runnable{
    private static SignalParser parser;
    private HashMap<Class, Command> signalHandlerMap;
    
    
    private SignalParser(){
            signalHandlerMap = new HashMap<>();
            registerSignals();
    }
    
    public static synchronized SignalParser getParser(){
        if (parser == null){
            parser = new SignalParser();
        }
        return parser;
    }
    
    public void run () {
        while (true){
            QueuePacket data = SignalQueue.getSignalQueue().dequeue();
            try{
            parse(data);
            } catch (KeyNotFoundException e){
                e.printStackTrace();
            }
        }
    }
    private void registerSignals(){
        try{
            register(BroadcastSignal.class,new BroadcastSignalCommand());
            //REGISTER YOUR SIGNALS
            
            register(AccessRequest.class, new AccessRequestCommand());
            
        } catch (NotASignalizableTypeException e){
            e.printStackTrace();
            //TODO handle not a signalizable registration in signalParser
        }
    }
    private void register(Class name,Command signalHandler) throws NotASignalizableTypeException{
        try{
            System.out.println(name);
            if (Class.forName("org.mediaserver.interfaces.Signalizable").isAssignableFrom(name)){ // NOT SURE if isAssignableFrom does what it should
                signalHandlerMap.put(name, signalHandler);
        }
        else
        {
            throw new NotASignalizableTypeException();
        }
            }catch(ClassNotFoundException e){
                e.printStackTrace();
                //TODO handle classNotFound in register method of signalParser
            }
        }
    public void parse(QueuePacket data) throws KeyNotFoundException
    {
        if (signalHandlerMap.containsKey(data.getSignal().getClass())){
            signalHandlerMap.get(data.getSignal().getClass()).execute(data);
        }
        else{
            throw new KeyNotFoundException();
        }
        
    }
}
