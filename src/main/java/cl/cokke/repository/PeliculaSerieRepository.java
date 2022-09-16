package cl.cokke.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.cokke.model.PeliculaSerie;

@Repository
public interface PeliculaSerieRepository extends JpaRepository<PeliculaSerie, Long> {

}
