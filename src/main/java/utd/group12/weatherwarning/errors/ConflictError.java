package utd.group12.weatherwarning.errors;

import org.springframework.http.HttpStatus;

/**
 * HTTP 409 Conflict
 * 
 * The request could not be completed due to a conflict with the current state of the resource. <br/>
 * This code is only allowed in situations where it is expected that the user might be able to resolve the conflict and resubmit the request.
 *   
 * Used when the data can not be saved because there is already something saved with that location that can't be overridden.
 */
public class ConflictError extends HttpError  {
	private static final long serialVersionUID = -479489513639935749L;
	
	public ConflictError(String string) {
		super(string, HttpStatus.CONFLICT);
	}
}
