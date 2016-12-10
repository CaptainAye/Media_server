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
import java.awt.event.MouseEvent;
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
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import org.mediaserver.communication.FileSearcher;
import org.mediaserver.lists.ClientSideServerList;
/**
 *
 * @author Natalia
 */
public class SharePanel extends JPanel{
    
    private int width = 1024;
    private int height = 668;
    
    private JLabel label1;
    private JButton button;
    JList<CheckboxListItem> list;
    CheckboxListItem[] checkboxList;
    HashMap<Path,String> listaPozycji;
    String[] keys;
    String[] values;
    
    
    SharePanel(){
        setContent(FileSearcher.searchDirectories());
        initComponents();
    }
    
    private void initComponents(){
        
        setSize(width,height);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setMaximumSize(new Dimension(width,height));
        setBorder(BorderFactory.createLineBorder(Color.gray));
        
        label1 = new JLabel("Wybierz pliki które chcesz udostępnić serwerowi.");
        label1.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        label1.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        button = new JButton("coś");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Times New Roman", Font.PLAIN, 20));
 
        
        list.setCellRenderer(new CheckboxListRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(label1);
        add(new JScrollPane(list));
        add(button);
    }
    //Tibo
    public void setContent(HashMap<Path,String> listaPozycji){
        
        checkboxList = new CheckboxListItem[listaPozycji.size()];
        list = new JList<CheckboxListItem>(checkboxList);
        keys = new String[listaPozycji.size()];
        values = new String[listaPozycji.size()];
        Set entries = listaPozycji.entrySet();
        Iterator entriesIterator = entries.iterator();
        int i = 0;
        while(entriesIterator.hasNext()){
            Map.Entry mapping = (Map.Entry) entriesIterator.next();
            keys[i] = mapping.getKey().toString();
            values[i] = mapping.getValue().toString();
            checkboxList[i] = new CheckboxListItem(values[i]);
            i++;
        }
    }

    public void checkFiles(MouseAdapter adapter) {
        list.addMouseListener(adapter);
    }
    
    // Represents items in the list that can be selected
 
    class CheckboxListItem {
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

    class CheckboxListRenderer extends JCheckBox implements ListCellRenderer<CheckboxListItem> {

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
