
public class XmlPacketsFactory implements PacketsFactory {
	
	
	
	
	@Override
	public CMDPacket createCMDPacket(long sentTime,byte opCode,int priority,long executeTime) {
		return new XmlCMDPacket(sentTime, opCode, priority, executeTime);
	}

}
