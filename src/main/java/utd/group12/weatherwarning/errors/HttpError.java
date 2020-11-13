package utd.group12.weatherwarning.errors;

import org.springframework.http.HttpStatus;

/**
 * Only used as a base to be extended
 * Will be caught by the HTTP controller
 */
@SuppressWarnings("serial")
public class HttpError extends Exception {
	private final HttpStatus status;
	
	/**
	 * Creates an HTTP error exception
	 * 
	 * @param message	The error message
	 * @param status	The HTTP status to be returned
	 */
	protected HttpError(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}

	/**
	 * Gets the HTTP status for the error
	 * 
	 * @return the HTTP status
	 */
	public HttpStatus getStatus() {
		return status;
	}
}
