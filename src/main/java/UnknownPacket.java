

public class UnknownPacket implements Packet {
	
	byte[] msg;
	
	public UnknownPacket(byte[] msg){
		this.msg = msg;
	}
	
	@Override
	public byte[] toBytes() {
		// TODO Auto-generated method stub
		return msg;
	}

}
