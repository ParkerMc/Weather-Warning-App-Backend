package utd.group12.weatherwarning.errors;

import org.springframework.http.HttpStatus;

/**
 * HTTP 500 Internal Server Error
 * 
 * The server encountered an unexpected condition which prevented it from fulfilling the request.
 * 
 * Used for basically any server error that shouldn't have happened
 */
public class InternalServerError extends HttpError {
	private static final long serialVersionUID = -8011988555510569382L;
	
	private final Exception original;

	public InternalServerError(String string, Exception original) {
		super(string, HttpStatus.INTERNAL_SERVER_ERROR);
		this.original = original;
	}

	/**
	 * Get original exception
	 * 
	 * @return	the original exception
	 */
	public Exception getOriginal() {
		return original;
	}
}
