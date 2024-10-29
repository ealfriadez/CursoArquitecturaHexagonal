package pe.edu.unfv.infrastructure.adapters.input.rest;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import pe.edu.unfv.application.ports.input.StudentInputPort;
import pe.edu.unfv.domain.models.Student;
import pe.edu.unfv.infrastructure.adapters.input.rest.mapper.StudentRestMapper;
import pe.edu.unfv.infrastructure.adapters.input.rest.models.response.StudentResponse;
import pe.edu.unfv.utils.TestUtils;

@WebMvcTest(controllers = StudentRestAdapter.class)
class StudentRestAdapterTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StudentInputPort inputPort;

	@MockBean
	private StudentRestMapper restMapper;

	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {

		objectMapper = new ObjectMapper();
	}

	@Test
	@DisplayName("Test find by id when id exists")
	void testFindById() throws Exception {

		// Inicializacion
		Student student = TestUtils.buildStudent();
		StudentResponse studentResponse = TestUtils.buildStudentResponse();
		when(inputPort.findById(anyLong())).thenReturn(student);
		when(restMapper.toStudentResponse(any(Student.class))).thenReturn(studentResponse);

		// Evaluacion del comportamiento
		mockMvc.perform(get("/students/{id}", 1L).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(studentResponse))).andDo(print());

		// Comprobaciones o aserciones
		verify(inputPort, times(1)).findById(1L);
		verify(restMapper, times(1)).toStudentResponse(student);
	}

	@Test
	void testFindByIds() throws Exception {

		// Inicializacion
		List<Long> ids = Collections.singletonList(1L);
		List<Student> studentList = Collections.singletonList(TestUtils.buildStudent());
		List<StudentResponse> studentListResponse = Collections.singletonList(TestUtils.buildStudentResponse());
		when(inputPort.findByIds(anyList())).thenReturn(studentList);
		when(restMapper.toStudentResponses(anyList())).thenReturn(studentListResponse);

		// Evaluacion del comportamiento
		mockMvc.perform(get("/students/find-by-ids").param("ids", "1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(studentListResponse))).andDo(print());

		// Comprobaciones o aserciones
		verify(inputPort, times(1)).findByIds(anyList());
		verify(restMapper, times(1)).toStudentResponses(anyList());
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
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteBy() {
		fail("Not yet implemented");
	}

}
