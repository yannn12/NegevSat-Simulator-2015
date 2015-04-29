
public interface PacketsFactory {
	public CMDPacket createCMDPacket(long sentTime,byte opCode,int priority,long executeTime);
		
	
	
}
