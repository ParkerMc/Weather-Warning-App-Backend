package utd.group12.weatherwarning.data;

import java.util.Date;

public interface IDataUsers {
	public void addToken(String username, String token, Date tokenExp);
	public boolean isTokenUsed(String token);

	public DataUser getFromGoogleID(String ID);
	public DataUser createUser(String username, String email, String googleID, String password, String phoneNumber);
	public boolean isUsernameUsed(String username);
	public DataUser getUser(String username);
	public boolean isTokenValid(String username, String token);
}
