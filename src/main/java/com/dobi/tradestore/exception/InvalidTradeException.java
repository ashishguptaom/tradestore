package com.dobi.tradestore.exception;

public class InvalidTradeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidTradeException(String message) {
		super(message);
	}
}
