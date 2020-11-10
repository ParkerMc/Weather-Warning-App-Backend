package utd.group12.weatherwarning.data.json;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import utd.group12.weatherwarning.data.IDataInfo;
import utd.group12.weatherwarning.data.IDataServer;
import utd.group12.weatherwarning.data.IDataUsers;

public class DataServer implements IDataServer {
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private static File file = new File("data.json");
	 
	private MainJson json;
	
	@Override
	public void start() {
		if(file.exists()) {
			try {
				FileReader reader = new FileReader(file);
				this.json = gson.fromJson(reader, MainJson.class);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			this.json = new MainJson();
		}
		this.forceSave();
	}

	@Override
	public void stop() {}

	@Override
	public IDataInfo getInfo() {
		return this.json.info;
	}

	@Override
	public IDataUsers getUsers() {
		return this.json.users;
	}

	@Override
	public void forceSave() {
		try {
			FileWriter writer = new FileWriter(file);
			gson.toJson(this.json, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

}
