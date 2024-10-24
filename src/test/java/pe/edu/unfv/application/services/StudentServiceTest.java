package pe.edu.unfv.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pe.edu.unfv.application.ports.output.ExternalCoursesOutputPort;
import pe.edu.unfv.application.ports.output.StudentPersistencePort;
import pe.edu.unfv.domain.exceptions.StudentNotFoundException;
import pe.edu.unfv.domain.models.Student;
import pe.edu.unfv.utils.TestUtils;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

	@Mock
	private StudentPersistencePort studentPersistencePort;
	
	@Mock
	private ExternalCoursesOutputPort externalCoursesOutputPort; 
	
	@InjectMocks
	private StudentService studentService;
	
	@Test
	void testFindById_Success() {
		
		//Inicializacion
		when(studentPersistencePort.findById(anyLong())).thenReturn(Optional.of(TestUtils.buildStudent()));
		
		//Evaluacion del comportamiento		
		Student respuesta = studentService.findById(1L);
		
		//Comprobaciones o aserciones
		assertNotNull(respuesta);
		assertEquals(1L, respuesta.getId());
		assertEquals("Pepito", respuesta.getFirstName());		
		verify(studentPersistencePort, times(1)).findById(1L);
	}
	
	@Test
	void testFindById_Throw() {
		
		//Inicializacion
		when(studentPersistencePort.findById(anyLong())).thenReturn(Optional.empty());
		
		//Comprobaciones o aserciones		
		assertThrows(StudentNotFoundException.class, () -> {
			studentService.findById(4L);
		});
		verify(studentPersistencePort, times(1)).findById(4L);
	}

	@Test
	void testFindAll() {
		when(studentPersistencePort.findByIds(anyIterable())).thenReturn(Collections.singletonList(TestUtils.buildStudent()));

		List<Student> respuesta = studentPersistencePort.findByIds(Collections.singletonList(1L));
		
		assertEquals(1, respuesta.size());
		verify(studentPersistencePort, times(1)).findByIds(Collections.singletonList(1L));
	}

	
	// Then => entonces
	//		assertThrows(IllegalArgumentException.class, () -> {
	//			example.factorial(numero);
	//		});
	@Test
	void testSave() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteById() {
		fail("Not yet implemented");
	}

	@Test
	void testFindByIds() {
		fail("Not yet implemented");
	}

}
