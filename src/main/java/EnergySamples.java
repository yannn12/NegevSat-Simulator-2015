import java.nio.ByteBuffer;

	public  class EnergySamples{
		public long sentTime;
		public int current;
		public int voltage;
		
		public EnergySamples(byte[] msg){
			ByteBuffer msgBuffer = ByteBuffer.wrap(msg);
			sentTime = msgBuffer.getLong();
			current = msgBuffer.getInt();
			voltage = msgBuffer.getInt();
		}
	}