package utd.group12.weatherwarning.data;


/**
 * Stores location data
 */
public class DataLocation {
	/**
	 * Stores cords for a location
	 */
	public static class Point{
		private final double lat;
		private final double lng;
		
		/**
		 * @param lat	latitude
		 * @param lng	longitude
		 */
		public Point(double lat, double lng) {
			this.lat = lat;
			this.lng = lng;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			long temp;
			temp = Double.doubleToLongBits(lat);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(lng);
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
			Point other = (Point) obj;
			if (Double.doubleToLongBits(lat) != Double.doubleToLongBits(other.lat))
				return false;
			if (Double.doubleToLongBits(lng) != Double.doubleToLongBits(other.lng))
				return false;
			return true;
		}

		/**
		 * @return the latitude
		 */
		public double getLat() {
			return lat;
		}

		/**
		 * @return the longitude
		 */
		public double getLng() {
			return lng;
		}
	}
}
