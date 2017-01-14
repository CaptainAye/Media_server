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
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.nio.file.Path;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

/**
 *
 * @author Natalia
 */
public class FilesPanel extends JTabbedPane{
    
    private final int width = 1024;
    private final int height = 668;
    
    private JPanel audioPanel;
    private JPanel videoPanel;
    private JPanel imagePanel;
    private JLabel audioLabel;
    private JLabel videoLabel;
    private JLabel imageLabel;
    private JList audioList;
    private JList videoList;
    private JList imageList;
    
    private DefaultListModel audioModel;
    private DefaultListModel videoModel;
    private DefaultListModel imageModel;
    
    
    FilesPanel(){
        initComponents();
    }
    
    private void initComponents(){
        setSize(width,height);
        setMaximumSize(new Dimension(width,height));
        //setBorder(BorderFactory.createLineBorder(Color.gray));
        
        audioPanel = new JPanel();
        audioPanel.setMaximumSize(new Dimension(width, height-60));
        audioPanel.setLayout(new BoxLayout(audioPanel, BoxLayout.Y_AXIS));
        videoPanel = new JPanel();
        videoPanel.setMaximumSize(new Dimension(width, height-60));
        videoPanel.setLayout(new BoxLayout(videoPanel, BoxLayout.Y_AXIS));
        imagePanel = new JPanel();
        imagePanel.setMaximumSize(new Dimension(width, height-60));
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));

        audioModel = new DefaultListModel();
        videoModel = new DefaultListModel();
        imageModel = new DefaultListModel();
        
        audioList = new JList(audioModel);
        audioList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        audioList.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        
        videoList = new JList(videoModel);
        videoList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        videoList.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        
        imageList = new JList(imageModel);
        imageList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        imageList.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        
        
        audioLabel = new JLabel("MUZYKA",SwingConstants.CENTER);
        audioLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        audioLabel.setPreferredSize(new Dimension(305, 30));
        
        videoLabel = new JLabel("FILMY",SwingConstants.CENTER);
        videoLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        videoLabel.setPreferredSize(new Dimension(305, 30));
        
        imageLabel = new JLabel("OBRAZY",SwingConstants.CENTER);
        imageLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        imageLabel.setPreferredSize(new Dimension(305, 30));
        
        
        
        
        addTab("MUZYKA",audioPanel);
        setTabComponentAt(0, audioLabel);
        setMnemonicAt(0, KeyEvent.VK_1);
        
        addTab("FILMY",videoPanel);
        setTabComponentAt(1, videoLabel);
        setMnemonicAt(1, KeyEvent.VK_2);
        
        addTab("OBRAZY",imagePanel);
        setTabComponentAt(2, imageLabel);
        setMnemonicAt(2, KeyEvent.VK_3);
        
        audioPanel.add(new JScrollPane(audioList));
        videoPanel.add(new JScrollPane(videoList));
        imagePanel.add(new JScrollPane(imageList));
        
        
    }
    
    public void streamMusic(MouseAdapter adapter) {
        audioList.addMouseListener(adapter);      
    }
    public void streamVideo(MouseAdapter adapter) {
        videoList.addMouseListener(adapter);       
    }
    public void streamImage(MouseAdapter adapter) {
        imageList.addMouseListener(adapter);    
    }
    
    public DefaultListModel getAudioModel(){
        return audioModel;
    }
    public DefaultListModel getVideoModel(){
        return videoModel;
    }
    public DefaultListModel getImageModel(){
        return imageModel;
    }
    
    public JList getAudioList(){
        return audioList;
    }
    public JList getVideoList(){
        return videoList;
    }
    public JList getImageList(){
        return imageList;
    }
    
}
