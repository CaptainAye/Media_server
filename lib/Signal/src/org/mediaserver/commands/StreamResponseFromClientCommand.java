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
 * @author Natalia
 */
public class StreamResponseFromClientCommand implements Command{

    @Override
    public void execute(QueuePacket data, Integer callerId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
