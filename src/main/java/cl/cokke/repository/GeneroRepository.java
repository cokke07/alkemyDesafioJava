package cl.cokke.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.cokke.model.Genero;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {

}
