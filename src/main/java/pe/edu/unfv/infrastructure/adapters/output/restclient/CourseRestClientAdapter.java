package pe.edu.unfv.infrastructure.adapters.output.restclient;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pe.edu.unfv.application.ports.output.ExternalCoursesOutputPort;
import pe.edu.unfv.infrastructure.adapters.output.restclient.client.CourseFeignClient;

@Component
@RequiredArgsConstructor
public class CourseRestClientAdapter implements ExternalCoursesOutputPort{	
	
	private final CourseFeignClient feignClient;
	
	@Override
	public void removeStudentFromCollection(Long studentId) {	
		feignClient.removeStudentFromCollection(studentId);
	}
}
