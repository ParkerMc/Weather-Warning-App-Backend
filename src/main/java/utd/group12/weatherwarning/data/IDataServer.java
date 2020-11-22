package utd.group12.weatherwarning.data;

/**
 * Interface for the class that handles the main part of our data
 */
public interface IDataServer {
	/**
	 * Starts the data service if needed
	 */
	public void start();
	
	/**
	 * Stops and cleans up the data service if needed
	 */
	public void stop();
	
	/**
	 * Forces the data service to save (if it hasn't already)
	 */
	public void forceSave();
	
	/**
	 * Gets the info data handler
	 * 
	 * @return	the info data handler
	 */
	public IDataInfo getInfo();
	
	/**
	 * Gets the user data handler
	 * 
	 * @return	the user data handler
	 */
	public IDataUsers getUsers();
	
	/**
	 * Gets the token data handler
	 * 
	 * @return	the token data handler
	 */
	public IDataTokens getTokens();

	/**
	 * Gets the settings handler
	 * 
	 * @return	the settings handler
	 */
	public IDataSettings getSettings();
}
