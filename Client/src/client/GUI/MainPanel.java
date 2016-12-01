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
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Natalia
 */
public class MainPanel extends JPanel{
    
    //panel główny z serwerami
    private int width = 1024;
    private int height = 668;

    private JLabel label1;
    private JLabel label2;
    private JButton subscribeButton;
    private JComboBox serverList;

    
    
    MainPanel(){
        initComponents();
    }
    
    private void initComponents(){
        
        setSize(width,height);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setMaximumSize(new Dimension(width,height));
        setBorder(BorderFactory.createLineBorder(Color.gray));
        
        
        label1 = new JLabel("WITAJ!");
        label1.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        label1.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        label2 = new JLabel("Wybierz serwer z listy do którego chcesz się podłączyć");
        label2.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        serverList = new JComboBox();
        serverList.setMaximumSize(new Dimension(300,30));
        serverList.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        
        subscribeButton = new JButton("Subskrybuj");
        subscribeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        subscribeButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));


        add(Box.createRigidArea(new Dimension(0, 20)));
        add(label1);
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(label2);
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(serverList);
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(subscribeButton);
   
    }
    
    public void subscribeListener(ActionListener listener) {
            subscribeButton.addActionListener(listener);
  }
}