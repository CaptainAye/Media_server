/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.commands;

import client.FileSearcher;
import java.nio.file.Path;
import java.util.HashMap;
import org.mediaserver.communication.QueuePacket;
import org.mediaserver.interfaces.Command;

/**
 *
 * @author Tomek
 */
public class BroadcastSignalCommand implements Command{
    private Boolean searched = false;
    private HashMap<String,Path> filesMap = new HashMap<String,Path>();
    public synchronized void execute(QueuePacket data){
        System.out.println("Listen to Host: " + data.getSignal().getLocalIp()+ " on port: " + data.getSignal().getSourcePort());
        if (searched == false){
            filesMap = FileSearcher.searchDirectories();
            searched = true;
        }
    }
    
}
