package pe.edu.unfv.application.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.edu.unfv.application.ports.input.StudentInputPort;
import pe.edu.unfv.application.ports.output.ExternalCoursesOutputPort;
import pe.edu.unfv.application.ports.output.StudentPersistencePort;
import pe.edu.unfv.domain.exceptions.StudentEmailAlreadyExistsException;
import pe.edu.unfv.domain.exceptions.StudentNotFoundException;
import pe.edu.unfv.domain.models.Student;

@Service
@RequiredArgsConstructor
public class StudentService implements StudentInputPort{
	
	private final StudentPersistencePort persistencePort;
	private final ExternalCoursesOutputPort coursesOutputPort;

	@Override
	public Student findById(Long id) {		
		return persistencePort.findById(id)
				.orElseThrow(StudentNotFoundException::new);
	}

	@Override
	public List<Student> findAll() {		
		return persistencePort.findAll();
	}

	@Override
	public Student save(Student student) {		
		if (persistencePort.existsByEmail(student.getEmail())) {
			throw new StudentEmailAlreadyExistsException(student.getEmail());
		}
		return persistencePort.save(student);
	}

	@Override
	public Student update(Long id, Student student) {		
		if (persistencePort.existsByEmail(student.getEmail())) {
			throw new StudentEmailAlreadyExistsException(student.getEmail());
		}
		return persistencePort.findById(id)
				.map(oldStudent -> {
					oldStudent.setFirstName(student.getFirstName());
					oldStudent.setLastName(student.getLastName());
					oldStudent.setAge(student.getAge());
					oldStudent.setAddress(student.getAddress());
					oldStudent.setEmail(student.getEmail());
					return persistencePort.save(oldStudent);
				}).orElseThrow(StudentNotFoundException::new);
	}

	@Override
	public void deleteById(Long id) {
		if (persistencePort.findById(id).isEmpty()) {
			throw new StudentNotFoundException();
		}
		
		persistencePort.deleteById(id);
		coursesOutputPort.removeStudentFromCollection(id);
	}

	@Override
	public List<Student> findByIds(Iterable<Long> ids) {		
		return persistencePort.findByIds(ids);
	}

}
