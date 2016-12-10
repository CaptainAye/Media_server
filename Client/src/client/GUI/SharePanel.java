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
import java.awt.event.MouseAdapter;
import java.nio.file.Path;
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
    private JButton button;
    static JList<Controller.CheckboxListItem> list;
    private static Controller.CheckboxListItem[] checkboxList;
    private static String[] keys;
    private static String[] values;
    static JProgressBar progressBar;
    
    
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
        
        button = new JButton("WYŚLIJ");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                
        progressBar = new JProgressBar();
        
        list = new  JList<Controller.CheckboxListItem>();
        
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(label1);
        add(progressBar);
        add(new JScrollPane(list));
        add(label2);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(button);
    }
    //Tibo
 /*   public void setContent(HashMap<Path,String> listaPozycji){

        checkboxList = new Controller.CheckboxListItem[listaPozycji.size()];
        keys = new String[listaPozycji.size()];
        values = new String[listaPozycji.size()];
        Set entries = listaPozycji.entrySet();
        Iterator entriesIterator = entries.iterator();
        int i = 0;
        while(entriesIterator.hasNext()){
            Map.Entry mapping = (Map.Entry) entriesIterator.next();
            keys[i] = mapping.getKey().toString();
            values[i] = mapping.getValue().toString();
            checkboxList[i] = new Controller.CheckboxListItem(values[i]);
            i++;
        }
        
        label2.setText("Znaleziono " + values.length + " plików.");
        list.setListData(checkboxList);
        list.setCellRenderer(new Controller.CheckboxListRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
    }
*/

    public void checkFiles(MouseAdapter adapter) {
        list.addMouseListener(adapter);
    }
         
    public static class UpdateFilesList implements Runnable {
        public void run() {
            
            long startTime = System.nanoTime();
            progressBar.setIndeterminate(true);
            HashMap<Path,String> listaPozycji = FileSearcher.searchDirectories();
            long endTime = System.nanoTime();
            label1.setText("Wybierz pliki które chcesz wysłać do serwera.");
            progressBar.setIndeterminate(false);
            long duration = (endTime - startTime);
            
            checkboxList = new Controller.CheckboxListItem[listaPozycji.size()];
            keys = new String[listaPozycji.size()];
            values = new String[listaPozycji.size()];
            Set entries = listaPozycji.entrySet();
            Iterator entriesIterator = entries.iterator();
            int i = 0;
            while(entriesIterator.hasNext()){
                Map.Entry mapping = (Map.Entry) entriesIterator.next();
                keys[i] = mapping.getKey().toString();
                values[i] = mapping.getValue().toString();
                checkboxList[i] = new Controller.CheckboxListItem(values[i]);
                i++;
            }
        
            label2.setText("Znaleziono " + values.length + " plików.");
            list.setListData(checkboxList);
            list.setCellRenderer(new Controller.CheckboxListRenderer());
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
    }

    /*public static class UpdateRunnable implements Runnable {
        private final Controller.CheckboxListItem[] listTest;
        private UpdateRunnable(Controller.CheckboxListItem[] list1) {
            this.listTest = list1;
        }
        public void run() {
            DefaultListModel model = (DefaultListModel) list.getModel();
            model.clear();
            for (Controller.CheckboxListItem i : listTest) {
                list.setModel(model);
                model.addElement(i);
            }
        
        list.setCellRenderer(new Controller.CheckboxListRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
    }
    */
}
