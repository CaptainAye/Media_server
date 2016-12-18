/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.lists;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Tomek
 */
public class ServerSideClientList implements Serializable {
    
    private static class Client{
        HashMap<Path,String> filesMap;
        final Integer id;
        
        Client (HashMap<Path,String> filesMap,Integer id){
            this.filesMap = filesMap;
            this.id = id;
        }
        
        public Integer getId(){
            return id;
        }
    }
    
    
    private String serializeFileName = "ClientList.ser";
    private List<Client> list;
    private static ServerSideClientList instance;
    
    private void readClientList(){
        try{
            FileInputStream listFile = new FileInputStream(serializeFileName);
            ObjectInputStream deserializedList = new ObjectInputStream(listFile);
            list = (ArrayList<Client>) deserializedList.readObject();
        } catch ( IOException | ClassNotFoundException e){
            try{
                FileOutputStream fileStream = new FileOutputStream(serializeFileName);
                ObjectOutputStream writeStream = new ObjectOutputStream(fileStream);
                writeStream.writeObject(list);
                
            } catch (IOException  exc){
                exc.printStackTrace();
            }
        }
    }
    
    private ServerSideClientList () {
            list = new ArrayList<Client>();
        
    }
    public static ServerSideClientList getClientList(){
        if (instance == null){
            instance = new ServerSideClientList();
            instance.readClientList();
        }

        return instance;
    }
    
    public Boolean clientExists(Integer id){
        for(Client client: list){
            if (client.id == id){
                return true;
            }
        }
        return false;
    }
    
    public void addToList(HashMap<Path,String> filesMap, Integer id){
        if (!clientExists(id)){
            list.add(new Client(filesMap, id));
            saveClientList();
        }
    }
    
    public void removeFromList(Integer id){
        Client clientToRemove = null;
        for(Client client : list){
            if (client.getId() == id){
                clientToRemove = client;
                break;
            }
        }
        if (clientToRemove != null){
            list.remove(clientToRemove);
            saveClientList();
        }
    }
    
    public void saveClientList(){
        try{
        FileOutputStream listFile = new FileOutputStream(serializeFileName);
        ObjectOutputStream serializableList = new ObjectOutputStream(listFile);
        serializableList.writeObject(list);
        } catch (FileNotFoundException e){
            e.printStackTrace();
            System.err.println("Could not open/create ClientList.ser");
            System.exit(-1);
        }
           catch (IOException e){
               e.printStackTrace();
            System.err.println("Could not serialize to ClientList.ser");
            System.exit(-1);
           }
    }
    public HashMap<Path,Integer> getMap(){
        HashMap<Path, Integer> filesDB = new HashMap<Path,Integer>();
        for(Client client : list){
            Set<Path> keySet = client.filesMap.keySet();
            for (Path filePath : keySet){
                filesDB.put(filePath, client.getId());
            }
        }
        return filesDB;
    }
}
