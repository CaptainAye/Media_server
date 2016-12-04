/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.generator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

/**
 *
 * @author Tomek
 */
public class IdentificationNumberGenerator {
    private static Integer id = 0 ;
    public static Integer generateId(){
        try{
            FileInputStream fileStream = new FileInputStream("id.ser");
            ObjectInputStream readStream = new ObjectInputStream(fileStream);
            id =(Integer) readStream.readObject();
        } catch (ClassNotFoundException | IOException e){
            id=0;
            Random generator = new Random();
            for(int i=0;i<8;i++){
                id += ((generator.nextInt()%10)+1) *(int) Math.pow(10, i);    
            }
            FileOutputStream fileStream = new FileOutputStream("id.ser");
            ObjectOutputStream writeStream = new ObjectOutputStream(fileStream);
            writeStream.writeObject(id);
        } finally{
            return id;   
        }
    }
}
