/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.commands;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import org.mediaserver.communication.DedicatedSender;
import org.mediaserver.communication.QueuePacket;
import org.mediaserver.interfaces.Command;
import org.mediaserver.lists.ServerSideClientList;
import org.mediaserver.signals.AccessGrantedSignal;
import org.mediaserver.signals.GetFilesRequestSignal;
import org.mediaserver.signals.GetFilesResponseSignal;
import client.Client;

/**
 *
 * @author Tomek
 */
public class AccessGrantedSignalCommand implements Command {
    
    private HashMap<Path,Integer> indexedFilesMap;
  
    public void execute(QueuePacket data, Integer callerId){
        
        Integer clientID = data.getSignal().getId();
        
        AccessGrantedSignal accessgranted = (AccessGrantedSignal) data.getSignal();
        
        indexedFilesMap = accessgranted.getIndexedFilesMap();
        
        //GetFilesResponseSignal getFileResponse = (GetFilesResponseSignal) data.getSignal();
        
        //getFileResponse.setFilesForIndexing(accessgranted.getIndexedFilesMap());
        
        Client.addSharedFiles(clientID, indexedFilesMap);
                
    }
    
}
