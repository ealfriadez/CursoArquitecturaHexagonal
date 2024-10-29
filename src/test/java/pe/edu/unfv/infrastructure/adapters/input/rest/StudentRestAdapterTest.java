package pe.edu.unfv.infrastructure.adapters.input.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import pe.edu.unfv.application.ports.input.StudentInputPort;
import pe.edu.unfv.infrastructure.adapters.input.rest.mapper.StudentRestMapper;

@WebMvcTest(StudentRestAdapter.class)
class StudentRestAdapterTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private StudentInputPort inputPort;

	@MockBean
	private StudentRestMapper restMapper;
	
	private ObjectMapper objectMapper;

}
