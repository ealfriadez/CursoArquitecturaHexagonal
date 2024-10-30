package pe.edu.unfv.infrastructure.adapters.input.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pe.edu.unfv.infrastructure.adapters.input.rest.models.enums.ErrorType.FUNCTIONAL;
import static pe.edu.unfv.infrastructure.utils.ErrorCatalog.STUDENT_NOT_FOUND;
import static pe.edu.unfv.infrastructure.adapters.input.rest.models.enums.ErrorType.SYSTEM;
import static pe.edu.unfv.infrastructure.utils.ErrorCatalog.INTERNAL_SERVER_ERROR;
import static pe.edu.unfv.infrastructure.utils.ErrorCatalog.STUDENT_BAD_PARAMETERS;
import static pe.edu.unfv.infrastructure.utils.ErrorCatalog.STUDENT_EMAIL_ALREADY_EXISTS;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.databind.ObjectMapper;

import pe.edu.unfv.application.ports.input.StudentInputPort;
import pe.edu.unfv.domain.exceptions.StudentNotFoundException;
import pe.edu.unfv.infrastructure.adapters.input.rest.mapper.StudentRestMapper;
import pe.edu.unfv.infrastructure.adapters.input.rest.models.request.StudentCreateRequest;
import pe.edu.unfv.infrastructure.adapters.input.rest.models.response.ErrorResponse;
import pe.edu.unfv.utils.TestUtils;

@WebMvcTest(controllers = { StudentRestAdapter.class })
class GlobalControllerAdviceTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private StudentInputPort inputPort;
	
	@MockBean
	private StudentRestMapper restMapper;

	
	@Test
	void whenThrowsStudentNotFoundException_thenReturnNotFound() throws Exception {
		// Inicializacion
		when(inputPort.findById(anyLong())).thenThrow(new StudentNotFoundException());

		// Evaluacion del comportamiento
		mockMvc.perform(get("/students/{id}", 1L).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(result -> {
					ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
							ErrorResponse.class);

					// Comprobaciones o aserciones					
					assertAll(							
							() -> assertEquals(STUDENT_NOT_FOUND.getCode(), errorResponse.getCode()),
							() -> assertEquals(FUNCTIONAL, errorResponse.getType()),
							() -> assertEquals(STUDENT_NOT_FOUND.getMessage(), errorResponse.getMessage()),
							() -> assertNotNull(errorResponse.getTimestamp())
					);
				})
				.andDo(print());

		// Comprobaciones o aserciones
		verify(inputPort, times(1)).findById(anyLong());
	}
	
	@Test
	void whenThrowsStudentNotValidException_thenReturnBadRequest_OneParameter() throws Exception {
		// Inicializacion
		StudentCreateRequest invalidRequest = TestUtils.buildInvalidStudentCreateRequest();
		
		// Evaluacion del comportamiento
		mockMvc.perform(post("/students")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(invalidRequest)))	
		
				// Comprobaciones o aserciones
				.andExpect(status().isBadRequest())
				.andExpect(result -> {
					ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
							ErrorResponse.class);
										
					assertAll(							
							() -> assertThat(errorResponse.getCode()).isEqualTo(STUDENT_BAD_PARAMETERS.getCode()),
							() -> assertThat(errorResponse.getType()).isEqualTo(FUNCTIONAL),
							() -> assertThat(errorResponse.getMessage()).isEqualTo(STUDENT_BAD_PARAMETERS.getMessage()),
							() -> assertThat(errorResponse.getDetails().size()).isEqualTo(1),
							() -> assertThat(errorResponse.getTimestamp()).isNotNull()
					);
				})
				.andDo(print());

		// Comprobaciones o aserciones		
	}
	
	@Test
	void whenThrowsStudentNotValidException_thenReturnBadRequest_AllParameters() throws Exception {
		// Inicializacion		
		
		// Evaluacion del comportamiento
		mockMvc.perform(post("/students")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}"))	
		
				// Comprobaciones o aserciones
				.andExpect(status().isBadRequest())
				.andExpect(result -> {
					ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
							ErrorResponse.class);
										
					assertAll(							
							() -> assertThat(errorResponse.getCode()).isEqualTo(STUDENT_BAD_PARAMETERS.getCode()),
							() -> assertThat(errorResponse.getType()).isEqualTo(FUNCTIONAL),
							() -> assertThat(errorResponse.getMessage()).isEqualTo(STUDENT_BAD_PARAMETERS.getMessage()),
							() -> assertThat(errorResponse.getDetails()).isNotNull(),
							() -> assertThat(errorResponse.getTimestamp()).isNotNull()
					);
				})
				.andDo(print());

		// Comprobaciones o aserciones		
	}
}
