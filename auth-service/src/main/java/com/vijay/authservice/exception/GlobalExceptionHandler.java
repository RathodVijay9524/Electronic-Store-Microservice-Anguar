package com.vijay.authservice.exception;


import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundExc(ResourceNotFoundException exc,
			WebRequest webRequest) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exc.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadApiRequestException.class)
	public ResponseEntity<ApiResponseMessage> handleBadApiRequest(BadApiRequestException ex) {
		log.info("Bad api request");
		ApiResponseMessage response = ApiResponseMessage.builder().message(ex.getMessage())
				.status(HttpStatus.BAD_REQUEST).success(false).build();
		return new ResponseEntity<ApiResponseMessage>(response, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(AuthUserApiException.class)
	public ResponseEntity<ErrorDetails> handleRBlogAPIException(AuthUserApiException exc, WebRequest webRequest) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exc.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exc, WebRequest webRequest) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exc.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex) {
		List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
		Map<String, Object> response = new HashMap<>();
		allErrors.stream().forEach(objectError -> {
			String message = objectError.getDefaultMessage();
			String field = ((FieldError) objectError).getField();
			response.put(field, message);
		});

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

//	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//			HttpHeaders headers, HttpStatus status, WebRequest request) {
//		Map<String, String> errors = new HashMap<>();
//		ex.getBindingResult().getAllErrors().forEach((error) -> {
//			String fieldName = ((FieldError) error).getField();
//			String message = error.getDefaultMessage();
//			errors.put(fieldName, message);
//		});
//
//		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//	}

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
//                                                                        WebRequest webRequest){
//        Map<String, String> errors = new HashMap<>();
//        exception.getBindingResult().getAllErrors().forEach((error) ->{
//            String fieldName = ((FieldError)error).getField();
//            String message = error.getDefaultMessage();
//            errors.put(fieldName, message);
//        });
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }

}
