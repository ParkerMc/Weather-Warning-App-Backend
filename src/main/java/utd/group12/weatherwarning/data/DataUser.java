package utd.group12.weatherwarning.data;

/**
 * The class used for the user objects
 */
public class DataUser {
	private final String username;
	private final String email;
	private final String googleID;
	private final String password;
	private final String salt;
	private final String name;
	private final String phoneNumber;
	
	/**
	 * Constructor for the user object <br/>
	 * Only {@code googleID} or {@code password} will be set
	 * 
	 * @param username		the user's username
	 * @param email			the user's email
	 * @param googleID		the user's googleID 
	 * @param password		the user's hashed password
	 * @param salt			the user's password salt
	 * @param name				the user's name
	 * @param phoneNumber	the user's phone number
	 */
	public DataUser(String username, String email, String googleID, String password, String salt, String name, String phoneNumber) {
		this.username = username;
		this.email = email;
		this.googleID = googleID;
		this.password = password;
		this.salt = salt;
		this.name = name;
		this.phoneNumber = phoneNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((googleID == null) ? 0 : googleID.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((salt == null) ? 0 : salt.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataUser other = (DataUser) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (googleID == null) {
			if (other.googleID != null)
				return false;
		} else if (!googleID.equals(other.googleID))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (salt == null) {
			if (other.salt != null)
				return false;
		} else if (!salt.equals(other.salt))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
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
	 * Gets the name
	 * 
	 * @return	the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the password
	 * 
	 * @return	the password hash
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Gets the password salt
	 * 
	 * @return	the password salt
	 */
	public String getSalt() {
		return salt;
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
