package cl.cokke.service;

import java.util.List;
import java.util.Optional;

import cl.cokke.model.Personaje;

public interface PersonajeService {

	public List<Personaje> buscarTodos();
	public Optional<Personaje> buscarPorId(Long id);
	public Personaje guardarPersonaje(Personaje p);
	public Personaje editarPersonaje(Personaje p);
	public void eliminarPersonaje(Long id);
	public Personaje findByNombreContainingIgnoreCase(String nombre);
	
	//public Personaje findByPeliculaSerie(String pelicula);
	public List<Personaje> findAllByEdad(Integer edad);
	
}
