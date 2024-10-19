package pe.edu.unfv.application.ports.output;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "courses-service", url = "http://localhost:8090")
public interface ExternalCoursesOutputPort {

	@DeleteMapping("/courses/remove-student-from-collection/{studentId}")
	void removeStudentFromCollection(@PathVariable Long studentId);
}
