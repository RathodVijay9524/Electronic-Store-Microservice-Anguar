package com.vijay.commonservice.exception;

import com.vijay.commonservice.response.ApiResponseMessage;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        log.info("Exception Handler invoked !!");
        ApiResponseMessage response = ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String, Object> response = new HashMap<>();
        allErrors.stream().forEach(objectError -> {
            String message = objectError.getDefaultMessage();
            String field = ((FieldError) objectError).getField();
            response.put(field, message);
        });

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}



/*    @ExceptionHandler(ResourceNotFoundExceptions.class)
    public ResponseEntity<ErrorDetails> resourceNotFoundHandler(ResourceNotFoundExceptions exc, WebRequest webRequest) {
        logger.info("Exception Hndler invoked");
        ErrorDetails errorDetails=new ErrorDetails(
                LocalDateTime.now(),
                exc.getMessage(),
                webRequest.getDescription(false),"USER_NOT_FOUND"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);


    }*/

	/*
	@ExceptionHandler(EmployeeNotFoundException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage employeeNotFoundHandler(EmployeeNotFoundException exception) {
		ErrorMessage message=new ErrorMessage(HttpStatus.NOT_FOUND,exception.getMessage());
		return message;

	}
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage genricExceptionHandler(Exception exception) {
		ErrorMessage message=new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,exception.getMessage());
		return message;

	}

	@Data
public class CustomException extends RuntimeException{

    private String errorCode;
    private int status;

    public CustomException(String message, String errorCode, int status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
}
	 @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException exception) {
        return new ResponseEntity<>(new ErrorResponse().builder()
                .errorMessage(exception.getMessage())
                .errorCode(exception.getErrorCode())
                .build(),
                HttpStatus.valueOf(exception.getStatus()));
    }

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorRespose> resourceNotFoundHandler(ResourceNotFoundException exception) {
		return new ResponseEntity<>(new ErrorRespose().builder()
				.errorMessage(exception.getMessage())
				.errorCode(exception.getErrorCode())
				.build(),HttpStatus.NOT_FOUND);

}
*/