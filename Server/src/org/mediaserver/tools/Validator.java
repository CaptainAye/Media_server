/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Tomek
 */
public class Validator {
    public static Boolean validateIpV4(String ip)
    {
        String regexIpV4 = "\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b";
        Pattern ipv4 = Pattern.compile(regexIpV4);
        Matcher match = ipv4.matcher(ip);
        return match.find();
    }
}
