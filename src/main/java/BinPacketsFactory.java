
public class BinPacketsFactory implements PacketsFactory {

	@Override
	public CMDPacket createCMDPacket(long sentTime, byte opCode, int priority,
			long executeTime) {
		return new BinCMDPacket(sentTime, opCode, priority, executeTime);
	}

	

}
