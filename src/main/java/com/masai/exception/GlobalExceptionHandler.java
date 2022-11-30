package com.masai.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;



@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MyErrorBean> validationExceptionHandler(MethodArgumentNotValidException manve) {
		
		MyErrorBean error = new MyErrorBean();
		
		error.setTimestamp(LocalDateTime.now());
		error.setMessage("Validation error");
		error.setDetails(manve.getBindingResult().getFieldError().getDefaultMessage());
		
		return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(AdminException.class)
	public ResponseEntity<MyErrorBean> adminExceptionHandler(AdminException ae, WebRequest wr) {
		
		MyErrorBean error = new MyErrorBean();
		
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(ae.getMessage());
		error.setDetails(wr.getDescription(false));
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CategoryException.class)
	public ResponseEntity<MyErrorBean> categoryExceptionHandler(CategoryException ce, WebRequest wr) {
		
		MyErrorBean error = new MyErrorBean();
		
		error.setDetails(wr.getDescription(false));
		error.setMessage(ce.getMessage());
		error.setTimestamp(LocalDateTime.now());
		
		return new ResponseEntity<MyErrorBean>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(LoginException.class)
	public ResponseEntity<MyErrorBean> loginExceptionHandler(LoginException ie, WebRequest wr) {
		
		MyErrorBean error = new MyErrorBean();
		
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(ie.getMessage());
		error.setDetails(wr.getDescription(false));
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CustomerException.class)
	public ResponseEntity<MyErrorBean> customerExceptionHandler(CustomerException ce, WebRequest wr) {
		
		MyErrorBean error = new MyErrorBean();
		
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(ce.getMessage());
		error.setDetails(wr.getDescription(false));
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CartException.class)
	public ResponseEntity<MyErrorBean> cartExceptionHandler(CartException ce, WebRequest wr) {
		
		MyErrorBean error = new MyErrorBean();
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(ce.getMessage());
		error.setDetails(wr.getDescription(false));
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(OrderException.class)
	public ResponseEntity<MyErrorBean> orderExceptionHandler(OrderException oe, WebRequest wr) {
		
		MyErrorBean error = new MyErrorBean();
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(oe.getMessage());
		error.setDetails(wr.getDescription(false));
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler(AddressException.class)
	public ResponseEntity<MyErrorBean> addressExceptionHandler(AddressException ae, WebRequest wr) {
		
		MyErrorBean error = new MyErrorBean();
		
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(ae.getMessage());
		error.setDetails(wr.getDescription(false));
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ProductException.class)
	public ResponseEntity<MyErrorBean> productExceptionHandler(ProductException pe, WebRequest wr) {
		
		MyErrorBean error = new MyErrorBean();
		
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(pe.getMessage());
		error.setDetails(wr.getDescription(false));
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(OutOfStockException.class)
	public ResponseEntity<MyErrorBean> stockExceptionHandler(OutOfStockException pe, WebRequest wr) {
		
		MyErrorBean error = new MyErrorBean();
		
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(pe.getMessage());
		error.setDetails(wr.getDescription(false));
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MyErrorBean> globalExceptionHandler(Exception ie, WebRequest wr) {

		MyErrorBean error = new MyErrorBean();

		error.setMessage(ie.getMessage());
		error.setDetails(wr.getDescription(false));
		error.setTimestamp(LocalDateTime.now());
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<MyErrorBean> noHandlerFoundHandler(NoHandlerFoundException nfe, WebRequest wr) {
		
		MyErrorBean error = new MyErrorBean();
		
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(nfe.getMessage());
		error.setDetails(wr.getDescription(false));
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}


}
