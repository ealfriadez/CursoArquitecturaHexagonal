package pe.edu.unfv.infrastructure.adapters.input.rest.mapper;

import java.time.LocalDate;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import pe.edu.unfv.domain.models.Student;
import pe.edu.unfv.infrastructure.adapters.input.rest.models.request.StudentCreateRequest;
import pe.edu.unfv.infrastructure.adapters.input.rest.models.response.StudentResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentRestMapper {
	
	Student toStudent(StudentCreateRequest request);
	
	@Mapping(target = "timestamp", expression = "java(mapTimestamp())")
	StudentResponse toStudentResponse(Student student);
	
	List<StudentResponse> toStudentResponses(List<Student> students);
	
	default String mapTimestamp() {
		return LocalDate.now().toString();
	}
}
