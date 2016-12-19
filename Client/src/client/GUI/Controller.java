/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;



import client.Client;
import java.awt.Component;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.ListCellRenderer;
import org.mediaserver.communication.ContentSender;
import org.mediaserver.communication.DedicatedReceiver;
import org.mediaserver.communication.DedicatedSender;
import org.mediaserver.lists.ClientSideServerList;
import org.mediaserver.communication.FileSearcher;
import org.mediaserver.communication.SignalReceiver;
import org.mediaserver.exceptions.ServerNotFoundException;
import org.mediaserver.signals.AccessRequestSignal;
import org.mediaserver.signals.GetFilesRequestSignal;
import org.mediaserver.signals.StreamRequestFromClientSignal;
import org.mediaserver.signals.GetFilesResponseSignal;
/**
 *
 * @author Natalia
 */
 
public class Controller {
    
    private final MainView mainView;
    private final MainPanel mainPanel;
    private SharePanel sharePanel;
    private FilesPanel filesPanel;
    private JComboBox serverlist;
    private static HashMap<Path,String> selectedFilesMap;
    private ArrayList<Path> audioMap;
    private ArrayList<Path> videoMap;
    private ArrayList<Path> imageMap;
   
    
    public Controller(MainView mainView, Component panel1, Component panel2, Component panel3){
        this.mainView = mainView;
        this.mainPanel = (MainPanel) panel1;
        this.sharePanel = (SharePanel) panel2;
        this.filesPanel = (FilesPanel) panel3;
        
        
        mainPanel.subscribeListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
            serverlist = (JComboBox) mainPanel.getJComboBox();
            try{
                Socket socket = new Socket(getIpFromComboBox(),getPortFromComboBox());
                SignalReceiver.getSignalReceiver().connectSocket(socket);
                DedicatedSender.getSender().send(socket, new AccessRequestSignal(Client.getId()));
            } catch (IOException e){
                e.printStackTrace();
            }
            mainView.getContentPane().remove(panel1);
            mainView.getContentPane().add(panel2);
            mainView.getContentPane().revalidate();
            mainView.getContentPane().repaint();
            
            Thread updateListThread = new Thread(new SharePanel.UpdateFilesList());
            updateListThread.start();
            
            selectedFilesMap = new HashMap<Path,String>();
            
            sharePanel.list.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent event) {
                    JList<CheckboxListItem> list =
                   (JList<CheckboxListItem>) event.getSource();

                // Get index of item clicked
                int index = list.locationToIndex(event.getPoint());
                CheckboxListItem item = (CheckboxListItem) list.getModel().getElementAt(index);

                // Toggle selected state
                item.setSelected(!item.isSelected());
                selectedFilesMap.put(sharePanel.getMapKey(index), sharePanel.getMapValue(index));
                
               
              
                // Repaint cell
                list.repaint(list.getCellBounds(index, index));
                
           }
           });         
        }
      }
    );

        sharePanel.sendListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                //wysłanie plików na serwer
                mainView.getContentPane().remove(panel2);
                mainView.getContentPane().add(panel3);
                mainView.getContentPane().revalidate();
                mainView.getContentPane().repaint();
                
                 //Dodanie servera do subkrybowanych //TIBO
                try {
                    addServerToList(selectedFilesMap);
                } catch (ServerNotFoundException e) {
                    e.printStackTrace();
                }
                
                 try{
                    //Wybrane pliki są wysyłane na server
                    Socket socket = new Socket(getIpFromComboBox(),getPortFromComboBox());
                    SignalReceiver.getSignalReceiver().connectSocket(socket);
                    GetFilesResponseSignal getFileResponseSignal = new GetFilesResponseSignal(Client.getId());
                    DedicatedSender.getSender().send(socket, getFileResponseSignal );
                    getFileResponseSignal.setFilesForIndexing(selectedFilesMap);
                    
                } catch (IOException e){
                    e.printStackTrace();
                 }
                
                //Client.addSharedFiles(selectedFilesMap);
                
                //NATALIA 
                /*Set entries = selectedFilesMap.entrySet();
                Iterator entriesIterator = entries.iterator();
                ArrayList<String> values = new ArrayList<String>(selectedFilesMap.size());
                audioMap = new ArrayList<Path>();
                videoMap = new ArrayList<Path>();
                imageMap = new ArrayList<Path>();             
                int i = 0;
                while(entriesIterator.hasNext()){
                    Map.Entry mapping = (Map.Entry) entriesIterator.next();
                    values.add(i,mapping.getValue().toString());
                    
                    String temp = values.get(i);
                    String[] parts = temp.split("\\.(?=[^\\.]+$)");
                    
                    //TODO use FileType
                    switch(parts[1])
                    {
                        case "mp3": filesPanel.getAudioModel().addElement(temp);
                                    audioMap.add((Path) mapping.getKey());
                                    break;
                        case "avi": filesPanel.getVideoModel().addElement(temp);
                                    videoMap.add((Path) mapping.getKey());
                                    break;
                        case "mp4": filesPanel.getVideoModel().addElement(temp);
                                    videoMap.add((Path) mapping.getKey());
                                    break;
                        case "jpg": filesPanel.getImageModel().addElement(temp);
                                    imageMap.add((Path) mapping.getKey());
                                    break;
                    }
                }*/
                
                
                //Tibo pobieranie plików z servera/klienta
                    if(!Client.getSharedFilesFromServer().isEmpty()){
                        Set entries = Client.getSharedFilesFromServer().entrySet();
                        Iterator entriesIterator = entries.iterator();
                        ArrayList<String> values = new ArrayList<String>(Client.getSharedFilesFromServer().size());
                        int i = 0;
                            while(entriesIterator.hasNext()){
                                System.out.println("Downloading file from server");
                                Map.Entry mapping = (Map.Entry) entriesIterator.next();
                                Path pt = (Path) mapping.getKey();
                                String name = pt.getFileName().toString();                              
                                values.add(i,name);
                    
                                String temp = values.get(i);
                                String[] parts = temp.split("\\.(?=[^\\.]+$)");
                    
                                    switch(parts[1])
                                    {
                                        case "mp3": filesPanel.getAudioModel().addElement(temp);
                                            break;
                                        case "avi": filesPanel.getVideoModel().addElement(temp);
                                            break;
                                        case "jpg": filesPanel.getImageModel().addElement(temp);
                                            break;
                                    }
                            }
                    }
            }
        }
        );
        
        
        filesPanel.getAudioList().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    System.out.println("KLIKNIĘTO AUDIO");
                    //wysłanie StreamRequestSignal
                    //odebranie StreamAvailableSignal
                    //wysłanie StreamListenSignal
                    try{
                        Socket socket = new Socket(getIpFromComboBox(),getPortFromComboBox());
                        SignalReceiver.getSignalReceiver().connectSocket(socket);
                        DedicatedSender.getSender().send(socket, new StreamRequestFromClientSignal(Client.getId()));
                        
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
            } 
        });
        filesPanel.getVideoList().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    System.out.println("KLIKNIĘTO VIDEO");
                    //wysłanie StreamRequestSignal
                    //odebranie StreamAvailableSignal
                    //wysłanie StreamListenSignal
                    try{
                        Socket socket = new Socket(getIpFromComboBox(),getPortFromComboBox());
                        SignalReceiver.getSignalReceiver().connectSocket(socket);
                        DedicatedSender.getSender().send(socket, new StreamRequestFromClientSignal(Client.getId()));
                        //ContentSender.send(getIpFromComboBox(), socket.getPort(), videoMap.get(0));
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
            } 
        });
        filesPanel.getImageList().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    System.out.println("KLIKNIĘTO IMAGE");
                    //wysłanie StreamRequestFromClientSignal
                    //odebranie StreamAvailableSignal
                    //wysłanie StreamListenSignal
                    try{
                        Socket socket = new Socket(getIpFromComboBox(),getPortFromComboBox());
                        SignalReceiver.getSignalReceiver().connectSocket(socket);
                        DedicatedSender.getSender().send(socket, new StreamRequestFromClientSignal(Client.getId()));

                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
            } 
        });
    }
    public String getIpFromComboBox(){
        //serverlist = (JComboBox) mainPanel.getJComboBox();
        String temp = serverlist.getSelectedItem().toString();
        String[] parts = temp.split(" ip: ");
        parts = parts[1].split(" port:");
        return parts[0];
    }
    
    public Integer getPortFromComboBox(){
        //serverlist = mainPanel.getJComboBox();
        String temp = serverlist.getSelectedItem().toString();
        String[] parts = temp.split(" port:");
        System.out.println(parts[1]);
        String str_id = parts[1].substring(parts[1].lastIndexOf(":")+1);
        return Integer.parseInt(str_id);
    }
    
    public JList getSelectedFromSharePanel(){
        JList list = sharePanel.getList();
        JList selectedList = null;
        
        
        return selectedList;
    }
    public void addServerToList(HashMap<Path,String> wybranepliki) throws ServerNotFoundException{
        
        //serverlist = mainPanel.getJComboBox();
        //Server id:1 ip: 127.0.0.1
        String temp = serverlist.getSelectedItem().toString();
        String[] parts = temp.split(" ip: ");
        
        String ip = parts[1];
        String str_id = parts[0].substring(parts[0].lastIndexOf(":")+1);
        int id = Integer.parseInt(str_id);
        //ClientSideServerList.getClientSideServerList().addServerToList(ip,10502,id);
        ClientSideServerList.getClientSideServerList().setSubscribed(id, wybranepliki);
    }
    
    public void searchFiles(){
        FileSearcher.searchDirectories();
    }
        
    static class CheckboxListItem {
       private String label;
       private boolean isSelected = false;

       public CheckboxListItem(String label) {
          this.label = label;
       }

       public boolean isSelected() {
          return isSelected;
       }

       public void setSelected(boolean isSelected) {
          this.isSelected = isSelected;
       }

       public String toString() {
          return label;
       }
    }

    // Handles rendering cells in the list using a check box

    static class CheckboxListRenderer extends JCheckBox implements ListCellRenderer<CheckboxListItem> {

       @Override
       public Component getListCellRendererComponent(
             JList<? extends CheckboxListItem> list, CheckboxListItem value,
             int index, boolean isSelected, boolean cellHasFocus) {
          setEnabled(list.isEnabled());
          setSelected(value.isSelected());
          setFont(list.getFont());
          setBackground(list.getBackground());
          setForeground(list.getForeground());
          setText(value.toString());
          return this;
       }
    }
}
