/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.interfaces;

import java.io.IOException;
import java.net.DatagramPacket;

/**
 *
 * @author Tomek
 */
public interface Socketable {
    public Boolean isClosed();
    public void open(Integer port);
    public void close();
    public void send(DatagramPacket data);
    public Signalizable receive() throws IOException, ClassNotFoundException;
    
}
