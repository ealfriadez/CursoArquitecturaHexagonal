package pe.edu.unfv.infrastructure.adapters.input.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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
import pe.edu.unfv.infrastructure.adapters.input.rest.models.request.StudentCreateRequest;
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
	void testFindAll() throws Exception {

		// Inicializacion
		List<Student> studentList = TestUtils.studentList();
		List<StudentResponse> studentListResponse = TestUtils.studentListResponseMock();
		when(inputPort.findAll()).thenReturn(studentList);
		when(restMapper.toStudentResponses(anyList())).thenReturn(studentListResponse);

		// Evaluacion del comportamiento
		mockMvc.perform(get("/students").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(studentListResponse))).andDo(print());

		// Comprobaciones o aserciones
		verify(inputPort, times(1)).findAll();
		verify(restMapper, times(1)).toStudentResponses(anyList());
	}

	@Test
	void testSave() throws Exception {

		// Inicializacion
		StudentCreateRequest studentCreateRequest = TestUtils.buildStudentCreateRequest();
		Student student = TestUtils.buildStudent();
		StudentResponse studentResponse = TestUtils.buildStudentResponse();
		when(restMapper.toStudent(any(StudentCreateRequest.class))).thenReturn(student);
		when(inputPort.save(any(Student.class))).thenReturn(student);
		when(restMapper.toStudentResponse(any(Student.class))).thenReturn(studentResponse);

		// Evaluacion del comportamiento
		mockMvc.perform(post("/students").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(studentCreateRequest))).andExpect(status().isCreated())

				// Comprobaciones o aserciones
				.andExpect(header().string("Location", "/students/1"))
				.andExpect(content().json(objectMapper.writeValueAsString(studentResponse))).andDo(print());

		// Comprobaciones o aserciones
		verify(restMapper, times(1)).toStudent(any(StudentCreateRequest.class));
		verify(inputPort, times(1)).save(student);
		verify(restMapper, times(1)).toStudentResponse(student);
	}

	@Test
	void testUpdate() throws Exception {

		// Inicializacion
		StudentCreateRequest request = TestUtils.buildStudentCreateRequest();
		Student student = TestUtils.buildStudent();
		StudentResponse response = TestUtils.buildStudentResponse();
		when(restMapper.toStudent(any(StudentCreateRequest.class))).thenReturn(student);
		when(inputPort.update(anyLong(), any(Student.class))).thenReturn(student);
		when(restMapper.toStudentResponse(any(Student.class))).thenReturn(response);

		// Evaluacion del comportamiento
		mockMvc.perform(put("/students/{id}", 1L).contentType(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andDo(print()).andExpect(result -> {
					StudentResponse responseStudentResponse = objectMapper
							.readValue(result.getResponse().getContentAsString(), StudentResponse.class);

					// Comprobaciones o aserciones
					assertEquals("Pepito", responseStudentResponse.getFirstName());
					assertThat(responseStudentResponse.getLastName()).isEqualTo("Dos Palotes");

					assertAll(() -> assertEquals(15, responseStudentResponse.getAge()),
							() -> assertEquals("pepito@gmail.com", responseStudentResponse.getEmail()),
							() -> assertEquals("Clle. 1", responseStudentResponse.getAddress()),
							() -> assertEquals(LocalDate.now().toString(), responseStudentResponse.getTimestamp()));
				});

		// Comprobaciones o aserciones
		verify(restMapper, times(1)).toStudent(any(StudentCreateRequest.class));
		verify(inputPort, times(1)).update(anyLong(), any(Student.class));
		verify(restMapper, times(1)).toStudentResponse(any(Student.class));
	}

	@Test
	void testDeleteBy() throws Exception {

		// Inicializacion
		doNothing().when(inputPort).deleteById(anyLong());

		// Evaluacion del comportamiento
		mockMvc.perform(delete("/students/{id}", 1L).contentType(MediaType.APPLICATION_JSON))
		
		// Comprobaciones o aserciones
		.andExpect(status().isNoContent()).andDo(print());
		
		// Comprobaciones o aserciones
		verify(inputPort, times(1)).deleteById(anyLong());
	}
}
