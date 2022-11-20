package cl.cokke.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cl.cokke.model.PeliculaSerie;



@Repository
public interface PeliculaSerieRepository extends JpaRepository<PeliculaSerie, Long> {

	@Query(value = "SELECT * FROM pelicula_serie U WHERE LOWER(U.titulo) LIKE %?1%",nativeQuery = true)
	public List<PeliculaSerie> findByTitulo(String nombre);
	
	public List<PeliculaSerie> findByTituloContainingIgnoreCase(String searchTerm);
}
