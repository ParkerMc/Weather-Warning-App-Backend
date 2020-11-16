package utd.group12.weatherwarning.data;

/**
 * Interface for the class that handles all token related data 
 */
public interface IDataTokens {
	/**
	 * Adds a token to the user
	 * 
	 * @param username			username to add the token to
	 * @param token				token to add
	 */
	public void add(String username, DataToken token);
	
	/**
	 * Checks if the token is already used
	 * 
	 * @param token		token to check
	 * @return			if the token is already used
	 */
	public boolean exists(String token);
	
	/**
	 * Checks if a token exists for the user
	 * 
	 * @param username	the username for the token
	 * @param token		the token for the username
	 * @return			if the token is valid for the username
	 */
	public boolean exists(String username, String token);
	
	/**
	 * Removes the token from a user
	 * 
	 * @param username	the username for the user
	 * @param token		the token to remove
	 */
	public void remove(String username, String token);
}
