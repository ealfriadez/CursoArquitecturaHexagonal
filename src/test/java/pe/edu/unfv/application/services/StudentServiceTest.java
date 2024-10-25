package pe.edu.unfv.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import pe.edu.unfv.domain.exceptions.StudentEmailAlreadyExistsException;
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

		// Inicializacion
		when(studentPersistencePort.findById(anyLong())).thenReturn(Optional.of(TestUtils.buildStudent()));

		// Evaluacion del comportamiento
		Student respuesta = studentService.findById(1L);

		// Comprobaciones o aserciones
		assertNotNull(respuesta);
		assertEquals(1L, respuesta.getId());
		assertEquals("Pepito", respuesta.getFirstName());
		verify(studentPersistencePort, times(1)).findById(1L);
	}

	@Test
	void testFindById_Throw() {

		// Inicializacion
		Optional<Student> empty = Optional.empty();
		when(studentPersistencePort.findById(anyLong())).thenReturn(empty);

		// Comprobaciones o aserciones
		assertThrows(StudentNotFoundException.class, () -> {
			studentService.findById(4L);
		});
		verify(studentPersistencePort, times(1)).findById(4L);
	}

	@Test
	void testFindByIds() {

		// Inicializacion
		List<Long> ids = Collections.singletonList(1L);
		when(studentPersistencePort.findByIds(anyCollection()))
				.thenReturn(Collections.singletonList(TestUtils.buildStudent()));

		// Evaluacion del comportamiento
		List<Student> respuesta = studentService.findByIds(Collections.singletonList(1L));

		// Comprobaciones o aserciones
		assertFalse(respuesta.isEmpty());
		assertEquals(1, respuesta.size());
		assertEquals(1L, respuesta.get(0).getId());
		assertEquals("Pepito", respuesta.get(0).getFirstName());
		assertEquals("Dos Palotes", respuesta.get(0).getLastName());
		verify(studentPersistencePort, times(1)).findByIds(ids);
	}

	@Test
	void testFindAll() {
		// Inicializacion
		when(studentPersistencePort.findAll()).thenReturn(TestUtils.studentList());

		// Evaluacion del comportamiento
		List<Student> respuesta = studentService.findAll();

		// Comprobaciones o aserciones
		assertFalse(respuesta.isEmpty());
		assertEquals(6, respuesta.size());
		assertEquals(6L, respuesta.get(5).getId());
		assertEquals("Juan", respuesta.get(1).getFirstName());
		assertEquals("Coqueto", respuesta.get(3).getLastName());
		verify(studentPersistencePort, times(1)).findAll();
	}

	@Test
	void testSave_Success() {

		// Inicializacion
		Student studentToSave = Student.builder().id(1L).firstName("Pepito").lastName("Dos Palotes").age(15)
				.email("pepito@gmail.com").address("Clle. 1").build();

		when(studentPersistencePort.existsByEmail(anyString())).thenReturn(Boolean.FALSE);
		when(studentPersistencePort.save(any(Student.class))).thenReturn(TestUtils.buildStudent());

		// Evaluacion del comportamiento
		Student respuesta = studentService.save(studentToSave);

		// Comprobaciones o aserciones
		assertNotNull(respuesta);
		assertEquals(1L, respuesta.getId());
		assertEquals("Pepito", respuesta.getFirstName());
		assertEquals("Dos Palotes", respuesta.getLastName());
		assertEquals(15, respuesta.getAge());
		assertEquals("pepito@gmail.com", respuesta.getEmail());
		assertEquals("Clle. 1", respuesta.getAddress());
		verify(studentPersistencePort, times(1)).existsByEmail("pepito@gmail.com");
		verify(studentPersistencePort, times(1)).save(studentToSave);
	}

	@Test
	void testSave_Faild() {

		// Inicializacion
		Student studentToSave = Student.builder().id(1L).firstName("Pepito").lastName("Dos Palotes").age(15)
				.email("pepito@gmail.com").address("Clle. 1").build();

		when(studentPersistencePort.existsByEmail(anyString())).thenReturn(Boolean.TRUE);

		// Comprobaciones o aserciones
		assertThrows(StudentEmailAlreadyExistsException.class, () -> {
			studentService.save(studentToSave);
		});
		verify(studentPersistencePort, times(1)).existsByEmail("pepito@gmail.com");
		verify(studentPersistencePort, times(0)).save(studentToSave);
	}
	
	@Test
	void testUpdate_Success() {

		// Inicializacion
		Student studentToUpdate = Student.builder().id(1L).firstName("Pepito1").lastName("Dos Palotes1").age(15)
				.email("pepito1@gmail.com").address("Clle. 11").build();

		when(studentPersistencePort.existsByEmail(anyString())).thenReturn(Boolean.FALSE);
		when(studentPersistencePort.findById(anyLong())).thenReturn(Optional.of(TestUtils.buildStudent()));

		// Evaluacion del comportamiento
		Student respuesta = studentService.update(1L, studentToUpdate);
		
		// Comprobaciones o aserciones
				assertNotNull(respuesta);
				assertEquals(1L, respuesta.getId());
				assertEquals("Pepito", respuesta.getFirstName());
				assertEquals("Dos Palotes", respuesta.getLastName());
				assertEquals(15, respuesta.getAge());
				assertEquals("pepito@gmail.com", respuesta.getEmail());
				assertEquals("Clle. 1", respuesta.getAddress());
				verify(studentPersistencePort, times(1)).existsByEmail("pepito@gmail.com");
				//verify(studentPersistencePort, times(1)).save(studentToSave);
	}

	@Test
	void testUpdate_Fail() {

		// Inicializacion
		Student studentToUpdate = Student.builder().id(1L).firstName("Pepito").lastName("Dos Palotes").age(15)
				.email("pepito@gmail.com").address("Clle. 1").build();

		when(studentPersistencePort.existsByEmail(anyString())).thenReturn(Boolean.TRUE);

		// Comprobaciones o aserciones
		assertThrows(StudentEmailAlreadyExistsException.class, () -> {
			studentService.save(studentToUpdate);
		});
		verify(studentPersistencePort, times(1)).existsByEmail("pepito@gmail.com");
		verify(studentPersistencePort, times(0)).save(studentToUpdate);
	}

	@Test
	void testDeleteById() {
		fail("Not yet implemented");
	}
}
