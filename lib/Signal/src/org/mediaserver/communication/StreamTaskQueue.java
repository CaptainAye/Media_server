/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.communication;

import java.util.LinkedList;

/**
 *
 * @author Tomek
 */
public class StreamTaskQueue {
    private static StreamTaskQueue streamTaskQueue = new StreamTaskQueue();
    private LinkedList<QueueTask> queue = new LinkedList<>();
    public static class QueueTask{
        private final Integer requestorClientId;
        private final Integer targetClientId;
        private final String contentPath;
        private final Integer clientPort;
        private final String fileName;
        
        public QueueTask(Integer requestorClientId,Integer targetClientId, Integer clientPort, String contentPath, String fileName){
            this.requestorClientId = requestorClientId;
            this.targetClientId = targetClientId;
            this.contentPath = contentPath;
            this.clientPort = clientPort;
            this.fileName = fileName;
        }
        
        public String getFileName(){
            return fileName;
        }
        
        public Integer getRequestorClientId(){
        return requestorClientId;
        }
        
        public Integer getTargetClientId(){
            return targetClientId;
        }
        
        public Integer getClientPort(){
            return clientPort;
        }
        
        public String getContentPath(){
            return contentPath;
        }
    }
    private StreamTaskQueue(){
    }
    public void enqueue(QueueTask newTask){
        synchronized(this){
            queue.push(newTask);
            if (queue.size() == 1){
                notifyAll();
            }
        }
    }
    
    public QueueTask dequeue(){
        synchronized(this){
            while(queue.size() == 0){
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            
            return queue.removeLast();
        }
    }
    
    public static synchronized StreamTaskQueue getSignalQueue(){
        return streamTaskQueue;
    }
    
    public Integer size(){
        return queue.size();
    }
    
}
