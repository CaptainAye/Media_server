/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.lists;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

/**
 *
 * @author Tomek
 */
public class ClientSideServerList {
    
    private class Server{
        private final String ip;
        private final Integer port;
        private final Integer id;
        
        public Server (String ip, Integer port, Integer id){
            this.ip = ip;
            this.port = port;
            this.id = id;
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
                    removeServerFromList(serverToRemove);
                
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
    
    private synchronized void removeServerFromList(Server server){
        if (list.contains(server) && server != null){
            list.remove(server);
            for(int i=0; i < serverList.getItemCount();i++){ // removing item from combobox
                String comboBoxItem = "Server id:" + server.getId() + " ip: " + server.getIp();
                if(serverList.getItemAt(i).toString().equals(comboBoxItem)){
                    serverList.removeItemAt(i);
                    return;
                }
            }
        }
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
    private ClientSideServerList(){
        serverAvailabilityChecker.start();
    }
    
    public static ClientSideServerList getClientSideServerList(){
        return instance;
    }
    
    public synchronized void addServer(String serverIp, Integer serverPort, Integer serverId){
        Server server = new Server(serverIp, serverPort, serverId);
        String comboBoxItem = "Server id:" + serverId + " ip: " + serverIp;
        serverList.addItem(comboBoxItem);
        list.add(server);
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
    
}
