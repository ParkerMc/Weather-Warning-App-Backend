package utd.group12.weatherwarning.data.json;

public class MainJson {
	DataInfo info;
	
	static MainJson create() {
		MainJson main = new MainJson();
		main.info = new DataInfo();
		return main;
	}
}
