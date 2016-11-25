/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;

/**
 *
 * @author Tomek
 */
public class FileSearcher {
   private static String extensions = ".avi;.jpg;.mp3";
   private static HashMap<String,Path> filesMap = new HashMap<String,Path>();
   
   private FileSearcher(){};
   
   public static HashMap<String,Path> searchDirectories(){
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
       for (String name: filesMap.keySet()){
           System.out.println(name);
       }
    return filesMap;
   }
    
   private static class DirectoryTreeSearcher extends SimpleFileVisitor<Path>{
        private String[] extensionsArray = FileSearcher.extensions.split(";");
        @Override
        public FileVisitResult visitFile(Path file,BasicFileAttributes attr){
            for (String extension: extensionsArray){
                if (file.getFileName().toString().contains(extension)){
                    System.out.println(file);
                    filesMap.put(file.getFileName().toString(), file);
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

