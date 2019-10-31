package io.pivotal.datatx.citi.cacheservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(value = NOT_FOUND)
public class NotFoundStatusException extends RuntimeException {

	public NotFoundStatusException() {
		super();
	}

	public NotFoundStatusException(String message) {
		super(message);
	}

	public NotFoundStatusException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundStatusException(Throwable cause) {
		super(cause);
	}

	protected NotFoundStatusException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}