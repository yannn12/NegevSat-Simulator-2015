import java.nio.charset.StandardCharsets;


public class XmlCMDPacket extends CMDPacket {
	
	public XmlCMDPacket(long sentTime,byte opCode,int priority,long executeTime) {
		super(sentTime,opCode,priority,executeTime);
	}
	
	@Override
	public byte[] toBytes() {
		String result =
                "<?xml version=\"1.0\"?>" +
                        "<packet>" +
                        "<upstreamPacket time=\""+this.sentTime+"\">" +
                        "<mission time=\""+this.executeTime+"\" opcode=\""+this.opCode+"\" priority=\""+this.priority+"\"/>" +
                        "</upstreamPacket>" +
                        "</packet>";
		return result.getBytes(StandardCharsets.UTF_8);
	}

}
