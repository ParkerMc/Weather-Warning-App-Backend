package utd.group12.weatherwarning.errors;

import org.springframework.http.HttpStatus;

/**
 * HTTP 401 Unauthorized
 * 
 * The request requires user authentication. <br/>
 * If the request already included Authorization credentials, then the 401 response indicates that authorization has been refused for those credentials.
 * 
 * Used when the the authentication information is not given or invalid.
 *
 */
public class UnathorizedError extends HttpError  {
	private static final long serialVersionUID = -1234139866967647950L;

	public UnathorizedError(String string) {
		super(string, HttpStatus.UNAUTHORIZED);
	}
}
