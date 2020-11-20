package utd.group12.weatherwarning.data;

/**
 * Stores all the current weather data
 */
public class DataWeather {
	final double temp; 			// C
	final double windSpeed; 	// km/h
	final int pressure;			// Pa
	final double humidity;		// %
	final double low;			// C
	final double high;			// C
	final int rainProbability;	// %
	
	/**
	 * @param temp
	 * @param windSpeed
	 * @param pressure
	 * @param humidity
	 * @param low
	 * @param high
	 * @param rainProbability
	 */
	public DataWeather(double temp, double windSpeed, int pressure, double humidity,
			double low, double high, int rainProbability) {
		this.temp = temp;
		this.windSpeed = windSpeed;
		this.pressure = pressure;
		this.humidity = humidity;
		this.low = low;
		this.high = high;
		this.rainProbability = rainProbability;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(high);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(humidity);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(low);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + pressure;
		result = prime * result + rainProbability;
		temp = Double.doubleToLongBits(this.temp);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(windSpeed);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		DataWeather other = (DataWeather) obj;
		if (Double.doubleToLongBits(high) != Double.doubleToLongBits(other.high))
			return false;
		if (Double.doubleToLongBits(humidity) != Double.doubleToLongBits(other.humidity))
			return false;
		if (Double.doubleToLongBits(low) != Double.doubleToLongBits(other.low))
			return false;
		if (pressure != other.pressure)
			return false;
		if (rainProbability != other.rainProbability)
			return false;
		if (Double.doubleToLongBits(temp) != Double.doubleToLongBits(other.temp))
			return false;
		if (Double.doubleToLongBits(windSpeed) != Double.doubleToLongBits(other.windSpeed))
			return false;
		return true;
	}

	/**
	 * @return the temp
	 */
	public double getTemp() {
		return temp;
	}

	/**
	 * @return the windSpeed
	 */
	public double getWindSpeed() {
		return windSpeed;
	}

	/**
	 * @return the pressure
	 */
	public int getPressure() {
		return pressure;
	}

	/**
	 * @return the humidity
	 */
	public double getHumidity() {
		return humidity;
	}

	/**
	 * @return the low
	 */
	public double getLow() {
		return low;
	}
	
	/**
	 * @return the high
	 */
	public double getHigh() {
		return high;
	}

	/**
	 * @return the rainProbability
	 */
	public int getRainProbability() {
		return rainProbability;
	}
	
	/**
	 * Stores the location info
	 */
	public static class WeatherInfo{
		final String gridId;
		final int gridX;
		final int gridY;
		final String radarStation;
		final String stationIdentifier;
		final String stationName;
		
		/**
		 * @param gridId				the gridID
		 * @param gridX					the x grid number
		 * @param gridY					the y grid number
		 * @param radarStation			the radar station
		 * @param stationIdentifier		the weather station identifier
		 * @param stationName			the weather station name
		 */
		public WeatherInfo(String gridId, int gridX, int gridY, String radarStation, String stationIdentifier,
				String stationName) {
			this.gridId = gridId;
			this.gridX = gridX;
			this.gridY = gridY;
			this.radarStation = radarStation;
			this.stationIdentifier = stationIdentifier;
			this.stationName = stationName;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((gridId == null) ? 0 : gridId.hashCode());
			result = prime * result + gridX;
			result = prime * result + gridY;
			result = prime * result + ((radarStation == null) ? 0 : radarStation.hashCode());
			result = prime * result + ((stationIdentifier == null) ? 0 : stationIdentifier.hashCode());
			result = prime * result + ((stationName == null) ? 0 : stationName.hashCode());
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
			WeatherInfo other = (WeatherInfo) obj;
			if (gridId == null) {
				if (other.gridId != null)
					return false;
			} else if (!gridId.equals(other.gridId))
				return false;
			if (gridX != other.gridX)
				return false;
			if (gridY != other.gridY)
				return false;
			if (radarStation == null) {
				if (other.radarStation != null)
					return false;
			} else if (!radarStation.equals(other.radarStation))
				return false;
			if (stationIdentifier == null) {
				if (other.stationIdentifier != null)
					return false;
			} else if (!stationIdentifier.equals(other.stationIdentifier))
				return false;
			if (stationName == null) {
				if (other.stationName != null)
					return false;
			} else if (!stationName.equals(other.stationName))
				return false;
			return true;
		}

		/**
		 * @return the gridId
		 */
		public String getGridId() {
			return gridId;
		}

		/**
		 * @return the gridX
		 */
		public int getGridX() {
			return gridX;
		}

		/**
		 * @return the gridY
		 */
		public int getGridY() {
			return gridY;
		}

		/**
		 * @return the radarStation
		 */
		public String getRadarStation() {
			return radarStation;
		}

		/**
		 * @return the stationIdentifier
		 */
		public String getStationIdentifier() {
			return stationIdentifier;
		}

		/**
		 * @return the stationName
		 */
		public String getStationName() {
			return stationName;
		}		
	}
}
