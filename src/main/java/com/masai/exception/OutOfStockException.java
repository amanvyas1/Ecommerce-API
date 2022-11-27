package com.masai.exception;

public class OutOfStockException extends Exception{
	
	public OutOfStockException(){
		
	}

	public OutOfStockException(String message) {
		super(message);
	}
}
