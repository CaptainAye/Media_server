/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mediaserver.app;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.mediaserver.communication.ServerReceiver;
import org.mediaserver.communication.SignalBroadcaster;
import org.mediaserver.communication.SignalSenderback;
import org.mediaserver.exceptions.BroadcastSignalNotPresentException;
import org.mediaserver.exceptions.IncorrectCommandException;
import org.mediaserver.exceptions.IncorrectIPFormatException;
import org.mediaserver.interfaces.Signalizable;
import org.mediaserver.signals.BroadcastSignal;
import org.mediaserver.tools.Validator;

/**
 *
 * @author Tomek
 */
public class Server {
    private ArrayList<BroadcastSignal> broadcastList = new ArrayList<>();
    private Integer serverNo = 1;
    //private SignalBroadcasterBack sigBroad;
    private SignalBroadcaster sigBroad;
    private Boolean readConfigFile() throws IncorrectCommandException, IncorrectIPFormatException
        {
        try {
        BufferedReader config = new BufferedReader(new FileReader("server.conf"));
        String line, command = "";
        String [] uncommentedLine, parsedLine;
        Boolean optionParser = false, commandPresence = false;
        String[] commands = {"broadcast"};
        while ((line = config.readLine()) != null)
        {
            uncommentedLine = line.split("#");
            line = uncommentedLine[0];
            line = line.trim();
            parsedLine = line.split(" ");
            if (line.equals(""))
            {
                continue;
            }
            for (int i=0; i<commands.length;i++)
            {
                if (commands[i].equals(line)) // if new command is found
                {
                    command = commands[i];
                    optionParser = true;
                    commandPresence = true;
                    break;
                }
            }
            if (optionParser == true )
            {
                optionParser = false;
                continue;
            }
            else
            {
                if (commandPresence == false)
                {
                    throw new IncorrectCommandException();
                }
                else
                {
                    switch(command)
                    {
                        case "broadcast":
                        {
                            String ipGroup = parsedLine[0];
                            String ipLocal = parsedLine[1];
                            Integer srcPort = 0;
                            Integer destPort = 0;
                            try
                            {
                                srcPort = Integer.parseInt(parsedLine[2]);
                                destPort = Integer.parseInt(parsedLine[3]);
                                
                                if (Validator.validateIpV4(ipGroup))
                                {
                                    broadcastList.add(new BroadcastSignal(ipGroup,ipLocal, srcPort, destPort, serverNo));
                                }
                                else
                                {
                                    throw new IncorrectCommandException();
                                }   
                            }catch(NumberFormatException e)
                            {
                                e.printStackTrace();
                                System.exit(-1);
                            }
                            break;
                        }
                    }
                }
            }
            
            
        }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            System.err.println("Server configuration file not present");
            System.exit(-1);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.err.println("Unexpected error while reading server.conf");
            System.exit(-1);
        }
        return true;
    }

    public Server(){
        //broadcastList = new ArrayList<>();
        try {
            readConfigFile();
            //sender = new SignalSenderback();
            //sigBroad = new SignalBroadcasterBack(sender,broadcastList);
            sigBroad = new SignalBroadcaster(broadcastList);
            ServerReceiver receiver = new ServerReceiver(10500); // port = 10500
            
            Thread signalBroadcasterThread = new Thread(sigBroad);
            Thread serverReceiverThread = new Thread(receiver);
            
            serverReceiverThread.start();
            signalBroadcasterThread.start();
        }
        catch(BroadcastSignalNotPresentException e){
            e.printStackTrace();
            System.exit(-1);
        }
        catch(IncorrectCommandException e){
            e.printStackTrace();
            System.exit(-1);
        }
        catch(IncorrectIPFormatException e){
            e.printStackTrace();
            System.exit(-1);
        }
    } 
    public static void main(String[] args) {
        Server instance = new Server();
        
    }
    
}
