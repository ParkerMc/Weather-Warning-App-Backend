package utd.group12.weatherwarning.response;

import java.time.Instant;
import java.util.Date;

import org.springframework.http.HttpStatus;

/**
 * Used to return an error where the status code is not 2XX or 3XX
 */
public class ErrorResponse {
	private final Date timestamp;
	private final int status;
	private final String error;
	private final String message;
	private final String path;
	
	/**
	 * Creates the error response
	 * 
	 * @param status	The HTTP status to return
	 * @param message	The more detail message (likely will be shown to the end user)
	 * @param path		The endpoint where the error occurred
	 */
	public ErrorResponse(HttpStatus status, String message, String path) {
		this.timestamp = Date.from(Instant.now());
		this.status = status.value();
		this.error = status.getReasonPhrase();
		this.message =  message;
		this.path = path;
	}
	
	public String getError() {
		return error;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getPath() {
		return path;
	}
	
	public int getStatus() {
		return status;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
}
