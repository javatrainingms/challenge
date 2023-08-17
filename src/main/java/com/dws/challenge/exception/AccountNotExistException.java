package com.dws.challenge.exception;

import org.springframework.http.HttpStatus;

public class AccountNotExistException extends RuntimeException {

	public AccountNotExistException(String message) {
		super(message);
	}
}