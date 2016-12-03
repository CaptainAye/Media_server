/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.signals;

import java.util.HashMap;
import org.mediaserver.interfaces.Signalizable;

/**
 *
 * @author Natalka
 */
public class AccessRequestSignal extends Signalizable{
    private HashMap<String,String> map;
    public AccessRequestSignal (Integer id)
    {
        setId(id);
    }
    
    public void setFilesForIndexing(HashMap<String,String> map){
        this.map.putAll(map);
    }
    
    public HashMap<String,String> getFilesToIndex(){
        return map;
    }
}
