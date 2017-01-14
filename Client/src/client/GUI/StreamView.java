/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.GUI;

import client.Client;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import javax.swing.event.ChangeListener;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoundedRangeModel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Natalia
 */
public class StreamView extends JFrame{
    
    private int height = 600;
    private int width = 800;
    
    //private DefaultListModel audioModel;
    private DefaultListModel imageModel;
    private DefaultListModel videoModel;
    private JPanel audioPanel;
    private JPanel imagePanel;
    private JPanel imageView;
    private JPanel buttonsPanel;
    private JButton buttonPlay;
    private JButton buttonNext;
    private JButton buttonPrev;
    private JList audioList;
    private JProgressBar seekBar;
    private JSlider volume;
    
    private JLabel panelLabel;
    private JLabel title;
    private JLabel imageLabel;
    private JLabel videoLabel;
    
    //private BufferedImage image;
    
    
    public StreamView(){
        setSize(width,height);
    }
    public StreamView(int width, int height){
        setSize(width,height);
    }
    
    void initAudioComponents(String audioName,DefaultListModel audioModel) {
        audioPanel = new JPanel();
        audioPanel.setMaximumSize(new Dimension(width,height-100));
        audioPanel.setLayout(new BoxLayout(audioPanel, BoxLayout.Y_AXIS));
        
        title = new JLabel(audioName);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setMaximumSize(new Dimension(width,20));
        
        seekBar = new JProgressBar();
        
        buttonsPanel = new JPanel();
        buttonsPanel.setSize(new Dimension(width,100));
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setMaximumSize(new Dimension(width,100));
        
        volume = new JSlider(JSlider.HORIZONTAL,0,100,30);
        volume.setMaximum(100);
        volume.setMinimum(0);
        volume.setMajorTickSpacing(10);
        volume.setMinorTickSpacing(1);
        volume.setPaintTicks(true);
        volume.setPaintLabels(true);
        
        buttonPlay = new JButton("PAUZA");
        buttonPlay.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        buttonNext = new JButton("NASTĘPNE");
        buttonNext.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        buttonPrev = new JButton("POPRZEDNIE");
        buttonPrev.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        
        //audioModel = new DefaultListModel();
        audioList = new JList(audioModel);
        audioList.setMaximumSize(new Dimension(400,100));
        audioList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        audioList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        audioList.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        
        add(audioPanel);
        audioPanel.add(title);
        audioPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        audioPanel.add(seekBar);
        audioPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonsPanel.add(buttonPlay);
        buttonsPanel.add(buttonNext);
        buttonsPanel.add(buttonPrev);
        audioPanel.add(buttonsPanel);
        audioPanel.add(volume);
        audioPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        audioPanel.add(new JScrollPane(audioList));

        
    }
    
    void initImageComponents(BufferedImage image, String imageName) throws IOException{
        imagePanel = new JPanel();
        imagePanel.setMaximumSize(new Dimension(width,height-100));
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
        
        title = new JLabel(imageName);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setMaximumSize(new Dimension(width,20));
        
        imageView = new JPanel();
        JLabel picLabel = new JLabel(new ImageIcon(image));
        imageView.setMaximumSize(new Dimension(image.getWidth(),image.getHeight()));
        imageView.add(picLabel);
        imageView.repaint(); 
        
        buttonNext = new JButton("NASTĘPNE");
        buttonNext.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        buttonPrev = new JButton("POPRZEDNIE");
        buttonPrev.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        
        buttonsPanel = new JPanel();
        buttonsPanel.setSize(new Dimension(width,100));
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setMaximumSize(new Dimension(width,100));
        
        imageModel = new DefaultListModel();
        
        add(imagePanel);
        imagePanel.add(title);
        imagePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        imagePanel.add(imageView);
        imagePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
    }
    
    public void prevListener(ActionListener listener) {
        buttonPrev.addActionListener(listener);                                                     
    }
    public void nextListener(ActionListener listener) {
        buttonNext.addActionListener(listener);                                                     
    }
    public void playListener(ActionListener listener) {
        buttonPlay.addActionListener(listener);                                                     
    }
    public void audioListListener(MouseAdapter adapter) {
        audioList.addMouseListener(adapter);    
    }
    public void changeVolume(ChangeListener change){
        volume.addChangeListener(change);
    }
    
    public JList getAudioList(){
        return audioList;
    }
    public JSlider getVolume(){
        return volume;
    }
    
    public void setAudioTitle(String title){
        this.title.setText(title);
    }
    public void setPlayButtonTitle(String title){
        this.buttonPlay.setText(title);
    }
}
