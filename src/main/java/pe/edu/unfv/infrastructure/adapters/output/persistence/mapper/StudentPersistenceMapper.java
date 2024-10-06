package pe.edu.unfv.infrastructure.adapters.output.persistence.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import pe.edu.unfv.domain.models.Student;
import pe.edu.unfv.infrastructure.adapters.output.persistence.models.StudentEntity;

@Mapper(componentModel = "spring")
public interface StudentPersistenceMapper {

	StudentEntity toStudentEntity(Student student);
	
	Student toStudent(StudentEntity entity);
	
	List<Student> toStudents(List<StudentEntity> entities);	
}
