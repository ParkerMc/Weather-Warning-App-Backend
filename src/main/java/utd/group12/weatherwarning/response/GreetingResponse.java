package utd.group12.weatherwarning.response;

/*
 * The example response returns two strings
 * This is just a data holding class that is used to format the greeting response
 */
public class GreetingResponse {

	private final String service_name;
	private final String content;

	/**
	 * Creates the greeting response
	 * 
	 * @param service_name	The service name to return
	 * @param content		The other content to be returned
	 */
	public GreetingResponse(String service_name, String content) {
		this.service_name = service_name;
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}
	
	public String getServiceName() {
		return this.service_name;
	}
}
