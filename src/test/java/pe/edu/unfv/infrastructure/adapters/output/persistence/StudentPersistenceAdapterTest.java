package pe.edu.unfv.infrastructure.adapters.output.persistence;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
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

import pe.edu.unfv.domain.models.Student;
import pe.edu.unfv.infrastructure.adapters.output.persistence.mapper.StudentPersistenceMapper;
import pe.edu.unfv.infrastructure.adapters.output.persistence.models.StudentEntity;
import pe.edu.unfv.infrastructure.adapters.output.persistence.repository.StudentJpaRepository;
import pe.edu.unfv.utils.TestUtils;

@ExtendWith(MockitoExtension.class)
class StudentPersistenceAdapterTest {

	@Mock
	private StudentJpaRepository jpaRepository;

	@Mock
	private StudentPersistenceMapper mapper;

	@InjectMocks
	private StudentPersistenceAdapter studentPersistenceAdapter;

	@Test
	void testFindById() {

		// Inicializacion
		Student studentExpect = TestUtils.buildStudent();
		StudentEntity studentEntityExpect = TestUtils.buildStudentEntityMock();
		when(jpaRepository.findById(anyLong())).thenReturn(Optional.of(studentEntityExpect));
		when(mapper.toStudent(any(StudentEntity.class))).thenReturn(studentExpect);

		// Evaluacion del comportamiento
		Optional<Student> respuesta = studentPersistenceAdapter.findById(1L);

		// Comprobaciones o aserciones
		assertAll(() -> assertNotNull(respuesta), () -> assertEquals(studentExpect, respuesta.get()),
				() -> assertEquals(1L, respuesta.get().getId()),
				() -> assertEquals("Pepito", respuesta.get().getFirstName()),
				() -> assertEquals("Dos Palotes", respuesta.get().getLastName()),
				() -> assertEquals(15, respuesta.get().getAge()),
				() -> assertEquals("pepito@gmail.com", respuesta.get().getEmail()),
				() -> assertEquals("Clle. 1", respuesta.get().getAddress()));

		verify(jpaRepository, times(1)).findById(1L);
		verify(mapper, times(1)).toStudent(any(StudentEntity.class));
	}

	@Test
	void testFindById_NotFound() {

		// Inicializacion
		when(jpaRepository.findById(anyLong())).thenReturn(Optional.empty());
		
		// Evaluacion del comportamiento
		Optional<Student> respuesta = studentPersistenceAdapter.findById(1L);

		// Comprobaciones o aserciones
		assertAll(
			() -> assertFalse(respuesta.isPresent()),
			() -> assertTrue(respuesta.isEmpty())
		);	
		
		verify(jpaRepository, times(1)).findById(1L);	
	}

	@Test
	void testFindByIds_Success() {

		// Inicializacion
		List<Long> ids = Collections.singletonList(1L);
		List<StudentEntity> studentEntities = Collections.singletonList(TestUtils.buildStudentEntityMock());
		List<Student> student = Collections.singletonList(TestUtils.buildStudent());
		when(jpaRepository.findAllById(anyCollection())).thenReturn(studentEntities);
		when(mapper.toStudents(anyList())).thenReturn(student);

		// Evaluacion del comportamiento
		List<Student> respuesta = studentPersistenceAdapter.findByIds(ids);

		// Comprobaciones o aserciones
		assertAll(() -> assertFalse(respuesta.isEmpty()), () -> assertEquals(1, respuesta.size()),
				() -> assertEquals(1L, respuesta.get(0).getId()),
				() -> assertEquals("Pepito", respuesta.get(0).getFirstName()),
				() -> assertEquals("Dos Palotes", respuesta.get(0).getLastName()));

		verify(jpaRepository, times(1)).findAllById(ids);
		verify(mapper, times(1)).toStudents(studentEntities);
	}

	@Test
	void testFindAll_Success() {

		// Inicializacion
		List<StudentEntity> studentEntities = Collections.singletonList(TestUtils.buildStudentEntityMock());
		List<Student> students = Collections.singletonList(TestUtils.buildStudent());

		when(jpaRepository.findAll()).thenReturn(studentEntities);
		when(mapper.toStudents(anyList())).thenReturn(students);

		// Evaluacion del comportamiento
		List<Student> respuesta = studentPersistenceAdapter.findAll();

		// Comprobaciones o aserciones
		assertAll(() -> assertFalse(respuesta.isEmpty()), () -> assertEquals(1, respuesta.size()),
				() -> assertEquals(1L, respuesta.get(0).getId()),
				() -> assertEquals("Pepito", respuesta.get(0).getFirstName()),
				() -> assertEquals("Dos Palotes", respuesta.get(0).getLastName()));

		verify(jpaRepository, times(1)).findAll();
		verify(mapper, times(1)).toStudents(studentEntities);
	}

	@Test
	void testSave_Success() {

		// Inicializacion
		StudentEntity studentEntity = TestUtils.buildStudentEntityMock();
		Student student = TestUtils.buildStudent();

		when(mapper.toStudentEntity(any(Student.class))).thenReturn(studentEntity);
		when(jpaRepository.save(any(StudentEntity.class))).thenReturn(studentEntity);
		when(mapper.toStudent(any(StudentEntity.class))).thenReturn(student);

		// Evaluacion del comportamiento
		Student respuesta = studentPersistenceAdapter.save(student);

		// Comprobaciones o aserciones
		assertAll(() -> assertNotNull(respuesta), () -> assertEquals(student, respuesta),
				() -> assertEquals(1L, respuesta.getId()), () -> assertEquals("Pepito", respuesta.getFirstName()),
				() -> assertEquals("Dos Palotes", respuesta.getLastName()));

		verify(mapper, times(1)).toStudentEntity(student);
		verify(jpaRepository, times(1)).save(studentEntity);
		verify(mapper, times(1)).toStudent(studentEntity);
	}

	@Test
	void testDeleteById() {

		// Inicializacion
		doNothing().when(jpaRepository).deleteById(anyLong());

		// Evaluacion del comportamiento
		studentPersistenceAdapter.deleteById(1L);

		// Comprobaciones o aserciones
		verify(jpaRepository, times(1)).deleteById(1L);
	}

	@Test
	void testExistsByEmail() {

		// Inicializacion			
		when(jpaRepository.existsByEmailIgnoreCase(anyString())).thenReturn(Boolean.TRUE);
		
		// Evaluacion del comportamiento
		boolean respuesta = studentPersistenceAdapter.existsByEmail("pepito@gmail.com");

		// Comprobaciones o aserciones
		assertAll(			
			() -> assertTrue(respuesta)		
		);
				
		verify(jpaRepository, times(1)).existsByEmailIgnoreCase(anyString());
	}
}
