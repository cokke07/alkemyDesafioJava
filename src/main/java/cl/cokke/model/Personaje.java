package cl.cokke.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "personajes")
public class Personaje {

	@Id
	@Column(name="id_personaje")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="edad")
	private int edad;
	
	@Column(name="peso")
	private int peso;
	
	@Column(name="historia")
	private String historia;
	
	@Column(name="image")
	private String image;
	
	@ManyToMany ()
    @JoinTable(
            name = "personajes_x_peliculas",
            joinColumns = @JoinColumn(name = "id_personaje"),
            inverseJoinColumns = @JoinColumn(name = "id_pelicula_serie"))
	@JsonIgnore
    private List<PeliculaSerie> peliculaSerie;
	
	
}
