package utd.group12.weatherwarning.data.json;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import utd.group12.weatherwarning.data.IDataInfo;
import utd.group12.weatherwarning.data.IDataServer;
import utd.group12.weatherwarning.data.IDataTokens;
import utd.group12.weatherwarning.data.IDataUsers;

/**
 * The connector between the program and the data when using JSON
 */
public class DataServer implements IDataServer {
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private static File file = new File("data.json");
	 
	private MainJson json;
	
	/**
	 * Starts the data service if needed
	 */
	@Override
	public void start() {
		if(file.exists()) {		// If the data file exits load it
			try {
				FileReader reader = new FileReader(file);			// Open the reader
				this.json = gson.fromJson(reader, MainJson.class);	// Read data
				reader.close();										// Close the reader
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {	// Else just get the default configuration
			this.json = new MainJson();
		}
		this.forceSave();	// force save all data 
	}

	/**
	 * Stops and cleans up the data service if needed
	 */
	@Override
	public void stop() {}

	/**
	 * Forces the data service to save (if it hasn't already)
	 */
	@Override
	public void forceSave() {
		try {
			FileWriter writer = new FileWriter(file);	// Open the writer
			gson.toJson(this.json, writer);				// Write to the file
			writer.close();								// Close the writer
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Gets the info data handler
	 * 
	 * @return	the info data handler
	 */
	@Override
	public IDataInfo getInfo() {
		return this.json.info;
	}

	/**
	 * Gets the user data handler
	 * 
	 * @return	the user data handler
	 */
	@Override
	public IDataUsers getUsers() {
		return this.json.users;
	}

	@Override
	public IDataTokens getTokens() {
		return this.json.tokens;
	}

}
