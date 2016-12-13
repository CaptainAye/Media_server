/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.communication;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import org.mediaserver.files.FileType;

/**
 *
 * @author Tomek
 */
public class FileSearcher {
   private static final String EXTENSIONS = FileType.getExtensions();//".avi;.jpg;.mp3";
   private static final HashMap<Path,String> FILES_MAP = new HashMap<Path,String>();
   
   private FileSearcher(){};
   
   public static HashMap<Path,String> searchDirectories(){
       Iterable<Path> dirs = FileSystems.getDefault().getRootDirectories();
       for (Path path : dirs){
           DirectoryTreeSearcher searcher = new DirectoryTreeSearcher();
           try{
               Files.walkFileTree(path, searcher);
           } catch (IOException e){
               e.printStackTrace();
               continue;
           }
       }
    return FILES_MAP;
   }
    
   private static class DirectoryTreeSearcher extends SimpleFileVisitor<Path>{
        private String[] extensionsArray = FileSearcher.EXTENSIONS.split(";");
        @Override
        public FileVisitResult visitFile(Path file,BasicFileAttributes attr){
            for (String extension: extensionsArray){
                if (file.getFileName().toString().contains(extension)){
                    System.out.println(file);
                    FILES_MAP.put( file , file.getFileName().toString());
                }
            }
        return FileVisitResult.CONTINUE;
        }
        
        @Override
        public FileVisitResult visitFileFailed(Path file, IOException e){
            System.err.println(e);
            return FileVisitResult.CONTINUE;
        }
    }   
}

