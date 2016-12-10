/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.lists;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JComboBox;
import org.mediaserver.exceptions.ServerNotFoundException;

/**
 *
 * @author Tomek
 */
public  class ClientSideServerList {
    
    public class Server{
        private final String ip;
        private final Integer port;
        private final Integer id;
        private Boolean subscribed;
        private HashMap<Path,String> filesMap;
        public Server (String ip, Integer port, Integer id){
            this.ip = ip;
            this.port = port;
            this.id = id;
            subscribed = false;
            filesMap = null;
        }
        public String getIp(){
            return ip;
        }
        public Integer getPort(){
            return port;
        }
        public Integer getId(){
            return id;
        }
        public synchronized void subscribe(){
            subscribed = true;
            ClientSideServerList.this.removeServerFromViewList(this);
        }
        
        public synchronized void unsubscribe(){
            subscribed = false;
            ClientSideServerList.this.addServerToViewList(this);
        }
        public Boolean subscribeStatus(){
            return subscribed;
        }
        
        public void setFilesMap(HashMap<Path, String> map){
            this.filesMap = map;
        }
        
        public HashMap<Path,String> getFilesMap(){
            return filesMap;
        }
    }
    
    
    private JComboBox serverList;
    private static ClientSideServerList instance = new ClientSideServerList();
    private List<Server> list = new ArrayList<Server>();
    private Thread serverAvailabilityChecker = new Thread(new Runnable(){
        public void run(){
            Server serverToRemove = null;
            while(true){
                serverToRemove = null;
                    for (Server server: list){
                        if (!serverAvailable(server)){
                            serverToRemove = server;
                            break;
                        }
                    }
                try{
                    if (serverToRemove != null){
                        removeServerFromList(serverToRemove.getId());
                    }
                } catch(ServerNotFoundException e){
                    e.printStackTrace();
                    //TODO handling serverNotFoundException
                }
                try{
                Thread.sleep(1000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                    System.exit(-1);
                    //TODO handling Interrupted exception
                }
            }
        }
    });
    private Server getServerFromList(Integer id) throws ServerNotFoundException{
        for(Server server : list){
            if (server.getId().equals(id)){
                return server;
            }
        }
        throw new ServerNotFoundException();
    }
    
    private String comboBoxStringBuilder(Server server){
        return "Server id:" + server.getId() + " ip: " + server.getIp() + " port: " + server.getPort();
    }
    
    private ClientSideServerList(){
        serverAvailabilityChecker.start();
    }
    private synchronized void addServerToViewList(Server serverToAdd){
            String comboBoxItem = comboBoxStringBuilder(serverToAdd);
            serverList.addItem(comboBoxItem);
        }
    private Boolean serverAvailable(Server server){
        try{
            Socket s = new Socket(server.getIp(),server.getPort());
            s.close();
            return true;
        } catch (IOException e){
          //e.printStackTrace();
        }
        return false;
    }
    private synchronized void removeServerFromViewList(Server serverToRemove){
            for(int i=0; i < serverList.getItemCount();i++){ // removing item from combobox
                String comboBoxItem = comboBoxStringBuilder(serverToRemove);
                if(serverList.getItemAt(i).toString().equals(comboBoxItem)){
                    serverList.removeItemAt(i);
                    return;
                }
            }
    }
    
    public synchronized void removeServerFromList(Integer id) throws ServerNotFoundException{
        for(Server serverToRemove : list){
            if (serverToRemove.getId().equals(id)){
                list.remove(serverToRemove);
                removeServerFromViewList(serverToRemove);
                return;
            }
        }
        throw new ServerNotFoundException();
        }
    public synchronized void addServerToList(String serverIp, Integer serverPort, Integer serverId){
        Server serverToAdd = new Server(serverIp, serverPort, serverId);
        list.add(serverToAdd);
        addServerToViewList(serverToAdd);
    }
    public static ClientSideServerList getClientSideServerList(){
        return instance;
    } 
    public Boolean serverExists(Integer id){
        for(Server server : list){
            if(server.getId().equals(id)){
                return true;
            }
        }
        return false;
    } 
    public void addComboBoxListener(JComboBox serverList){
        this.serverList = serverList;
    }
    public Socket getServerSocket(Integer id) throws IOException, ServerNotFoundException{
        Server server = getServerFromList(id);
        return new Socket(server.getIp(),server.getPort());
    }
    public void setSubscribed( Integer id, HashMap<Path,String> map) throws ServerNotFoundException{
        Server server = getServerFromList(id);
        server.setFilesMap(map);
        server.subscribe();
    }
    
    public void setUnsubscribed(Integer id) throws ServerNotFoundException{
        Server server = getServerFromList(id);
        server.setFilesMap(null);
        server.unsubscribe();
    }
}
