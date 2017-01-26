/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.communication;

import java.util.HashMap;
import org.mediaserver.commands.AccessGrantedSignalCommand;
import org.mediaserver.commands.AccessRequestSignalCommand;
import org.mediaserver.commands.GetFilesResponseSignalCommand;
import org.mediaserver.commands.BroadcastSignalCommand;
import org.mediaserver.commands.CheckStreamTaskSignalCommand;
import org.mediaserver.commands.GetFilesRequestSignalCommand;
import org.mediaserver.commands.StreamRequestFromClientCommand;
import org.mediaserver.commands.StreamRequestFromServerCommand;
import org.mediaserver.commands.StreamResponseFromClientCommand;
import org.mediaserver.commands.StreamResponseFromServerCommand;
import org.mediaserver.exceptions.KeyNotFoundException;
import org.mediaserver.exceptions.NotASignalizableTypeException;
import org.mediaserver.interfaces.Command;
import org.mediaserver.signals.AccessGrantedSignal;
import org.mediaserver.signals.AccessRequestSignal;
import org.mediaserver.signals.GetFilesResponseSignal;
import org.mediaserver.signals.BroadcastSignal;
import org.mediaserver.signals.CheckStreamTaskSignal;
import org.mediaserver.signals.GetFilesRequestSignal;
import org.mediaserver.signals.StreamRequestFromClientSignal;
import org.mediaserver.signals.StreamRequestFromServerSignal;
import org.mediaserver.signals.StreamResponseFromClientSignal;
import org.mediaserver.signals.StreamResponseFromServerSignal;

/**
 *
 * @author Tomek
 */
public class SignalParser implements Runnable{
    private static SignalParser parser;
    private Integer callerId;
    
    private HashMap<Class, Command> signalHandlerMap;
    
    
    private SignalParser(){
            signalHandlerMap = new HashMap<>();
            registerSignals();
            callerId = null;
    }
    
    public void setCallerId(Integer callerId){
        this.callerId = callerId;
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
            register(AccessRequestSignal.class,new AccessRequestSignalCommand());
            register(GetFilesResponseSignal.class, new GetFilesResponseSignalCommand());
            register(AccessGrantedSignal.class, new AccessGrantedSignalCommand());
            register(GetFilesRequestSignal.class,new GetFilesRequestSignalCommand());
            register(StreamRequestFromClientSignal.class,new StreamRequestFromClientCommand());
            register(StreamRequestFromServerSignal.class, new StreamRequestFromServerCommand());
            register(StreamResponseFromClientSignal.class, new StreamResponseFromClientCommand());
            register(StreamResponseFromServerSignal.class, new StreamResponseFromServerCommand());
            register(CheckStreamTaskSignal.class, new CheckStreamTaskSignalCommand());
            //REGISTER YOUR SIGNALS
            
        } catch (NotASignalizableTypeException e){
            e.printStackTrace();
            //TODO handle not a signalizable registration in signalParser
        }
    }
    private void register(Class name,Command signalHandler) throws NotASignalizableTypeException{
        try{
            System.out.println(name);
            if (Class.forName("org.mediaserver.interfaces.Signalizable").isAssignableFrom(name)){ 
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
            signalHandlerMap.get(data.getSignal().getClass()).execute(data,callerId);
        }
        else{
            throw new KeyNotFoundException();
        }
        
    }
}
