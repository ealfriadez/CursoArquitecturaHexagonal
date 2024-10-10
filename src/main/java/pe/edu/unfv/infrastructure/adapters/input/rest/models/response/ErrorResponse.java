package pe.edu.unfv.infrastructure.adapters.input.rest.models.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.unfv.infrastructure.adapters.input.rest.models.enums.ErrorType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

	private String code;
	private ErrorType type; //Functional, System
	private String message;
	private List<String> details;
	private String timestamp;
}
