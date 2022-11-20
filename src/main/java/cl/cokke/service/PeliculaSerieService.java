package cl.cokke.service;

import java.util.List;
import java.util.Optional;

import cl.cokke.dto.PeliculaSerieDTO;
import cl.cokke.model.PeliculaSerie;

public interface PeliculaSerieService {

	public List<PeliculaSerieDTO> buscarTodos();
	public Optional<PeliculaSerie> buscarPorId(Long id);
	public PeliculaSerie guardarPeliculaSerie(PeliculaSerie p);
	public PeliculaSerie editarPeliculaSerie(PeliculaSerie p);
	public void eliminarPeliculaSerie(Long id); 
	
	public List<PeliculaSerieDTO> findByTituloContainingIgnoreCase(String searchTerm);
}
