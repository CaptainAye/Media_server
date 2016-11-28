/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.queues;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Tomek
 */
public class ClientList implements Serializable {
    
    private static class Client{
        HashMap<String,Path> filesMap;
        Integer id;
        
        Client (HashMap<String,Path> filesMap,Integer id){
            this.filesMap = filesMap;
            this.id = id;
        }
        
    }
    private ArrayList<Client> list = new ArrayList<Client>();
    private static ClientList instance = new ClientList();
    private ClientList () {
        try{
            FileInputStream queueFile = new FileInputStream("ClientQueue.ser");
            ObjectInputStream deserializedList = new ObjectInputStream(queueFile);
            list = (ArrayList<Client>) deserializedList.readObject();
        } catch ( IOException | ClassNotFoundException e){
            e.printStackTrace();
            instance = new ClientList(); 
        }
    }
    public static ClientList getClientList(){
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
    
    public void add(HashMap<String,Path> filesMap, Integer id){
        if (!clientExists(id)){
            list.add(new Client(filesMap, id));
        }
    }
}
