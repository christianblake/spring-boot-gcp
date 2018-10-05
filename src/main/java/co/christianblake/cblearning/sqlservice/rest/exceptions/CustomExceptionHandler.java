package co.christianblake.cblearning.sqlservice.rest.exceptions;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
 
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler
{
	private static String getStringError(ConstraintViolation<?> t) {
		return t.getPropertyPath() + ": " + t.getMessage();
	}
	
    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<CustomError> handleAllExceptions(RuntimeException ex, WebRequest request) {
    	CustomError _error = new CustomError();
    	
    	_error.setTimestamp(LocalDateTime.now());
    	_error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    	_error.setDetails(request.getDescription(false));
    	_error.setMessages(Arrays.asList(ex.getLocalizedMessage()));
    	
        return new ResponseEntity<CustomError>(_error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
 
    @ExceptionHandler(TransactionSystemException.class)
    protected ResponseEntity<CustomError> handleConflict(TransactionSystemException ex, WebRequest request) {
    	
    	Throwable cause = ex.getRootCause();

    	CustomError _error = new CustomError();
    	
    	if( !(cause instanceof ConstraintViolationException)) {
        	_error.setTimestamp(LocalDateTime.now());
        	_error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        	_error.setDetails(request.getDescription(false));
        	_error.setMessages(Arrays.asList(ex.getLocalizedMessage()));
    		return new ResponseEntity<CustomError>(_error, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    	
    	_error.setTimestamp(LocalDateTime.now());
    	_error.setStatus(HttpStatus.BAD_REQUEST);
    	_error.setDetails(request.getDescription(false));
    	_error.setMessages(((ConstraintViolationException) cause).getConstraintViolations().stream().map(t -> getStringError(t)).collect(Collectors.toList()));
        
        return new ResponseEntity<CustomError>(_error, HttpStatus.BAD_REQUEST);
        		
    }   
}
