/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.signals;

import java.nio.file.Path;
import java.util.HashMap;
import org.mediaserver.interfaces.Signalizable;

/**
 *
 * @author Natalka
 */
public class GetFilesResponseSignal extends Signalizable{
    private HashMap<Path,String> map;
    public GetFilesResponseSignal (Integer id)
    {
        setId(id);
    }
    
    public void setFilesForIndexing(HashMap<Path,String> map){
        this.map.putAll(map);
    }
    
    public HashMap<Path,String> getFilesToIndex(){
        return map;
    }
}