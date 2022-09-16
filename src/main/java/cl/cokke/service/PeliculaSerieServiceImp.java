package cl.cokke.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.cokke.model.PeliculaSerie;
import cl.cokke.repository.PeliculaSerieRepository;

@Service
public class PeliculaSerieServiceImp implements PeliculaSerieService {

	@Autowired
	private PeliculaSerieRepository peliculaSerieRepository;
	
	@Override
	public List<PeliculaSerie> buscarTodos() {
		// TODO Auto-generated method stub
		return peliculaSerieRepository.findAll();
	}

	@Override
	public Optional<PeliculaSerie> buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return peliculaSerieRepository.findById(id);
	}

	@Override
	public PeliculaSerie guardarPeliculaSerie(PeliculaSerie p) {
		// TODO Auto-generated method stub
		return peliculaSerieRepository.save(p);
	}

	@Override
	public PeliculaSerie editarPeliculaSerie(PeliculaSerie p) {
		// TODO Auto-generated method stub
		return peliculaSerieRepository.save(p);
	}

	@Override
	public void eliminarPeliculaSerie(Long id) {
		peliculaSerieRepository.deleteById(id);

	}

}
