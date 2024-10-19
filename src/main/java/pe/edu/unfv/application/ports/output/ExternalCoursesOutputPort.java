package pe.edu.unfv.application.ports.output;

import org.springframework.web.bind.annotation.PathVariable;

public interface ExternalCoursesOutputPort {
	
	void removeStudentFromCollection(@PathVariable Long studentId);
}
