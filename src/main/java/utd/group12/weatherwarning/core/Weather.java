package utd.group12.weatherwarning.core;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import utd.group12.weatherwarning.Utils;
import utd.group12.weatherwarning.data.DataLocation;
import utd.group12.weatherwarning.data.DataLocation.Point;
import utd.group12.weatherwarning.data.DataWeather;
import utd.group12.weatherwarning.data.DataWeather.WeatherInfo;
import utd.group12.weatherwarning.errors.InternalServerError;

/**
 * Handles everything related to weather
 */
public class Weather {
	// Cache for the weather info - only loads once
	LoadingCache<Point, WeatherInfo> infoCache = CacheBuilder.newBuilder().maximumSize(1000).build(
			new CacheLoader<Point, WeatherInfo>() {
				@Override
				public WeatherInfo load(Point key) throws IOException {
					return createWeatherInfo(key);
					}
				});
	
	// Cache for the current weather - loads every 2 mins
	LoadingCache<WeatherInfo, DataWeather> weatherCache = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.MINUTES).maximumSize(1000).build(
			new CacheLoader<WeatherInfo, DataWeather>() {
				@Override
				public DataWeather load(WeatherInfo key) throws IOException  {
					return createCurrentWeather(key);
					}
				});
	
	public Weather(Core core) {}
	
	/**
	 * When everything is starting up
	 */
	void start() {}	
	
	/**
	 * Gets the current weather for the location
	 * 
	 * @param weatherInfo			the weather info to fetch weather for
	 * @return						the current weather
	 * @throws InternalServerError	if there was an error getting the current weather from NWS
	 */
	public DataWeather getCurrentWeather(WeatherInfo weatherInfo) throws InternalServerError {
		try {
			return weatherCache.get(weatherInfo);
		} catch (ExecutionException e) {
			throw new InternalServerError("Error geting current weather", e);
		}
	}
	
	/**
	 * Creates the current weather object for the location
	 * 
	 * @param weatherInfo	the weather info to fetch weather for
	 * @return				the current weather
	 * @throws IOException	if there was an error getting the current weather from NWS
	 */
	DataWeather createCurrentWeather(WeatherInfo weatherInfo) throws IOException {
		ObservationResponse.Properties observations = Utils.GETRequest(String.format("https://api.weather.gov/stations/%s/observations/latest", weatherInfo.getStationIdentifier()), null, ObservationResponse.class)
				.properties;
		ForecastResponse.Properties forecast = Utils.GETRequest(String.format("https://api.weather.gov/gridpoints/%s/%d%%2C%d", weatherInfo.getGridId(), weatherInfo.getGridX(), weatherInfo.getGridY()), null, ForecastResponse.class)
				.properties;
		
		return new DataWeather(observations.temperature.value, observations.windSpeed.value, observations.barometricPressure.value, observations.relativeHumidity.value,
				forecast.minTemperature.values[0].value, forecast.maxTemperature.values[0].value, forecast.probabilityOfPrecipitation.values[0].value, weatherInfo.getStationName());
	}

	/**
	 * Used to parse observation response from NWS
	 */
	static class ObservationResponse{
		Properties properties;
		static class Properties{
			Prop temperature;			// C
			Prop windSpeed;				// km/h
			IntProp barometricPressure;	// Pa
			Prop relativeHumidity; 		// %
			static class Prop{
				double value;
			}
			static class IntProp{
				int value;
			}
			
		}
	}
	
	/**
	 * Used to parse forecast response from NWS
	 */
	static class ForecastResponse{
		Properties properties;
		static class Properties{
			Prop maxTemperature;				// C
			Prop minTemperature;				// C
			IntProp probabilityOfPrecipitation;	// %
			static class Prop{
				Value[] values;
				static class Value{
					double value;
				}
			}
			static class IntProp{
				Value[] values;
				static class Value{
					int value;
				}
			}
		}
	}
	
	/**
	 * Gets the weather info for a requested point
	 * 
	 * @param lat					the latitude to get info for
	 * @param lng					the longitude to get info for
	 * @return						the weather info for the location
	 * @throws InternalServerError	if there was an error getting the current weather from NWS
	 */
	public WeatherInfo getWeatherInfo(double lat, double lng) throws InternalServerError {
		try {
			return infoCache.get(new DataLocation.Point(lat, lng));
		} catch (ExecutionException e) {
			throw new InternalServerError("Error geting point info", e);
		}
	}
	
	/**
	 * Creates the weather info object for a requested point
	 * 
	 * @param lat					the latitude to get info for
	 * @param lng					the longitude to get info for
	 * @return						the weather info for the location
	 * @throws InternalServerError	if there was an error getting the current weather from NWS
	 */
	WeatherInfo createWeatherInfo(DataLocation.Point point) throws IOException {
		PointInfoResponse.Properties info = Utils.GETRequest(String.format("https://api.weather.gov/points/%f%%2C%f", point.getLat(), point.getLng()), null, PointInfoResponse.class)
				.properties;
		PointStationsResponse.Station.Properties station = Utils.GETRequest(String.format("https://api.weather.gov/points/%f%%2C%f/stations", point.getLat(), point.getLng()), null, PointStationsResponse.class)
				.features[0].properties;
		return new WeatherInfo(info.gridId, info.gridX, info.gridY, info.radarStation,
				station.stationIdentifier, station.name);
	}
	
	/**
	 * Used to parse point info response from NWS
	 */
	static class PointInfoResponse{
		Properties properties;
		static class Properties{
			String gridId;
			int gridX;
			int gridY;
			String radarStation;
		}
	}
	
	/**
	 * Used to parse point station response from NWS
	 */
	static class PointStationsResponse{
		Station[] features;
		static class Station{
			Properties properties;
			static class Properties{
				String stationIdentifier;
				String name;
			}
			
		}
	}
}
