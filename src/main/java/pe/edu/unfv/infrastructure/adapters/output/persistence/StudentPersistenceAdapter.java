package pe.edu.unfv.infrastructure.adapters.output.persistence;

import java.util.List;
import java.util.Optional;

import pe.edu.unfv.application.ports.output.StudentPersistencePort;
import pe.edu.unfv.domain.models.Student;

public class StudentPersistenceAdapter implements StudentPersistencePort{

	@Override
	public Optional<Student> findById(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Student> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Student save(Student student) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

}
