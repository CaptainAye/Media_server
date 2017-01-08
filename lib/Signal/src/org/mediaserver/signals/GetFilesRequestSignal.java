/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.signals;

import org.mediaserver.interfaces.Signalizable;

/**
 *
 * @author Tomek
 */
public class GetFilesRequestSignal extends Signalizable {
    
    public GetFilesRequestSignal(Integer callerId){
        setId(callerId);
    }  
}
