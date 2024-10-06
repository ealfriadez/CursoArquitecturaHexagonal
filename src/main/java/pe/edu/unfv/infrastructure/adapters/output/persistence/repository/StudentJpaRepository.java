package pe.edu.unfv.infrastructure.adapters.output.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import pe.edu.unfv.infrastructure.adapters.output.persistence.models.StudentEntity;

public interface StudentJpaRepository extends CrudRepository<StudentEntity, Long>{

}
