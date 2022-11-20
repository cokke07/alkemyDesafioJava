package cl.cokke.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cl.cokke.model.Personaje;


@Repository
public interface PersonajeRepository extends JpaRepository<Personaje, Long> {

	@Query(value = "SELECT * FROM personaje U WHERE LOWER(U.nombre) LIKE %?1%",nativeQuery = true)
	public List<Personaje> findByNombre(String nombre);
	//forma declarativa para obtener automaticamente
	public List<Personaje> findByNombreContainingIgnoreCase(String searchTerm);
	
	public List<Personaje> findAllByEdad(Integer edad);
	
	@Query(value = "SELECT * FROM personajes P where P.pelicula_serie_id_pelicula_serie = ?",nativeQuery = true)
	public List<Personaje> findByPeliculaSerieId(Long id);
}
