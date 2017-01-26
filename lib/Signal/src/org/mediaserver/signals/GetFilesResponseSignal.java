/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.signals;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.mediaserver.interfaces.Signalizable;

/**
 *
 * @author Natalka
 */
public class GetFilesResponseSignal extends Signalizable{
    //problem z serializable
    private List<String> list = new ArrayList<>();
    public GetFilesResponseSignal (Integer id, HashMap<Path,String> map)
    {
        setId(id);
        for (Path path :  map.keySet()){
            this.list.add(path.toString());
        }
    }
    
    public void setFilesForIndexing(HashMap<Path,String> map){
        for( Path path : map.keySet()){
            this.list.add(path.toString());
        }
    }
    
    public HashMap<Path,String> getFilesToIndex(){
        HashMap<Path,String> map = new HashMap<>();
        for (String path : list){
            Path realPath = Paths.get(path);
            String name = realPath.getFileName().toString();
            map.put(realPath, name);
        }
        if ( map.size() == 0){
            map = null;
        }
        return map;
    }
}
