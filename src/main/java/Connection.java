
public interface Connection<T> {
	
	public boolean connect() throws Exception;
	
	public boolean send(T msg);
	public T receive();

}
