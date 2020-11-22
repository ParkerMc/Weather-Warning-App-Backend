package utd.group12.weatherwarning.data.json;

import java.util.HashMap;
import java.util.Map;

import utd.group12.weatherwarning.core.Core;
import utd.group12.weatherwarning.data.DataSetting;
import utd.group12.weatherwarning.data.IDataSettings;
import utd.group12.weatherwarning.errors.NotFoundError;

public class DataSettings implements IDataSettings{
	private static final DataSetting default_settings = new DataSetting(false, true, false, 0);
	
	Map<String, DataSetting> settings = new HashMap<String, DataSetting>();
	
	@Override
	public void create(String username) {
		settings.put(username, default_settings); // Add the default settings
		Core.instance.forceSave();	// and save
	}

	/**
	 * Gets the settings for a user
	 * 
	 * @param username			the username to get settings for
	 * @return					the settings
	 * @throws NotFoundError 	if the user's settings are not found
	 */
	@Override
	public DataSetting get(String username) throws NotFoundError {
		DataSetting setting = settings.get(username);
		if(setting == null) {
			throw new NotFoundError("User not found.");
		}
		return setting;
	}
	
	
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
	@Override
	public void update(String username, Boolean darkMode, Boolean useGPS, Boolean useMetric, Integer mainLocation) throws NotFoundError {
		DataSetting setting = this.get(username);
		
		boolean newDarkMode = setting.isDarkMode();
		boolean newUseGPS = setting.isUseGPS();
		boolean newUseMetric = setting.isUseMetric();
		int newMainLocation = setting.getMainLocation();
		
		if(darkMode != null) {
			newDarkMode = darkMode.booleanValue();
		}
		
		if(useGPS != null) {
			newUseGPS = useGPS.booleanValue();
		}
		
		if(useMetric != null) {
			newUseMetric = useMetric.booleanValue();
		}
		
		if(mainLocation != null) {
			newMainLocation = mainLocation.intValue();
		}
		
		this.settings.put(username, new DataSetting(newDarkMode, newUseGPS, newUseMetric, newMainLocation));
		Core.instance.forceSave();
		
	}
}
