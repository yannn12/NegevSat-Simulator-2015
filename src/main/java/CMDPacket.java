
public abstract class CMDPacket implements Packet {
	
	public long sentTime;
	public byte opCode;
	public int priority;
	public long executeTime;
	
	public CMDPacket(){
		this.sentTime = 0;
		this.opCode = 0;
		this.priority = 0;
		this.executeTime = 0;
	}
	
	public CMDPacket(long sentTime,byte opCode,int priority,long executeTime){
		this.sentTime = sentTime;
		this.opCode = opCode;
		this.priority = priority;
		this.executeTime = executeTime;
	}
	
}
