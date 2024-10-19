
package pe.edu.unfv.infrastructure.adapters.output.restclient.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "courses-services", url = "http://localhost:8087")
public interface CourseFeignClient {

	@DeleteMapping("/courses/remove-student-from-collection/{studentId}")
	void removeStudentFromCollection(@PathVariable Long studentId);
}
