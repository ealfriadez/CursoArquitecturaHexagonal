package pe.edu.unfv.infrastructure.adapters.input.rest;

import static pe.edu.unfv.infrastructure.utils.ErrorCatalog.INTERNAL_SERVER_ERROR;
import static pe.edu.unfv.infrastructure.utils.ErrorCatalog.STUDENT_BAD_PARAMETERS;
import static pe.edu.unfv.infrastructure.utils.ErrorCatalog.STUDENT_NOT_FOUND;

import java.time.LocalDate;
import java.util.Collections;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import pe.edu.unfv.domain.exceptions.StudentNotFoundException;
import pe.edu.unfv.infrastructure.adapters.input.rest.models.response.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

	private final String ERROR_LOG_MESSAGE = "Error -> code: {}, message: {}";
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(StudentNotFoundException.class)
	public ErrorResponse handleStudentNotFoundException() {
	
		log.error(ERROR_LOG_MESSAGE, STUDENT_NOT_FOUND.getCode(), STUDENT_NOT_FOUND.getMessage());
		
		return ErrorResponse.builder()
				.code(STUDENT_NOT_FOUND.getCode())
				.message(STUDENT_NOT_FOUND.getMessage())
				.timestamp(LocalDate.now().toString())
				.build();
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
		BindingResult bindingResult = e.getBindingResult();
		
		log.error(ERROR_LOG_MESSAGE, STUDENT_BAD_PARAMETERS.getCode(), STUDENT_BAD_PARAMETERS.getMessage());
		
		return ErrorResponse.builder()
				.code(STUDENT_BAD_PARAMETERS.getCode())
				.message(STUDENT_BAD_PARAMETERS.getMessage())
				.details(bindingResult.getFieldErrors().stream()
						//.map(fieldError -> fieldError.getDefaultMessage()) //OTRA OPCION
						.map(DefaultMessageSourceResolvable::getDefaultMessage)
						.toList())
				.timestamp(LocalDate.now().toString())
				.build();
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ErrorResponse handleException(Exception e) {
		
		log.error(ERROR_LOG_MESSAGE, INTERNAL_SERVER_ERROR.getCode(), INTERNAL_SERVER_ERROR.getMessage());
		
		return ErrorResponse.builder()
				.code(INTERNAL_SERVER_ERROR.getCode())
				.message(INTERNAL_SERVER_ERROR.getMessage())
				.details(Collections.singletonList(e.getMessage()))
				.timestamp(LocalDate.now().toString())
				.build();
	}
}
