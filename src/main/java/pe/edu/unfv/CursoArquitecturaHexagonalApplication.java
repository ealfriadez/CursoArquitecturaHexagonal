package pe.edu.unfv;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import lombok.RequiredArgsConstructor;
import pe.edu.unfv.infrastructure.adapters.output.persistence.models.StudentEntity;
import pe.edu.unfv.infrastructure.adapters.output.persistence.repository.StudentJpaRepository;

@EnableFeignClients
@SpringBootApplication
@RequiredArgsConstructor
public class CursoArquitecturaHexagonalApplication implements CommandLineRunner{

	private final StudentJpaRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursoArquitecturaHexagonalApplication.class, args);
	}
	
	public void run(String... args) throws Exception {
		repository.saveAll(Arrays.asList(
				new StudentEntity(null, "Santiago", "Alfriadez", 11, "snalfriadez@gmail.com", "Calle13"),
				new StudentEntity(null, "Sebastian", "Alfriadez", 23, "salfriadez@gmail.com", "Calle14"),
				new StudentEntity(null, "Doris", "Coronado", 30, "pilaxis@gmail.com", "Calle15")
				));
	}
}
