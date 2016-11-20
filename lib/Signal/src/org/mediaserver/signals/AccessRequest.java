/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.signals;

import org.mediaserver.interfaces.Signalizable;

/**
 *
 * @author Natalka
 */
public class AccessRequest extends Signalizable{
    public AccessRequest (String ip,String ipLocal, Integer srcPort, Integer destPort,Integer id)
    {
        setLocalIp(ipLocal); // setting IP of client in given group
        setIp(ip); // setting ip subnet mask
        setPorts(srcPort, destPort);
        setId(id); //sending client's unique id
        
    }
}
