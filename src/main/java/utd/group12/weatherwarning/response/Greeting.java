/*
 * This is just a data holding class that is used to format the greeting responce
 */
package utd.group12.weatherwarning.response;

public class Greeting {

	private final String service_name;
	private final String content;

	public Greeting(String service_name, String content) {
		this.service_name = service_name;
		this.content = content;
	}

	public String getServiceName() {
		return this.service_name;
	}

	public String getContent() {
		return this.content;
	}
}
