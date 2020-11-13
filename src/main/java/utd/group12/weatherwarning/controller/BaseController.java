package utd.group12.weatherwarning.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import utd.group12.weatherwarning.errors.HttpError;
import utd.group12.weatherwarning.errors.InternalServerError;
import utd.group12.weatherwarning.response.ErrorResponse;

/**
 * Base for all the controllers to handle our special errors
 */
public class BaseController {
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	/**
	 * Handle our special errors
	 * 
	 * @param error		the error
	 * @param request	request the error was thrown on
	 * @return			the error response
	 */
	@ExceptionHandler({HttpError.class})
	public ResponseEntity<ErrorResponse> handleHttpError(HttpError error, HttpServletRequest request) {
		if(error instanceof InternalServerError) {
			logger.info("Internal Error with request to: " + request.getRequestURI());
			((InternalServerError)error).getOriginal().printStackTrace();
		}
		return new ResponseEntity<ErrorResponse>(new ErrorResponse(error.getStatus(), error.getMessage(), request.getRequestURI()), error.getStatus());
	}
}
