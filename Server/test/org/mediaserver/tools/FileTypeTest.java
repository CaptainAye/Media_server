/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.tools;

import java.nio.file.Paths;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mediaserver.files.FileType;
/**
 *
 * @author Tomek
 */
public class FileTypeTest {
    
    @Test
    public void TestFileType(){
        String [] mockNames = {"get.avi","get.jpg","get.mp3"};
        FileType [] expectedResult = {FileType.VIDEO,FileType.IMAGE,FileType.AUDIO};
        
        for (int i=0; i< mockNames.length;i++){
            assertThat(FileType.getFileType(Paths.get(mockNames[i])),Is.is(expectedResult[i]) );
        }
    }
    
}
