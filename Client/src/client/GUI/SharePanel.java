/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import org.mediaserver.communication.FileSearcher;
/**
 *
 * @author Natalia
 */
public class SharePanel extends JPanel{
    
    private final int width = 1024;
    private final int height = 668;
    
    private static JLabel label1;
    private static JLabel label2;
    private JButton sendButton;
    static JList<Controller.CheckboxListItem> list;
    private static Controller.CheckboxListItem[] checkboxList;
    private static ArrayList<Path> keys;
    private static ArrayList<String> values;
    private static JProgressBar progressBar;
    private static HashMap<Path,String> filesMap;
    private static HashMap<Path,String> selectedFilesMap;
    
    
    
    SharePanel(){
        initComponents();
    }
    
    private void initComponents(){
        setSize(width,height);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setMaximumSize(new Dimension(width,height));
        setBorder(BorderFactory.createLineBorder(Color.gray));
        
        label1 = new JLabel("Poczekaj na wyszukanie plików.");
        label1.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        label1.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        label2 = new JLabel();
        label2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        sendButton = new JButton("WYŚLIJ");
        sendButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sendButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                
        progressBar = new JProgressBar();
        
        list = new  JList<Controller.CheckboxListItem>();
        list.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(label1);
        add(progressBar);
        add(new JScrollPane(list));
        add(label2);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(sendButton);
        revalidate();
    }
    
    public void checkFiles(MouseAdapter adapter) {
        list.addMouseListener(adapter);
    }
    
    public void sendListener(ActionListener listener) {
            sendButton.addActionListener(listener);                                                     
    }
    
    public JList getList(){
        return list;
    }
    
    public HashMap<Path,String> getMap(){
        return filesMap;
    }
    
    public HashMap<Path,String> getSelectedMap(){
        return selectedFilesMap;
    }
    
    public Path getMapKey(int i){
        return keys.get(i);
    }
    
    public String getMapValue(int i){
        return values.get(i);
    }
    
    public static class UpdateFilesList implements Runnable {
        public void run() {
            
            progressBar.setIndeterminate(true);
            filesMap = FileSearcher.searchDirectories();
            selectedFilesMap = new HashMap<Path,String>();
            label1.setText("Wybierz pliki które chcesz wysłać do serwera.");
            progressBar.setIndeterminate(false);
            
            checkboxList = new Controller.CheckboxListItem[filesMap.size()];
            keys = new ArrayList<Path>();
            values = new ArrayList<String>();
            Set entries = filesMap.entrySet();
            Iterator entriesIterator = entries.iterator();
            int i = 0;
            while(entriesIterator.hasNext()){
                Map.Entry mapping = (Map.Entry) entriesIterator.next();
                keys.add(i, (Path) mapping.getKey());
                values.add(i, mapping.getValue().toString());
                checkboxList[i] = new Controller.CheckboxListItem(values.get(i));
                i++;
            }
        
            label2.setText("Znaleziono " + values.size() + " plików.");
            list.setListData(checkboxList);
            list.setCellRenderer(new Controller.CheckboxListRenderer());
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
    }
}
