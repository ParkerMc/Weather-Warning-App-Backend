package utd.group12.weatherwarning.response;

import java.time.Instant;
import java.util.Date;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
	private final Date timestamp;
	private final int status;
	private final String error;
	private final String message;
	private final String path;
	
	public ErrorResponse(HttpStatus status, String message, String path) {
		this.timestamp = Date.from(Instant.now());
		this.status = status.value();
		this.error = status.getReasonPhrase();
		this.message =  message;
		this.path = path;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}

	public int getStatus() {
		return status;
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
	
	
}
