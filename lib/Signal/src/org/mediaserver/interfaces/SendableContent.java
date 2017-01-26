/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.interfaces;

/**
 *
 * @author Tomek
 */
public interface SendableContent {
    Integer sendContent(String fileName);
    Integer receiveContent(String fileName);
    
}
