package utd.group12.weatherwarning;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Utils {
	private static Gson gson = new GsonBuilder().create();
	
	public static <T> T POSTRequest(String url, Object arg, Class<T> classOfT) throws MalformedURLException, IOException {
		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);
		String argStr = gson.toJson(arg);
		OutputStream os = con.getOutputStream();
	    byte[] input = argStr.getBytes("utf-8");
	    os.write(input, 0, input.length);
	    InputStream is = con.getInputStream();
		Reader reader = new InputStreamReader(is, "UTF-8");
		T responce = gson.fromJson(reader, classOfT);
		con.disconnect();
		os.close();
		is.close();
		reader.close();
		return responce;
	}
	
	public static <T> T GETRequest(String url, Map<String, String> args, Class<T> classOfT) throws MalformedURLException, IOException {
		// put args into url
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
		HttpURLConnection con = (HttpURLConnection) new URL(realUrl).openConnection();
	    InputStream is = con.getInputStream();
		Reader reader = new InputStreamReader(is, "UTF-8");
		T responce = gson.fromJson(reader, classOfT);
		con.disconnect();
		is.close();
		reader.close();
		return responce;
	}
}
