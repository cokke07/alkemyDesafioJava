package cl.cokke.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import cl.cokke.exception.BadRecuestException;
import cl.cokke.exception.NotFoundException;
import cl.cokke.exception.RestServiceException;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionConfig {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<?> notFoundException(Exception e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	
	@ExceptionHandler(BadRecuestException.class)
	public ResponseEntity<?> badRecuestException(Exception e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
	
	@ExceptionHandler(RestServiceException.class)
	public ResponseEntity<?> restServiceException(Exception e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
	
}
