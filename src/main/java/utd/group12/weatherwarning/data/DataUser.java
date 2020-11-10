package utd.group12.weatherwarning.data;

public class DataUser {
	private final String username;
	private final String email;
	private final String googleId;
	private final String password;
	private final String phoneNumber;
	
	public DataUser(String username, String email, String googleId, String password, String phoneNumber) {
		this.username = username;
		this.email = email;
		this.googleId = googleId;
		this.password = password;
		this.phoneNumber = phoneNumber;
	}

	public String getUsername() {
		return username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getGoogleId() {
		return googleId;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
}
