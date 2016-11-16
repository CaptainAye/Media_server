/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.tools;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tomek
 */
public class ValidatorTest {
    
    public ValidatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of validateIpV4 method, of class Validator.
     */
    @Test
    public void testValidateIpV4() {
        System.out.println("validateIpV4");
        String ip = "192.168.0.18";
        String fake_ip="256.1232.0.10";
        Boolean expResult = true;
        
        Boolean result = Validator.validateIpV4(ip);
        assertEquals(expResult, result);
        result = Validator.validateIpV4(fake_ip);
        assertEquals(false, result);
    }
    
}
