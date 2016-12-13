/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.communication;

import com.sun.jna.NativeLibrary;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.net.DatagramSocket;
import java.nio.file.Path;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import org.mediaserver.files.FileType;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;
/**
 *
 * @author Tomek
 */
public class ContentSender {
    
    private static List<Integer> portList = new ArrayList<Integer>(49151);
 
    private static String formatRtspStream(String serverAddress, int serverPort, String id) {
        StringBuilder sb = new StringBuilder(60);
        sb.append(":sout=#rtp{sdp=rtsp://");
        //sb.append(serverAddress);
        sb.append(':');
        sb.append(serverPort);
        sb.append('/');
        sb.append(id);
        sb.append("}");
        return sb.toString();
    }
    
    private static String buildMRL (Path file){
        return "file:///" + file.toString();
    }
    
    private static void streamVideo(String remoteIp, Integer remotePort, Path file){
        String media = buildMRL(file);
        String options = formatRtspStream(remoteIp, remotePort,file.getFileName().toString());
        NativeLibrary.addSearchPath("libvlc", "E:\\Programy\\VLC-64bit");
        MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
        HeadlessMediaPlayer mediaPlayer = mediaPlayerFactory.newHeadlessMediaPlayer();
        mediaPlayer.playMedia(media,
            options,
            ":no-sout-rtp-sap",
            ":no-sout-standard-sap",
            ":sout-all",
            ":sout-keep"
        );
        try{
        Thread.currentThread().join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public static void send(String remoteIp, Integer remotePort, Path file){
        FileType fileType = FileType.getFileType(file);
        switch (fileType){
            case VIDEO:
                streamVideo(remoteIp, remotePort, file);
                break;
            case AUDIO:
                break;
            case IMAGE:
                break;
            
        }
       /* File fileToSend = file.toFile();
        byte[] data = new byte[4096];
        int count = 0;
        try{
            FileInputStream fileStream = new FileInputStream(fileToSend);
            DatagramSocket socket = new DatagramSocket();
            while (count != -1){
                System.out.println("Sending next count: " + count + "bytes");
                count = fileStream.read(data);
                DatagramPacket packetToSend = new DatagramPacket(data,data.length,InetAddress.getByName(remoteIp),remotePort);
                socket.send(packetToSend);
            }
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }*/
            
    }
}
