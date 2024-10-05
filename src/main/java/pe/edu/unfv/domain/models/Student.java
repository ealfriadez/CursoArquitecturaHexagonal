package pe.edu.unfv.domain.models;

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
public class Student {

	private Long id;
	private String firstName;
	private String lastName;
	private Integer age;
	private String address;
	private String email;
}
