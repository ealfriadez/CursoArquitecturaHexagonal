package pe.edu.unfv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import lombok.RequiredArgsConstructor;

@EnableFeignClients
@SpringBootApplication
@RequiredArgsConstructor
public class CursoArquitecturaHexagonalApplication {	
	
	public static void main(String[] args) {
		SpringApplication.run(CursoArquitecturaHexagonalApplication.class, args);
	}
}
