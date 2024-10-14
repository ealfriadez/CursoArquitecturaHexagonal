package pe.edu.unfv.application.ports.input;

import java.util.List;

import pe.edu.unfv.domain.models.Student;

public interface StudentInputPort {

	Student findById(Long id);
	List<Student> findByIds(Iterable<Long> ids);
	List<Student> findAll();
	Student save(Student student);
	Student update(Long id, Student student);
	void deleteById(Long id);
}
