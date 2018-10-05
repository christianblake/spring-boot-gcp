package co.christianblake.cblearning.sqlservice.rest.exceptions;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class CustomError {

	private LocalDateTime timestamp;
	private HttpStatus status;
	private String details;
	private List<String> messages;
	
}