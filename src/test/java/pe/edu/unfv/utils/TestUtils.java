package pe.edu.unfv.utils;

import pe.edu.unfv.domain.models.Student;

public class TestUtils {

	public static Student buildStudent() {
		return Student.builder()
				.id(1L)
				.firstName("Pepito")
				.lastName("Dos Palotes")
				.email("pepito@gmail.com")
				.address("Clle. 1")
				.build();
	}
}
