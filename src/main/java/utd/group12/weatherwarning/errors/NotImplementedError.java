package utd.group12.weatherwarning.errors;

import org.springframework.http.HttpStatus;

/**
 * HTTP 501 Not Implemented
 * 
 * The server does not support the functionality required to fulfill the request.
 * 
 * Used for when a request is not yet supported but will be
 */
public class NotImplementedError extends HttpError {
	private static final long serialVersionUID = 9016274951926300379L;

	public NotImplementedError(String string) {
		super(string, HttpStatus.NOT_IMPLEMENTED);
	}
}
