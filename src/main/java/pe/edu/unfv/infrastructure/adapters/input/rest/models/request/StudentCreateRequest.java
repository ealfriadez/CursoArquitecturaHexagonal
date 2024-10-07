package pe.edu.unfv.infrastructure.adapters.input.rest.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentCreateRequest {

	@NotBlank(message = "Field firstName cannnot be null or blank")
	private String firstName;
	
	@NotBlank(message = "Field firstName cannnot be null or blank")
	private String lastName;
	
	@NotBlank(message = "Field age cannout be null")
	@Min(value = 1, message = "Field age must be greater than zero")
	private Integer age;
	
	@Email(message = "Field firstName cannnot be null or blank")
	private String email;
	
	@NotBlank(message = "Field firstName cannnot be null or blank")
	private String address;
}
