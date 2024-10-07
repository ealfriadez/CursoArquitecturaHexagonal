package pe.edu.unfv.infrastructure.adapters.input.rest.models.response;

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
public class StudentResponse {

	private Long id;
	private String firstName;
	private String lastName;
	private Integer age;
	private String address;
	private String timestamp;
}
