package utd.group12.weatherwarning.response;

/**
 * Used to check if the user is logged in
 */
public class LoggedinResponse {
	private final boolean loggedin;
	
	/**
	 * Creates the logged in responce
	 * 
	 * @param loggedin	if the user is logged in or not
	 */
	public LoggedinResponse(boolean loggedin) {
		this.loggedin = loggedin;
	}

	public boolean isLoggedin() {
		return loggedin;
	}
}
