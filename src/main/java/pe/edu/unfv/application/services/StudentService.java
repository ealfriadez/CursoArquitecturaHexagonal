package pe.edu.unfv.application.services;

import java.util.List;

import lombok.RequiredArgsConstructor;
import pe.edu.unfv.application.ports.input.StudentInputPort;
import pe.edu.unfv.application.ports.output.StudentPersistencePort;
import pe.edu.unfv.domain.exceptions.StudentNotFoundException;
import pe.edu.unfv.domain.models.Student;

@RequiredArgsConstructor
public class StudentService implements StudentInputPort{
	
	private StudentPersistencePort persistencePort;

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
		return persistencePort.save(student);
	}

	@Override
	public Student update(Long id, Student student) {		
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
	}

}
