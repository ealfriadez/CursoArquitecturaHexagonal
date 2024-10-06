package pe.edu.unfv.application.ports.output;

import java.util.List;
import java.util.Optional;

import pe.edu.unfv.domain.models.Student;

public interface StudentPersistencePort {

	Optional<Student> findById(Long id);
	List<Student> findAll();
	Student save(Student student);
	void deleteById(Long id);
}
