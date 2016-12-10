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
import javax.swing.JCheckBox;
import javax.swing.ListCellRenderer;
import org.mediaserver.communication.DedicatedSender;
import org.mediaserver.lists.ClientSideServerList;
import org.mediaserver.communication.FileSearcher;
import org.mediaserver.signals.AccessRequestSignal;
/**
 *
 * @author Natalia
 */
 
public class Controller {
    
    private final MainView mainView;
    private final MainPanel mainPanel;
    private SharePanel sharePanel;
    //tibo
    private JComboBox serverlist;
   
    
    public Controller(MainView mainView, JPanel panel1, JPanel panel2){
        this.mainView = mainView;
        this.mainPanel = (MainPanel) panel1;
        this.sharePanel = (SharePanel) panel2;
        
        mainPanel.subscribeListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
            try{
                DedicatedSender.getSender().send(new Socket(getIpFromComboBox(),10502), new AccessRequestSignal(Client.getId()));
            } catch (IOException e){
                e.printStackTrace();
        }
            mainView.getContentPane().remove(panel1);
            mainView.getContentPane().add(panel2);
            mainView.getContentPane().revalidate();
            mainView.getContentPane().repaint();
            
            //Tibo
            //dodanie servera do listy subsrybowanych serwerów
            addServerToList();
            //Client.addToSubServerList(serverlist.getSelectedItem().toString());
            //przeszukiwanie plików
            //Client.researchFiles();
            
           
            
            //sharePanel.setContent(FileSearcher.searchDirectories()); 
            Thread updateListThread = new Thread(new SharePanel.UpdateFilesList());
            updateListThread.start();
            
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

                // Repaint cell
                list.repaint(list.getCellBounds(index, index));
           }
           });         
        }
      }    
    );     
  }
    
    public String getIpFromComboBox(){
        serverlist = mainPanel.getJComboBox();
        String temp = serverlist.getSelectedItem().toString();
        String[] parts = temp.split(" ip: ");;
        return parts[1];
    }
    
    public Integer getPortFromComboBox(){
        serverlist = mainPanel.getJComboBox();
        //Server id:1 ip: 127.0.0.1
        String temp = serverlist.getSelectedItem().toString();
        String[] parts = temp.split(" ip: ");
        String str_id = parts[0].substring(parts[0].lastIndexOf(":")+1);
        return Integer.parseInt(str_id);
    }
    public void addServerToList(){
        serverlist = mainPanel.getJComboBox();
        //Server id:1 ip: 127.0.0.1
        String temp = serverlist.getSelectedItem().toString();
        String[] parts = temp.split(" ip: ");
        
        String ip = parts[1];
        String str_id = parts[0].substring(parts[0].lastIndexOf(":")+1);
        int id = Integer.parseInt(str_id);
        //System.out.println(ip);
        //System.out.println(id);
        ClientSideServerList.getClientSideServerList().addServerToList(ip,id,10502);
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