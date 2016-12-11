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
    
    private JPanel musicPanel;
    private JPanel videoPanel;
    private JPanel photoPanel;
    private JLabel musicLabel;
    private JLabel videoLabel;
    private JLabel photoLabel;
    private JList musicList;
    private JList videoList;
    private JList photoList;
    
    private DefaultListModel musicModel;
    private DefaultListModel videoModel;
    private DefaultListModel photoModel;
    
    
    FilesPanel(){
        initComponents();
    }
    
    private void initComponents(){
        setSize(width,height);
        setMaximumSize(new Dimension(width,height));
        //setBorder(BorderFactory.createLineBorder(Color.gray));
        
        musicPanel = new JPanel();
        musicPanel.setMaximumSize(new Dimension(width, height-60));
        musicPanel.setLayout(new BoxLayout(musicPanel, BoxLayout.Y_AXIS));
        videoPanel = new JPanel();
        videoPanel.setMaximumSize(new Dimension(width, height-60));
        videoPanel.setLayout(new BoxLayout(videoPanel, BoxLayout.Y_AXIS));
        photoPanel = new JPanel();
        photoPanel.setMaximumSize(new Dimension(width, height-60));
        photoPanel.setLayout(new BoxLayout(photoPanel, BoxLayout.Y_AXIS));

        musicModel = new DefaultListModel();
        videoModel = new DefaultListModel();
        photoModel = new DefaultListModel();
        
        musicList = new JList(musicModel);
        musicList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        musicList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        musicList.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        
        videoList = new JList(videoModel);
        videoList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        videoList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        videoList.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        
        photoList = new JList(photoModel);
        photoList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        photoList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        photoList.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        
        
        musicLabel = new JLabel("MUZYKA",SwingConstants.CENTER);
        musicLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        musicLabel.setPreferredSize(new Dimension(305, 30));
        
        videoLabel = new JLabel("FILMY",SwingConstants.CENTER);
        videoLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        videoLabel.setPreferredSize(new Dimension(305, 30));
        
        photoLabel = new JLabel("OBRAZY",SwingConstants.CENTER);
        photoLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        photoLabel.setPreferredSize(new Dimension(305, 30));
        
        
        
        
        addTab("MUZYKA",musicPanel);
        setMnemonicAt(0, KeyEvent.VK_1);
        setTabComponentAt(0, musicLabel);
        
        addTab("FILMY",videoPanel);
        setTabComponentAt(1, videoLabel);
        setMnemonicAt(1, KeyEvent.VK_2);
        
        addTab("OBRAZY",photoPanel);
        setMnemonicAt(2, KeyEvent.VK_3);
        setTabComponentAt(2, photoLabel);
        
        musicPanel.add(new JScrollPane(musicList));
        videoPanel.add(new JScrollPane(videoList));
        photoPanel.add(new JScrollPane(photoList));
        
        
    }
    
    public DefaultListModel getMusicModel(){
        return musicModel;
    }
    public DefaultListModel getVideoModel(){
        return videoModel;
    }
    public DefaultListModel getPhotoModel(){
        return photoModel;
    }
    
}
