package pe.edu.unfv.infrastructure.adapters.output.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pe.edu.unfv.application.ports.output.StudentPersistencePort;
import pe.edu.unfv.domain.models.Student;
import pe.edu.unfv.infrastructure.adapters.output.persistence.mapper.StudentPersistenceMapper;
import pe.edu.unfv.infrastructure.adapters.output.persistence.models.StudentEntity;
import pe.edu.unfv.infrastructure.adapters.output.persistence.repository.StudentJpaRepository;

@Component 
@RequiredArgsConstructor
public class StudentPersistenceAdapter implements StudentPersistencePort{

	private final StudentJpaRepository jpaRepository;
	private final StudentPersistenceMapper mapper;
	
	@Override
	public Optional<Student> findById(Long id) {
		return jpaRepository.findById(id)
				.map(mapper::toStudent);
	}

	@Override
	public List<Student> findAll() {
		return mapper.toStudents(
				(List<StudentEntity>) jpaRepository.findAll()
		);
	}

	@Override
	public Student save(Student student) {
		return mapper.toStudent(
				jpaRepository.save(mapper.toStudentEntity(student))
		);
	}

	@Override
	public void deleteById(Long id) {
		jpaRepository.deleteById(id);		
	}

}
