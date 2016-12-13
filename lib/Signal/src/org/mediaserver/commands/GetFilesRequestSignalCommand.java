/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.commands;

import org.mediaserver.communication.QueuePacket;
import org.mediaserver.interfaces.Command;

/**
 *
 * @author Tomek
 */
public class GetFilesRequestSignalCommand implements Command {
    public void execute(QueuePacket data, Integer callerId){
        System.out.println("Get files request received");
        try{
            Thread.currentThread().sleep(10000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    
}
