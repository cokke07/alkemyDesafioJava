package cl.cokke.service;

import java.util.List;
import java.util.Optional;

import cl.cokke.model.Genero;

public interface GeneroService {

	public List<Genero> buscarTodos();
	public Optional<Genero> buscarPorId(Long id);
	public Genero guardarGenero(Genero g);
	public Genero editarGenero(Genero g);
	public void eliminarGenero(Long id); 
}
