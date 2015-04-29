import java.nio.ByteBuffer;


public class BinCMDPacket extends CMDPacket {
	
	public BinCMDPacket(long sentTime,byte opCode,int priority,long executeTime) {
		super(sentTime,opCode,priority,executeTime);
	}
	
	@Override
	public byte[] toBytes() {
		ByteBuffer buf = ByteBuffer.allocate(21);
		buf.putLong(this.sentTime);
		buf.put(this.opCode);
		buf.putInt(this.priority);
		buf.putLong(this.executeTime);
		return buf.array();
	}

}
