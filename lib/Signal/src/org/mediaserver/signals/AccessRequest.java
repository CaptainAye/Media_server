/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.signals;

import org.mediaserver.interfaces.Signalizable;

/**
 *
 * @author Natalka
 */
public class AccessRequest extends Signalizable{
    public AccessRequest (Integer id)
    {
        setId(id);
    }
}
