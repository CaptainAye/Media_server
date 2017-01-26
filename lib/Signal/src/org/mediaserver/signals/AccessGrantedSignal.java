/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.signals;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import org.mediaserver.interfaces.Signalizable;
/**
 *
 * @author Tomek
 */
public class AccessGrantedSignal extends Signalizable{
    
    private HashMap<String,Integer> indexedFilesMap;
    
    public AccessGrantedSignal(Integer id, HashMap<Path,Integer> map){
        HashMap<String,Integer> serializableMap = new HashMap<>();
        setId(id);
        for(Path path : map.keySet()){
            Integer client = map.get(path);
            serializableMap.put(path.toString(), client);
        }
        indexedFilesMap = serializableMap;
    }
    //tibo
    public HashMap<Path,Integer> getIndexedFilesMap(){
        HashMap<Path,Integer> indexedPathMap = new HashMap<>();
        for (String path : indexedFilesMap.keySet()){
            Integer client = indexedFilesMap.get(path);
            indexedPathMap.put(Paths.get(path), client);
        }
        return indexedPathMap;
    }
}
