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
import javax.swing.JList;
import javax.swing.JPanel;

/**
 *
 * @author Natalia
 */
public class Controller {
    
    private final MainView mainView;
    private final MainPanel mainPanel;
    private SharePanel sharePanel;
    
    public Controller(MainView mainView, JPanel panel1, JPanel panel2){
        this.mainView = mainView;
        this.mainPanel = (MainPanel) panel1;
        this.sharePanel = (SharePanel) panel2;
        
        mainPanel.subscribeListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
            System.out.println("Dodano panel udostępniania");
            mainView.getContentPane().remove(panel1);
            mainView.getContentPane().add(panel2);
            mainView.getContentPane().invalidate();
            mainView.getContentPane().validate();
            mainView.getContentPane().repaint();
           
              
            sharePanel.list.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent event) {
                    JList<SharePanel.CheckboxListItem> list =
                   (JList<SharePanel.CheckboxListItem>) event.getSource();

                // Get index of item clicked
                int index = list.locationToIndex(event.getPoint());
                SharePanel.CheckboxListItem item = (SharePanel.CheckboxListItem) list.getModel().getElementAt(index);

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
}
