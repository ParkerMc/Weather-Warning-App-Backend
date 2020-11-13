package utd.group12.weatherwarning.errors;

import org.springframework.http.HttpStatus;

/**
 * HTTP 400 Bad Request
 * 
 * The request could not be understood by the server due to malformed syntax. The client SHOULD NOT repeat the request without modifications.
 * 
 * Used when the request is bad, such as missing or invalid username or password.
 *
 */
public class BadRequestError extends HttpError  {
	private static final long serialVersionUID = 2728867520030811206L;

	public BadRequestError(String string) {
		super(string, HttpStatus.BAD_REQUEST);
	}
}
