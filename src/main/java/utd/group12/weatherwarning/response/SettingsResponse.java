package utd.group12.weatherwarning.response;

import utd.group12.weatherwarning.data.DataSetting;

/**
 * The response for when a user is returned
 */
public class SettingsResponse {
	private final boolean darkMode;
	private final boolean useGPS;
	private final boolean useMetric;
	private final int mainLocation;
	
	/**
	 * Creates the response for when the settings are returned
	 * 
	 * @param darkMode		if dark mode is enabled
	 * @param useGPS		if using GPS for current location is enabled
	 * @param useMetric		if units should be displayed in metric
	 * @param mainLocation	the main location to show under current weather 
	 */
	public SettingsResponse(boolean darkMode, boolean useGPS, boolean useMetric, int mainLocation) {
		this.darkMode = darkMode;
		this.useGPS = useGPS;
		this.useMetric = useMetric;
		this.mainLocation = mainLocation;
	}
	
	public SettingsResponse(DataSetting settings) {
		this(settings.isDarkMode(), settings.isUseGPS(), settings.isUseMetric(), settings.getMainLocation());
	}

	public boolean isDarkMode() {
		return darkMode;
	}

	public boolean isUseGPS() {
		return useGPS;
	}


	public boolean isUseMetric() {
		return useMetric;
	}


	public int getMainLocation() {
		return mainLocation;
	}
}
