package io.pivotal.datatx.citi.cacheservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(value = CONFLICT)
public class ConflictStatusException extends RuntimeException {

	public ConflictStatusException() {
		super();
	}

	public ConflictStatusException(String message) {
		super(message);
	}

	public ConflictStatusException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConflictStatusException(Throwable cause) {
		super(cause);
	}

	protected ConflictStatusException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}