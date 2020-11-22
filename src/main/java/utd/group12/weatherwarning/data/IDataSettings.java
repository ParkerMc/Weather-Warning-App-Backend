package utd.group12.weatherwarning.data;

import utd.group12.weatherwarning.errors.NotFoundError;

/**
 * Interface for the class that handles all setting related data 
 */
public interface IDataSettings {
	/**
	 * Creates settings for a user in the data system 
	 * 
	 * @param username			the user's username
	 */
	public void create(String username);
	
	/**
	 * Gets the settings for a user
	 * 
	 * @param username			the username to get settings for
	 * @return					the settings
	 * @throws NotFoundError 	if the settings where not found for the user
	 */
	public DataSetting get(String username) throws NotFoundError;

	/**
	 * Updates settings
	 * 
	 * @param username			The user's username
	 * @param darkMode			if dark mode is enabled
	 * @param useGPS			if GPS permission has been granted
	 * @param useMetric			if units should be dispaded in metric
	 * @param mainLocation		the main location to show on current weather
	 * @throws NotFoundError	if the settings were not found for the user
	 */
	public void update(String username, Boolean darkMode, Boolean useGPS, Boolean useMetric, Integer mainLocation) throws NotFoundError;
}
