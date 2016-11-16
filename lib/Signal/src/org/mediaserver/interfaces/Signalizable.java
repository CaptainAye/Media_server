/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.interfaces;
import java.io.Serializable;
/**
 *
 * @author Tomek
 */
public abstract class Signalizable implements Serializable {
    protected String ipAddress;
    protected Integer sourcePort;
    protected Integer destinationPort;
    protected String localIpAddress;
    protected Boolean terminate = false;
    protected Integer uniqueId;
    
    public Boolean getTerminate()
    {
        return terminate;
    }
    public void setTerminate(Boolean on)
    {
        terminate = on;
    }
    public void setIp(String ip)
    {
        ipAddress = ip;
    }
    
    public void setPorts(Integer srcPort, Integer destPort)
    {
        sourcePort = srcPort;
        destinationPort = destPort;
        
    }
    
    public void setId(Integer id){
        uniqueId = id;
    }
    
    public String getIp(){
        return ipAddress;
    }
    public Integer getSourcePort(){
        return sourcePort;
    }
    
    public Integer getDestinationPort(){
        return destinationPort;
    }
    
    public void setLocalIp(String ip){
        localIpAddress = ip;
    }
    
    public String getLocalIp(){
        return localIpAddress;
    }
    public Integer getId(){
        return uniqueId;
    }
    
    
}
