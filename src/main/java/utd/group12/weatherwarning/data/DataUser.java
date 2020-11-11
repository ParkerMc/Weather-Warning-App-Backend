package utd.group12.weatherwarning.data;

/**
 * The class used for the user objects
 */
public class DataUser {
	private final String username;
	private final String email;
	private final String googleID;
	private final String password;
	private final String phoneNumber;
	
	/**
	 * Constructor for the user object <br/>
	 * Only {@code googleID} or {@code password} will be set
	 * 
	 * @param username		the user's username
	 * @param email			the user's email
	 * @param googleID		the user's googleID 
	 * @param password		the user's encrypted password
	 * @param phoneNumber	the user's phone number
	 */
	public DataUser(String username, String email, String googleID, String password, String phoneNumber) {
		this.username = username;
		this.email = email;
		this.googleID = googleID;
		this.password = password;
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Gets the email
	 * 
	 * @return	the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Gets the googleID
	 * 
	 * @return	the googleID
	 */
	public String getGoogleID() {
		return googleID;
	}
	
	/**
	 * Gets the password
	 * 
	 * @return	the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Gets the phone number
	 * 
	 * @return	the phone number
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	/**
	 * Gets the username
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
}
