

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class EnergyPacket implements Packet{
	
	public List<EnergySamples> eneSample;
	
	
	public static EnergyPacket createPacket(byte[] msg){
		if(msg.length<1||msg.length!=(msg[0]*16+1) )
			return null;
		
		EnergyPacket result = new EnergyPacket();
		
		int count = msg[0];
		
		for(int i=0;i<count;i++){
			byte[] sample = Arrays.copyOfRange(msg, 1+i*16,1+(i+1)*16);
			result.eneSample.add(new EnergySamples(sample));
		}
		
		return result;
	}
	
	public EnergyPacket(){
		this.eneSample = new ArrayList();
		
		
		
	}

	@Override
	public byte[] toBytes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
