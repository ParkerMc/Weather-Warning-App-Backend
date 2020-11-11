package utd.group12.weatherwarning.response;

import utd.group12.weatherwarning.data.DataUser;

/**
 * The response for when a user is returned
 */
public class UserResponse {
	private final String username;
	private final String email;
	private final String phoneNumber;
	
	/**
	 * Creates the response for when a user is returned
	 * 
	 * @param username		username for the user returned
	 * @param email			email for the user returned
	 * @param phoneNumber	phone number for the user returned
	 */
	public UserResponse(String username, String email, String phoneNumber) {
		this.username = username;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Creates the response for when a user is returned
	 * 
	 * @param user		the user in the {@code DataUser} format
	 */
	public UserResponse(DataUser user) {
		this(user.getUsername(), user.getEmail(), user.getPhoneNumber());
	}

	public String getEmail() {
		return email;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getUsername() {
		return this.username;
	}
}
