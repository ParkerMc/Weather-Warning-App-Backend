package utd.group12.weatherwarning;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A place to put all those random helpful functions
 */
public class Utils {
	private static Gson gson = new GsonBuilder().create();
	
	/**
	 * Sends a GET request and phrases the response into JSON 
	 * @param url			The URL to request 
	 * @param args			A map of the get data to send
	 * @param classOfT		The class of the returned data to be used to parse JSON
	 * @return				The parsed response
	 * @throws IOException	From the HTTP connection
	 */
	public static <T> T GETRequest(String url, Map<String, String> args, Class<T> classOfT) throws IOException {
		// Put args into url
		String realUrl = new String(url);
		boolean first = true;
		for(Entry<String, String> arg : args.entrySet()) {
			realUrl += String.format("%s%s=%s",
					(first)? "?" : "&",
					arg.getKey(),
					arg.getValue()
					);
					
			if(first) {
				first = false;
			}
		}
		// Actually make request
		HttpURLConnection con = (HttpURLConnection) new URL(realUrl).openConnection();	// Setup the connection
		
		// Read the data from the server
	    InputStream is = con.getInputStream();
		Reader reader = new InputStreamReader(is, "UTF-8");
		T response = gson.fromJson(reader, classOfT);
		
		// Cleanup
		con.disconnect();
		is.close();
		reader.close();
		return response; 	// and return
	}
	
	/**
	 * Sends a POST request and phrases the response into JSON
	 *  
	 * @param url			The URL to request 
	 * @param arg			The post data to send
	 * @param classOfT		The class of the returned data to be used to parse JSON
	 * @return				The parsed response
	 * @throws IOException	From the HTTP connection
	 */
	public static <T> T POSTRequest(String url, Object arg, Class<T> classOfT) throws IOException {
		// Setup the connection
		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);
		
		// Write the data to the request
		String argStr = gson.toJson(arg);
		OutputStream os = con.getOutputStream();
	    byte[] input = argStr.getBytes("utf-8");
	    os.write(input, 0, input.length);
	    
	    // Read the data from the server
	    InputStream is = con.getInputStream();
		Reader reader = new InputStreamReader(is, "UTF-8");
		T response = gson.fromJson(reader, classOfT);
		
		// Cleanup
		con.disconnect();
		os.close();
		is.close();
		reader.close();
		return response;	// and return
	}
}
