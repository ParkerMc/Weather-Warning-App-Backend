package utd.group12.weatherwarning.response;

public class UserResponse {
	private final String username;
	private final String email;
	private final String phoneNumber;
	
	public UserResponse(String username, String email, String phoneNumber) {
		this.username = username;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public String getUsername() {
		return this.username;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
}
