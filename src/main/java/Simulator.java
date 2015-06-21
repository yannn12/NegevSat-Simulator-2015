
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Simulator
{
    Thread runnable;

    public Simulator()
    {
        super();
    }

    void start ( String portName, PacketsFactory factory ) throws Exception
    {
        ComConnection con = new ComConnection(portName);
        runnable = new Thread(new SerialWriter(con,factory));
        runnable.start();
        new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(;;){
					EnergyPacket packet = (EnergyPacket)con.receive();
					if(packet!= null){
						System.out.println("Energy Packet Received");
						for(EnergySamples sample :packet.eneSample){
							//String temp = "Energy Packet Sample:SendTime:"+sample.sentTime	+"Current: "+sample.current+"Voltage: "+sample.voltage;
							System.out.println("Energy Packet Sample:SendTime:"+sample.sentTime	+"Current: "+sample.current+"Voltage: "+sample.voltage);
						}
						System.out.println("Energy Packet Received");
					}
				}
				
			}
		}).start();
    }

    public static class SerialWriter implements Runnable
    {
    	PacketsFactory factory;
    	Connection<Packet> connection;
        OutputStream out;
        Scanner user_input = new Scanner( System.in );
        
        public static final int PASS = 14;
        public static final int SET_ENERGY = 15;
        public static final int SET_BATTARY = 16;
        public static final int SET_SPINRATE = 17;
        public static final int START_EXPERIMENT = 18;
        /* ---------------------------------------------------------------------------------------------- */
        public SerialWriter ( Connection<Packet> connection,PacketsFactory factory )
        {
            this.connection = connection;
            this.factory = factory;
        }
        
        private void printmenu(){
            System.out.println("\nPlease enter a command to simulate: \n");
            System.out.println("Press       T       -       Set High Temperature ");
            System.out.println("Press       E       -       Set Low Energy ");
            System.out.println("Press       C       -       Enter Connected Zone ");
            System.out.println("Press       D       -       Disconnect, Exit Connected Zone ");
            System.out.println("Press       Q       -       Exit Simulator ");
            /* --------------------------------- FOR TESTING ONLY ------------------------------------------- */
            System.out.println("\n            Ground Station Commands            \n");
            System.out.println("Press       T1      -       MOVE_TO_SAFE  ");
            System.out.println("Press       T2      -       MOVE_TO_STANDBY  ");
            System.out.println("Press       T3      -       MOVE_TO_OP  ");
            System.out.println("Press       T4      -       FORMAT_ENERGY  ");
            System.out.println("Press       T5      -       FORMAT_TEMP  ");
            System.out.println("Press       T6      -       FORMAT_STATIC  ");
            System.out.println("Press       T7      -       FORMAT_MIXED  ");
            System.out.println("Press       T8      -       SBAND_ON  ");
            System.out.println("Press       T9      -       SBAND_STANDBY  ");
            System.out.println("Press       T10     -       PAYLOAD_ON  ");
            System.out.println("Press       T11     -       PAYLOAD_STANDBY  ");
            System.out.println("Press       T12     -       THERMAL_CTRL_ON  ");
            System.out.println("Press       T13     -       THERMAL_CTRL_STANDBY  ");
            /* ---------------------------------------------------------------------------------------------- */
            System.out.println("\n            New Commands            \n");
            System.out.println("Press       P			-       PASS  ");
            System.out.println("Press       NE (Energy Value)	-       SET_ENERGY  ");
            System.out.println("Press       NB (Battary Value)	-       SET_BATTARY  ");
            System.out.println("Press       NS (Spinrate Value)	-       SET_SPINRATE  ");
            System.out.println("Press       NSE			-       START_EXPERIMENT  ");
        }
        
        private CMDPacket parse(String command){
            byte opCode =0;
            int priority = 1;
            long sentTime = 0;
            long executeTime = 0;
            System.out.println("\nYou have entered: "+command);
            String[] params = command.split(" ");
            try{
	            if(command.equalsIgnoreCase("T")){
	            	opCode = 100;
	            }
	            else if(command.equalsIgnoreCase("E")){
	                opCode = 101;
	            }
	            else if(command.equalsIgnoreCase("C")){
	                opCode = 102;
	            }
	            else if(command.equalsIgnoreCase("D")){
	                opCode = 103;
	            }
	            /* --------------------------------- FOR TESTING ONLY ------------------------------------------- */
	            else if(command.equalsIgnoreCase("T1")){
	                opCode = 1;
	            }
	            else if(command.equalsIgnoreCase("T2")){
	                opCode = 2;
	            }
	            else if(command.equalsIgnoreCase("T3")){
	                opCode = 3;
	            }
	            else if(command.equalsIgnoreCase("T4")){
	                opCode = 4;
	            }
	            else if(command.equalsIgnoreCase("T5")){
	                opCode = 5;
	            }
	            else if(command.equalsIgnoreCase("T6")){
	                opCode = 6;
	            }
	            else if(command.equalsIgnoreCase("T7")){
	                opCode = 7;
	            }
	            else if(command.equalsIgnoreCase("T8")){
	                opCode = 8;
	            }
	            else if(command.equalsIgnoreCase("T9")){
	                opCode = 9;
	                
	            }
	            else if(command.equalsIgnoreCase("T10")){
	                opCode = 10;
	            }
	            else if(command.equalsIgnoreCase("T11")){
	                opCode = 11;
	            }
	            else if(command.equalsIgnoreCase("T12")){
	                opCode = 12;
	            }
	            else if(command.equalsIgnoreCase("T13")){
	                opCode = 13;
	            }
	            else if(command.equalsIgnoreCase("P")){
	                opCode = PASS;
	            }
	            else if(params.length>=2&& params[0].equalsIgnoreCase("NE")){
	            	opCode = SET_ENERGY;
	            	priority = Integer.parseInt(params[1]);
	            	
	            }
	            else if(params.length>=2&& params[0].equalsIgnoreCase("NB")){
	            	opCode = SET_BATTARY;
	            	priority = Integer.parseInt(params[1]);
	            	
	            }
	            else if(params.length>=2&& params[0].equalsIgnoreCase("NS")){
	            	opCode = SET_SPINRATE;
	            	priority = Integer.parseInt(params[1]);
	            	
	            }
	            else if(command.equalsIgnoreCase("NSE")){
	            	opCode = START_EXPERIMENT;
	            	
	            }
	            /* ---------------------------------------------------------------------------------------------- */
	            else{
	                return null;
	            }
            }
            catch(NumberFormatException exp){
            	return null;
            }
            
            return factory.createCMDPacket(12332, opCode, priority, 0);
        }
        
        public void run ()
        {
            byte[] bytes = null;
            try{
            	connection.connect();
                while (true)
                {
                	this.printmenu();
                    String command = "";
                   user_input.useDelimiter("(\r\n)|(\n)");
                    command = user_input.next( );
                    System.out.println("\nYou have entered: "+command);
                    if (command.equalsIgnoreCase("Q")){
                        System.out.println("Exiting . . . ");
                        break;
                    }
                    
                    CMDPacket packet =  this.parse(command);
                    if(packet == null){
                    	System.out.println("Invalid Command \n");
                    	continue;
                    }
                    connection.send(packet);
                }
                //this.out.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main ( String[] args )
    {
        try
        {
        	PacketsFactory factory = new XmlPacketsFactory();
            int argSize = args.length;
            String port = "COM1";
            for(int i=0;i<args.length;i++){
            	if(args[i].equalsIgnoreCase("-port")&&i<args.length-1){
            		i++;
                    if (args[i].contains("COM")){
                        port = args[i];
                    }
            	}
                else if(args[i].equalsIgnoreCase("-p")&&i<args.length-1){
                	i++;
                	if (args[i].equalsIgnoreCase("binary")){
                		factory = new BinPacketsFactory();
                	}
                }
            }
            
            Simulator simulator = new Simulator();
            simulator.start(port,factory);
            simulator.runnable.join();
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}