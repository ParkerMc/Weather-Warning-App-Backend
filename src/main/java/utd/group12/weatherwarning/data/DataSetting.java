package utd.group12.weatherwarning.data;

/**
 * The class used for the settings
 */
public class DataSetting {
	private final boolean darkMode;
	private final boolean useGPS;
	private final boolean useMetric;
	private final int mainLocation;
	
	/**
	 * Creates the settings object
	 * 
	 * @param darkMode		if dark mode is enabled
	 * @param useGPS		if using GPS for current location is enabled
	 * @param useMetric		if units should be displayed in metric
	 * @param mainLocation	the main location to show under current weather 
	 */
	public DataSetting(boolean darkMode, boolean useGPS, boolean useMetric, int mainLocation) {
		this.darkMode = darkMode;
		this.useGPS = useGPS;
		this.useMetric = useMetric;
		this.mainLocation = mainLocation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (darkMode ? 1231 : 1237);
		result = prime * result + mainLocation;
		result = prime * result + (useGPS ? 1231 : 1237);
		result = prime * result + (useMetric ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataSetting other = (DataSetting) obj;
		if (darkMode != other.darkMode)
			return false;
		if (mainLocation != other.mainLocation)
			return false;
		if (useGPS != other.useGPS)
			return false;
		if (useMetric != other.useMetric)
			return false;
		return true;
	}

	/**
	 * @return if darkMode is enabled
	 */
	public boolean isDarkMode() {
		return darkMode;
	}

	/**
	 * @return if the GPS is allowed to be used
	 */
	public boolean isUseGPS() {
		return useGPS;
	}

	/**
	 * @return if units should be displayed in metric
	 */
	public boolean isUseMetric() {
		return useMetric;
	}

	/**
	 * @return the main location to display on current weather
	 */
	public int getMainLocation() {
		return mainLocation;
	}
	
	
}
