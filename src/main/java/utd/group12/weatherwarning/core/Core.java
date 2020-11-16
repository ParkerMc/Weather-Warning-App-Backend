package utd.group12.weatherwarning.core;

import utd.group12.weatherwarning.core.google.Google;
import utd.group12.weatherwarning.data.IDataServer;
import utd.group12.weatherwarning.data.json.DataServer;

public class Core {
	public static final Core instance = new Core();

	public final Google google;
	
	public final Tokens tokens;
	public final Users users;
	
	final IDataServer data;
			
	private Core() {
		this.data = new DataServer();
		
		this.google = new Google(this, this.data);
		
		this.tokens = new Tokens(this);
		this.users = new Users(this);
	}
	
	public void start() {
		this.data.start();
		this.tokens.start();
		this.users.start();
	}
	
	public void stop() {
		this.data.stop();
	}
	
	public void forceSave() {
		this.data.forceSave();
	}

}
