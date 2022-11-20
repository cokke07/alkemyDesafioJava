package cl.cokke.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.cokke.dto.PeliculaSerieDTO;
import cl.cokke.dto.PersonajeDTO;
import cl.cokke.model.PeliculaSerie;
import cl.cokke.service.GeneroService;
import cl.cokke.service.PeliculaSerieService;
import cl.cokke.service.PersonajeService;

@RestController
@RequestMapping("/api/v1/disney")
public class PeliculaSerieController {
	
	@Autowired
	private PersonajeService personajeServicio;

	@Autowired
	private PeliculaSerieService peliculaSerieServicio;

	@Autowired
	private GeneroService generoServicio;

	// Buscar todas las peliculas y series y mostrarlos en la peticion
		@GetMapping("/movies")
		public ResponseEntity<List<PeliculaSerieDTO>> listarTodasPeliculaSeries() {
			List<PeliculaSerieDTO> peliSeries = new ArrayList<PeliculaSerieDTO>();
			peliSeries = peliculaSerieServicio.buscarTodos();

			if (peliSeries.isEmpty()) {
				return new ResponseEntity<>(peliSeries, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(peliSeries, HttpStatus.OK);

		}

		// Crear peliculas y series nuevas
		@PostMapping("/movies/create")
		public ResponseEntity<PeliculaSerie> insertarPeliculaSerie(@RequestBody PeliculaSerie p) {
			PeliculaSerie nuevaPeliSerie = (peliculaSerieServicio.guardarPeliculaSerie(p));
			System.out.println(nuevaPeliSerie.toString());
			return new ResponseEntity<>(nuevaPeliSerie, HttpStatus.CREATED);
		}

		// Buscar peli o series por ID
		@GetMapping("/movies/{id}")
		public ResponseEntity<PeliculaSerie> buscarPeliculaSeriePorId(@PathVariable("id") Long id) {

			try {
				Optional<PeliculaSerie> peliSerieEncontrada = peliculaSerieServicio.buscarPorId(id);

				if (peliSerieEncontrada.isPresent()) {
					return new ResponseEntity<>(peliSerieEncontrada.get(), HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			} catch (Exception e) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		// Eliminar peli o series pasando la ID
		@DeleteMapping("/movies/{id}")
		public ResponseEntity<HttpStatus> eliminarPeluculaSerie(@PathVariable("id") Long id) {

			Optional<PeliculaSerie> peliSerieEncontrada = peliculaSerieServicio.buscarPorId(id);

			if (peliSerieEncontrada.isPresent()) {
				peliculaSerieServicio.eliminarPeliculaSerie(id);
				System.out.println("Pelicula o serie eliminada");
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		}

		// Modificar personaje pasando la ID
		@PutMapping("/movies/{id}")
		public ResponseEntity<PeliculaSerie> actualizarPeliculaSerie(@PathVariable("id") Long id,
				@RequestBody PeliculaSerie p) {

			Optional<PeliculaSerie> peliSerieEncontrada = peliculaSerieServicio.buscarPorId(id);
			if (peliSerieEncontrada.isPresent()) {
				peliSerieEncontrada.get().setTitulo(p.getTitulo());
				peliSerieEncontrada.get().setFechaCreacion(p.getFechaCreacion());
				peliSerieEncontrada.get().setCalificacion(p.getCalificacion());
				peliSerieEncontrada.get().setPersonajes(p.getPersonajes());

				return new ResponseEntity<>(peliculaSerieServicio.guardarPeliculaSerie(peliSerieEncontrada.get()),
						HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		}
		
		// Buscar por nombre
		@GetMapping("/movies/")
		public ResponseEntity<List<PeliculaSerieDTO>> buscarPeliculaSeriePorNombre(@RequestParam String name) {

			List<PeliculaSerieDTO> peliculaBuscada = peliculaSerieServicio.findByTituloContainingIgnoreCase(name);

			return new ResponseEntity<>(peliculaBuscada, HttpStatus.OK);
		}
}
