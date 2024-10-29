package pe.edu.unfv.infrastructure.adapters.output.restclient;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pe.edu.unfv.infrastructure.adapters.output.restclient.client.CourseFeignClient;

@ExtendWith(MockitoExtension.class)
class CourseRestClientAdapterTest {

	@Mock
	private CourseFeignClient feignClient;

	@InjectMocks
	private CourseRestClientAdapter courseRestClientAdapter;

	@Test
	void testRemoveStudentFromCollection() {

		// Inicializacion
		doNothing().when(feignClient).removeStudentFromCollection(anyLong());

		// Evaluacion del comportamiento
		courseRestClientAdapter.removeStudentFromCollection(1L);

		// Comprobaciones o aserciones
		verify(feignClient, times(1)).removeStudentFromCollection(anyLong());
	}
}
