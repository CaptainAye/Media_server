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
 * @author Tomek
 */
public class AccessGrantedSignal extends Signalizable{
    
    private HashMap<Path,Integer> indexedFilesMap;
    
    public AccessGrantedSignal(Integer id, HashMap<Path,Integer> map){
        setId(id);
        indexedFilesMap = map;
    }
    //tibo
    public HashMap<Path,Integer> getIndexedFilesMap(){
        return indexedFilesMap;
    }
}
