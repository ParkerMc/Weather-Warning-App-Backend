package utd.group12.weatherwarning.data;

public interface IDataServer {
	public void start();
	public void stop();
	
	public IDataInfo getInfo();
}
