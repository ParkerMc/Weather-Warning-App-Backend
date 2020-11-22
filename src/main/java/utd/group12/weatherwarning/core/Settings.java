package utd.group12.weatherwarning.core;

import utd.group12.weatherwarning.data.DataSetting;
import utd.group12.weatherwarning.data.IDataSettings;
import utd.group12.weatherwarning.errors.BadRequestError;
import utd.group12.weatherwarning.errors.NotFoundError;

/**
 * Handles everything related to settings
 */
public class Settings {
	private final Core core;
	private IDataSettings data;
	
	public Settings(Core core) {
		this.core = core;
	}
	
	/**
	 * When everything is starting up
	 */
	void start() {
		this.data = core.data.getSettings();
	}
	    
    /**
	 * Creates the settings for a user
	 * 
	 * @param username			the username to create settings for
	 */
	public void create(String username) {		
		this.data.create(username.toLowerCase());
	}
	
	/**
	 * Gets the requested user's settings
	 * 
	 * @param username			the username to get settings for 
	 * @return					the user's settings
	 * @throws NotFoundError	If the settings were not found
	 */
	public DataSetting get(String username) throws NotFoundError {
		return this.data.get(username.toLowerCase());
	}
	
	/**
	 * Updates a the user's settings
	 * 
	 * @param username				the user's username
	 * @param darkMode				the user's new dark mode setting
	 * @param useGPS				the user's new GPS setting
	 * @param useMetric				the user's unit setting
	 * @param mainLocation			the user's new main location
	 * @throws BadRequestError 		if the data is invalid
	 * @throws NotFoundError 		if the user's settings were not found
	 */
	public void update(String username, Boolean darkMode, Boolean useGPS, Boolean useMetric, Integer mainLocation) throws NotFoundError, BadRequestError {
		if(mainLocation != null && (mainLocation.intValue() < 0 || mainLocation.intValue() > 3)) {
			throw new BadRequestError("mainLocation must be between 0 and 3 inclusively");
		}
		
		this.data.update(username.toLowerCase(), darkMode, useGPS, useMetric, mainLocation);
	}
}
