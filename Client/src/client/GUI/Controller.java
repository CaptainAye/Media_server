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
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javax.imageio.ImageIO;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.ListCellRenderer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.mediaserver.communication.ContentReceiver;
import org.mediaserver.communication.ContentSender;
import org.mediaserver.communication.DedicatedReceiver;
import org.mediaserver.communication.DedicatedSender;
import org.mediaserver.lists.ClientSideServerList;
import org.mediaserver.communication.FileSearcher;
import org.mediaserver.communication.SignalReceiver;
import org.mediaserver.communication.StreamTaskChecker;
import org.mediaserver.exceptions.ServerNotFoundException;
import org.mediaserver.files.FileType;
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
    private StreamView streamView;
    private JComboBox serverlist;
    private HashMap<Path,String> selectedFilesMap;
    private ArrayList<Path> audioMap;
    private ArrayList<String> audioMapURI;
    private ArrayList<Media> audioMediaFiles;
    private ArrayList<MediaPlayer> audioMediaPlayers;
    private boolean isPlaying;
    private int filesPanelIndex;
    private int streamViewAudioIndex;
    private ArrayList<Path> videoMap;
    private ArrayList<Path> imageMap;
    private String ipFromCombobox;
    private int portFromCombobox;
    private Socket socket;
    private BufferedImage image;
    
    public Controller(MainView mainView, Component panel1, Component panel2, Component panel3){
        this.mainView = mainView;
        this.mainPanel = (MainPanel) panel1;
        this.sharePanel = (SharePanel) panel2;
        this.filesPanel = (FilesPanel) panel3;
        
        
        mainPanel.subscribeListener(new ActionListener() {
        @Override
            public void actionPerformed(ActionEvent evt) {
                serverlist = (JComboBox) mainPanel.getJComboBox();
                //sprawdzanie czy klient już subskrybował serwer
                try{
                    ipFromCombobox = getIpFromComboBox();
                    portFromCombobox = getPortFromComboBox();
                    socket = new Socket(ipFromCombobox, portFromCombobox );
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


                //przebudować
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
                System.out.println("Enter action performed");
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
                    //System.out.println(ipFromCombobox);
                    //System.out.println(portFromCombobox);
                    //System.out.println(socket.toString());
                    Socket socket = new Socket(ipFromCombobox, portFromCombobox );
                    SignalReceiver.getSignalReceiver().connectSocket(socket);
                    DedicatedSender.getSender().send(socket, new GetFilesResponseSignal(Client.getId(),selectedFilesMap));
                    Thread streamChecker = new Thread(new StreamTaskChecker(ipFromCombobox, portFromCombobox));
                    streamChecker.start();
                    //SignalReceiver.getSignalReceiver().connectSocket(socket);
                    //DedicatedSender.getSender().send(socket, new GetFilesResponseSignal(Client.getId(),selectedFilesMap));

                    
                } catch (Exception e){
                    e.printStackTrace();
                 }
                
                //if(Client.getSharedFilesFromServer().isEmpty()){
                    Set entries = selectedFilesMap.entrySet();
                    Iterator entriesIterator = entries.iterator();
                    ArrayList<String> values = new ArrayList<String>(selectedFilesMap.size());
                    //ArrayList<String> values = new ArrayList<String>(Client.getSharedFilesFromServer().size());
                    audioMap = new ArrayList<Path>();
                    audioMapURI = new ArrayList<String>();
                    videoMap = new ArrayList<Path>();
                    imageMap = new ArrayList<Path>();
                    audioMediaFiles = new ArrayList<Media>();
                    audioMediaPlayers = new ArrayList<MediaPlayer>();
                    int i = 0;
                    while(entriesIterator.hasNext()){
                        //System.out.println("Downloading file from server");
                        Map.Entry mapping = (Map.Entry) entriesIterator.next();
                        Path pt = (Path) mapping.getKey();
                        String name = pt.getFileName().toString();
                        values.add(i,name);

                        String temp = values.get(i);
                        switch(FileType.getFileType(pt))
                        {
                            case AUDIO: filesPanel.getAudioModel().addElement(temp);
                                        audioMap.add((Path) mapping.getKey());
                                        break;
                            case VIDEO: filesPanel.getVideoModel().addElement(temp);
                                        videoMap.add((Path) mapping.getKey());
                                        break;
                            case IMAGE: filesPanel.getImageModel().addElement(temp);
                                        imageMap.add((Path) mapping.getKey());
                                        break;
                        }
                        
                    }
                //}
                //zmiana na URI audioMap
                //zrobienie Media i MediaPlayer dla każdefo pliku muzycznego
                new JFXPanel();
                for(int a=0;a<audioMap.size();a++){
                    String musicFilePath = audioMap.get(a).toString();
                    musicFilePath = musicFilePath.replace("\\", "/");
                    File file = new File(musicFilePath);
                    String fileURI = file.toURI().toString();
                    audioMapURI.add(fileURI);
                    audioMediaFiles.add(new Media(audioMapURI.get(a)));
                    audioMediaPlayers.add(new MediaPlayer(audioMediaFiles.get(a)));
                }
            }
        }
        );
        
        filesPanel.streamMusic(new MouseAdapter(){
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    //System.out.println("KLIKNIĘTO AUDIO");
                    //wysłanie StreamRequestSignal
                    //odebranie StreamAvailableSignal
                    //wysłanie StreamListenSignal
                    filesPanelIndex = filesPanel.getAudioList().locationToIndex(event.getPoint());
                    try{
                        Socket socket = new Socket(ipFromCombobox,portFromCombobox);
                        SignalReceiver.getSignalReceiver().connectSocket(socket);
                        Integer contentReceiverPort = 50001; //TODO bardziej wyszukana metoda wyboru portu.
                        //Ustaw polaczenie do odebrania wysylanego strumienia (jakies Audio.getContent czy cos w ten desen)
                        Integer hostClientId = Client.getStringSharedFilesFromServer().get(audioMap.get(filesPanelIndex).toString());
                        Thread receiver = new Thread(new ContentReceiver(hostClientId, contentReceiverPort, audioMap.get(filesPanelIndex).toFile().getName()));
                        receiver.start();
                        DedicatedSender.getSender().send(socket, new StreamRequestFromClientSignal(Client.getId(),hostClientId, contentReceiverPort,audioMap.get(filesPanelIndex)));
                        //String receivedFilePath = ContentReceiver.receiveContent(hostClientId, contentReceiverPort, audioMap.get(filesPanelIndex).toFile().getName());
                        String receivedFilePath;
                        //ContentSender.send(ipFromCombobox, portFromCombobox, audioMap.get(filesPanelIndex));
                        receivedFilePath = audioMap.get(filesPanelIndex).getFileName().toString(); // TODO remove when file received from server
                        streamView = new StreamView();
                        streamView.initAudioComponents(receivedFilePath, filesPanel.getAudioModel());
                        streamView.setLocationRelativeTo(null);
                        streamView.setVisible(true);
;
                        streamViewAudioIndex=filesPanelIndex;
                        audioMediaPlayers.get(streamViewAudioIndex).play();
                        audioMediaPlayers.get(filesPanelIndex).setVolume(0.1);
                        isPlaying=true;
                        streamView.getAudioList().setSelectedIndex(streamViewAudioIndex);
                        
                        if(!streamView.isVisible())
                            audioMediaPlayers.get(streamViewAudioIndex).stop();
                        
                        //buttons
                        streamView.playListener(new ActionListener(){
                            public void actionPerformed(ActionEvent e) {
                                if(isPlaying){
                                    streamView.setPlayButtonTitle("ODTWÓRZ");
                                    audioMediaPlayers.get(streamViewAudioIndex).pause();
                                    isPlaying=false;
                                }
                                else{
                                    streamView.setPlayButtonTitle("PAUZA");
                                    audioMediaPlayers.get(streamViewAudioIndex).play();
                                    isPlaying=true;
                                }   
                            }
                            
                        } 
                        );
                        streamView.prevListener(new ActionListener(){
                            public void actionPerformed(ActionEvent e) {
                                if(streamViewAudioIndex>0){
                                    audioMediaPlayers.get(streamViewAudioIndex).stop();
                                    streamViewAudioIndex--;
                                    streamView.getAudioList().setSelectedIndex(streamViewAudioIndex);
                                    streamView.setAudioTitle(audioMap.get(streamViewAudioIndex).getFileName().toString());
                                    audioMediaPlayers.get(streamViewAudioIndex).play();
                                }
                                else if(streamViewAudioIndex==0);
                            }
                            
                        }
                        );
                        streamView.nextListener(new ActionListener(){
                            public void actionPerformed(ActionEvent e) {
                                if(streamViewAudioIndex<audioMediaPlayers.size()-1){
                                    audioMediaPlayers.get(streamViewAudioIndex).stop();
                                    streamViewAudioIndex++;
                                    streamView.getAudioList().setSelectedIndex(streamViewAudioIndex);
                                    streamView.setAudioTitle(audioMap.get(streamViewAudioIndex).getFileName().toString());
                                    audioMediaPlayers.get(streamViewAudioIndex).play();
                                }
                                else if(streamViewAudioIndex==audioMediaPlayers.size()-1);   
                            }
                            
                        }
                        );
                        
                        streamView.audioListListener(new MouseAdapter() {
                            public void mouseClicked(MouseEvent event) {
                                if (event.getClickCount() == 2) {
                                    audioMediaPlayers.get(streamViewAudioIndex).stop();
                                    streamViewAudioIndex=streamView.getAudioList().locationToIndex(event.getPoint());
                                    streamView.getAudioList().setSelectedIndex(streamViewAudioIndex);
                                    streamView.setAudioTitle(audioMap.get(streamViewAudioIndex).getFileName().toString());
                                    audioMediaPlayers.get(streamViewAudioIndex).play();
                                }
                            }            
                        }
                        );

                        streamView.changeVolume(new ChangeListener(){
                            public void stateChanged(ChangeEvent e) {
                                JSlider src = (JSlider)e.getSource();
                                int value = src.getValue();
                                System.out.println("Value " + value);
                                audioMediaPlayers.get(streamViewAudioIndex).setVolume(value/100);
                                streamView.getVolume().setValue(value);
                            } 
                        }
                        );
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
            } 
        });
        
        filesPanel.streamVideo(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    //System.out.println("KLIKNIĘTO VIDEO");
                    try{
                        Socket socket = new Socket(ipFromCombobox,portFromCombobox);
                        SignalReceiver.getSignalReceiver().connectSocket(socket);
                        Integer contentReceiverPort = 50002;
                        
                        int index = filesPanel.getVideoList().locationToIndex(event.getPoint());
                        Integer hostClientId = Client.getStringSharedFilesFromServer().get(videoMap.get(index).toString());
                        
                        Thread receiver = new Thread(new ContentReceiver(hostClientId, contentReceiverPort, videoMap.get(index).toFile().getName()));
                        receiver.start();
                        DedicatedSender.getSender().send(socket, new StreamRequestFromClientSignal(Client.getId(),hostClientId,contentReceiverPort,videoMap.get(index)));
                        String receivedFilePath;// = ContentReceiver.receiveContent(hostClientId, contentReceiverPort, videoMap.get(index).toFile().getName());
                        
                        ContentSender.send(ipFromCombobox, portFromCombobox, videoMap.get(index));
                        ContentSender.streamForwardVideo(ipFromCombobox, portFromCombobox, videoMap.get(index));
                        ///VLC
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
            } 
        });
        
        filesPanel.streamImage(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    //System.out.println("KLIKNIĘTO IMAGE");
                    try{
                        Socket socket = new Socket(ipFromCombobox,portFromCombobox);
                        SignalReceiver.getSignalReceiver().connectSocket(socket);
                        int index = filesPanel.getImageList().locationToIndex(event.getPoint());
                        System.out.println("client id : " + Client.getId());
                        Integer contentReceiverPort = 50003;
                        Integer hostClientId =Client.getStringSharedFilesFromServer().get(imageMap.get(index).toString());
                        
                        Thread receiver = new Thread(new ContentReceiver(hostClientId, contentReceiverPort, imageMap.get(index).toFile().getName()));
                        receiver.start();
                        DedicatedSender.getSender().send(socket, new StreamRequestFromClientSignal(Client.getId(),hostClientId,contentReceiverPort,imageMap.get(index)));
                        String receivedFilePath;// = ContentReceiver.receiveContent(hostClientId, contentReceiverPort, imageMap.get(index).toFile().getName());
                        
                        ContentSender.send(ipFromCombobox, portFromCombobox, imageMap.get(index));
                        
                        receivedFilePath = imageMap.get(index).toString(); // TODO remove when file will be properly received from server
                        image = ImageIO.read(new File(receivedFilePath));
                        
                        if(image.getWidth()>800 || image.getHeight()>600){
                            System.out.println("SCALED");
                            BufferedImage bufferedImage = resize(image,800,600);
                            streamView = new StreamView(bufferedImage.getWidth(),bufferedImage.getHeight());
                            streamView.initImageComponents(bufferedImage,receivedFilePath);
                            streamView.setLocationRelativeTo(null);
                            streamView.setVisible(true);
                            streamView.setDefaultCloseOperation(StreamView.DISPOSE_ON_CLOSE);
                        }
                        else{
                            System.out.println("NOT SCALED");
                            streamView = new StreamView(image.getWidth(),image.getHeight());
                            streamView.initImageComponents(image,imageMap.get(index).getFileName().toString());
                            streamView.setLocationRelativeTo(null);
                            streamView.setVisible(true);
                            streamView.setDefaultCloseOperation(StreamView.DISPOSE_ON_CLOSE);
                        }

                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
            } 
        });
    }
    
    public static BufferedImage resize(BufferedImage img, int newWidth, int newHeight){ 
        Image tmp = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
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
