package pe.edu.unfv.utils;

import java.util.List;

import pe.edu.unfv.domain.models.Student;
import pe.edu.unfv.infrastructure.adapters.output.persistence.models.StudentEntity;

public class TestUtils {

	public static Student buildStudent() {
		return Student.builder()
				.id(1L)
				.firstName("Pepito")
				.lastName("Dos Palotes")
				.age(15)
				.email("pepito@gmail.com")
				.address("Clle. 1")
				.build();
	}

	public static List<Student> studentList() {

		System.out.println(" -> Obteniendo listado estudiantes / Mock");

		return List.of(
				new Student(1L, "Pepito", "Dos Palotes", 15, "pepito@gmail.com", "Clle. 1"),
				new Student(2L, "Juan", "Sin miedo", 10, "juan@gmail.com", "Clle. 2"),
				new Student(3L, "Miguel", "Barraza", 65, "miguel@gmail.com", "Clle. 3"),
				new Student(4L, "Santiago", "Coqueto", 11, "santiago@gmail.com", "Clle. 4"),
				new Student(5L, "Carlitos", "Suero", 5, "carlitos@gmail.com", "Clle. 5"),
				new Student(6L, "Alfredito", "Chichicuarima", 8, "alfredito@gmail.com", "Clle. 6"));
	}
	
	public static StudentEntity buildStudentEntityMock() {
		return StudentEntity.builder()
				.id(1L)
				.firstName("Pepito")
				.lastName("Dos Palotes")
				.age(15)
				.email("pepito@gmail.com")
				.address("Clle. 1")
				.build();
	}
}
