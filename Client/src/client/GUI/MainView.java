/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.GUI;

import client.Client;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

//tibo
import client.GUI.MainPanel;
import java.awt.Toolkit;

/**
 *
 * @author Natalia
 */
public class MainView extends JFrame{
    
    private Controller control;
    private JPanel titlePanel;
    private JLabel titleLabel;
    private MainPanel mainPanel;
    private SharePanel sharePanel;
    private int height = 768;
    private int width = 1024;
    private Dimension window = Toolkit.getDefaultToolkit().getScreenSize();
    
    //tibo
    public static String server;
    
    
    public MainView(){
        initComponents();
    }
    

    private void initComponents() {
        
        setSize(width,height);
        setTitle("Projekt zespołowy");
        
        titlePanel = new JPanel();
        titleLabel = new JLabel("MEDIA SERVER");
        mainPanel = new MainPanel();
        sharePanel = new SharePanel();
    
        
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(titlePanel);
        add(mainPanel);
        
        
        titlePanel.setMaximumSize(new Dimension(width, 100));
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBackground(Color.lightGray);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        titleLabel.setFont(new Font("Times New Roman", Font.ITALIC, 50));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
         
        control = new Controller(this,mainPanel,sharePanel);
        
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainView mainView = new MainView();
                mainView.setLocationRelativeTo(null);
                mainView.setVisible(true);
                mainView.setDefaultCloseOperation(MainView.EXIT_ON_CLOSE);
                Client client = new Client();
                         
            }
        });
    }
}
