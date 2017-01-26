/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.communication;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.file.Paths;
import org.mediaserver.files.FileType;
import org.mediaserver.interfaces.Signalizable;

/**
 *
 * @author Tomek
 */
public class ContentReceiver implements Runnable {
    
    private Integer sendingClientId;
    private Integer portToReceive;
    private String fileName;
    
    public ContentReceiver(Integer sendingClientId,Integer portToReceive, String fileName){
        this.sendingClientId = sendingClientId;
        this.portToReceive = portToReceive;
        this.fileName = fileName;
    }
    public void run(){
        receiveContent(sendingClientId, portToReceive, fileName);
    }
    public String receiveContent(Integer sendingClientId,Integer portToReceive, String fileName){
        System.out.println("Waiting to receive content filePath: " + fileName.toString() +" on port: " + portToReceive + " from client: " + sendingClientId);
        FileType fileType = FileType.getFileType(Paths.get(fileName));
        if (fileType == FileType.AUDIO){
            return receiveAudio(sendingClientId,portToReceive, fileName);
        } else if(fileType == FileType.IMAGE){
            return receiveImage(sendingClientId,portToReceive, fileName);
        }else{
            return receiveVideo(sendingClientId,portToReceive, fileName);
        }
    }
    
    private String receiveAudio(Integer sendingClientId, Integer portToReceive, String fileName){
        String dir = "cache";
        
        String savePath =File.separator +  dir + File.separator + fileName;
        File directory = new File(dir);
        directory.mkdir();
        String returnValue = System.getProperty("user.dir")+savePath;
        System.out.println(returnValue);
        DatagramSocket socket = null;
        FileOutputStream fos = null;
        try{
            
            File file = new File(fileName);
            file.createNewFile();
            socket = new DatagramSocket(portToReceive);
            socket.setSoTimeout(1000);
            byte[] receiveData = new byte[1024];
            fos = new FileOutputStream(file);
            Integer count = 0;
            do{
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            fos.write(receivePacket.getData(),0,receivePacket.getLength());
            count = receivePacket.getLength();
            System.out.println("received count : " + count + " " + receivePacket.getOffset());
        }while(count == 1024);
        fos.close();
        } catch (SocketTimeoutException e){
            try{
                fos.close();
                socket.close();
            } catch (IOException exp){
                System.exit(-1);
            }
        } catch (SocketException e){
            returnValue = null;
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
           socket.close();
          
        }
        
        return returnValue;
    }
    
    private String receiveImage(Integer sendingClientId, Integer portToReceive, String fileName){
        //same as in audio
        return receiveAudio(sendingClientId, portToReceive, fileName);
    }
    
    private String receiveVideo(Integer sendingClientId, Integer portToReceive, String fileName){
        return null;
    }
    
}
