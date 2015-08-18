import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Vector;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;


public class ComConnection implements Connection<Packet> {
	
	String portName;
	SerialPort serialPort;
	OutputStream outStream;
	InputStream inStream;
	
	public ComConnection(String port) {
		this.portName = port;
	}
	
	@Override
	public boolean connect() throws Exception{
        CommPortIdentifier portIdentifier = null;

		portIdentifier = CommPortIdentifier.getPortIdentifier(portName);

        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
            return false;
        }
        CommPort commPort = null;
		commPort = portIdentifier.open(this.getClass().getName(),2000);

        if ( !(commPort instanceof SerialPort) )
        {
        	System.out.println("Error: Port is currently in use");
            return false;
        }
        serialPort = (SerialPort) commPort;

		serialPort.setSerialPortParams(19200,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);

        serialPort.setFlowControlMode(serialPort.FLOWCONTROL_RTSCTS_OUT|serialPort.FLOWCONTROL_RTSCTS_IN);

        outStream = serialPort.getOutputStream();
        inStream = serialPort.getInputStream();
        return true;
	}

	@Override
	public boolean send(Packet msg) {
		byte[] array = msg.toBytes();
		array = removeDelimiter(array);
		try {
			outStream.write(array, 0, array.length);

	        this.outStream.write(10);
	
	        this.outStream.flush();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	private byte[] removeDelimiter(byte[] msg)
	{
		Vector<Byte> resultVec = new Vector<Byte>();
		for(int i=0;i<msg.length;i++){
			switch(msg[i]){
				case 10:
					resultVec.add((byte) 11);
					resultVec.add((byte) 12);
					break;
				case 11:
					resultVec.add((byte) 11);
					resultVec.add((byte) 11);
				default:
					resultVec.add((byte) msg[i]);
					break;
			}
		}
		byte[] result = new byte[resultVec.size()];
		for(int i=0;i<result.length;i++){
			result[i] = resultVec.get(i);
		}
		return result;
	}

	@Override
	public Packet receive() {
		byte[] buffer = new byte[1024];
		byte[] temp = new byte[1];
		int i=0;
		int len=0;
		try{
			while(len!=-1){
				len = inStream.read(temp, 0, 1);
				if(len!=1)
					continue;
				if(temp[0]!=10){
					buffer[i]= temp[0];
					i++;
				}
				else{
					return EnergyPacket.createPacket(Arrays.copyOfRange(buffer, 0,i-1));
				}
				
			}
		}
		catch(Exception exp){
			return null;
		}
		return null;
		
		/*
		
		buffer[len] = '\0';
		len++;
		String str = new String(buffer,StandardCharsets.UTF_8);
		System.out.println(str);*/
	}

}
