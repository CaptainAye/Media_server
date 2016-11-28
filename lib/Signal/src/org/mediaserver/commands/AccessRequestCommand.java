/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.commands;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mediaserver.communication.QueuePacket;
import org.mediaserver.interfaces.Command;

/**
 *
 * @author Natalka
 */
public class AccessRequestCommand implements Command {
    
    private Integer uniqueID;
    private PrintWriter save;
    public void execute(QueuePacket data) { 
        //generate client's uniqueID
        try{
            uniqueID=data.getSignal().getId();
            save = new PrintWriter("clientID.txt");
            save.println(uniqueID);
            save.close();
            //System.out.println("uniqueID " + uniqueID);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AccessRequestCommand.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
