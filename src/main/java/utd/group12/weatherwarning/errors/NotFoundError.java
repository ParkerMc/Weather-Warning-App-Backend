package utd.group12.weatherwarning.errors;

import org.springframework.http.HttpStatus;

/**
 * HTTP 404 Not Found
 * 
 * The server has not found anything matching the Request-URI(endpoint + data in url). <br/>
 * No indication is given of whether the condition is temporary or permanent.
 * 
 * Used when the requested data can't be found.
 */
public class NotFoundError extends HttpError {
	private static final long serialVersionUID = 4367386373015714362L;

	public NotFoundError(String string) {
		super(string, HttpStatus.NOT_FOUND);
	}
}
