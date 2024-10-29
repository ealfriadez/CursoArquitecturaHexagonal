package pe.edu.unfv.infrastructure.adapters.output.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
		when(jpaRepository.findById(anyLong())).thenReturn(Optional.of(TestUtils.buildStudentEntityMock()));
		when(mapper.toStudent(any(StudentEntity.class))).thenReturn(TestUtils.buildStudent());
		
		// Evaluacion del comportamiento
		Optional<Student> respuesta = studentPersistenceAdapter.findById(1L);

		// Comprobaciones o aserciones
		assertNotNull(respuesta);
		assertEquals(1L, respuesta.get().getId());
		assertEquals("Pepito", respuesta.get().getFirstName());
		verify(jpaRepository, times(1)).findById(1L);
		//verify(mapper, times(1)).toStudent(TestUtils.buildStudent());
	}

	@Test
	void testFindAll() {
		fail("Not yet implemented");
	}

	@Test
	void testSave() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteById() {
		fail("Not yet implemented");
	}

	@Test
	void testExistsByEmail() {
		fail("Not yet implemented");
	}

	@Test
	void testFindByIds() {
		fail("Not yet implemented");
	}

}
