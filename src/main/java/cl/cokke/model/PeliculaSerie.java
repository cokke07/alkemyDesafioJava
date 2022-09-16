package cl.cokke.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "peliculas_series")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PeliculaSerie {

	@Id
	@Column(name="id_pelicula_serie")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="titulo")
	private String tittulo;
	
	@Column(name="fecha_creacion")
	private String fechaCreacion;
	
	@Column(name="calificacion")
	@Max(5)
	@Min(1)
	private int calificacion;
	
	//@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@ManyToMany ()
	@JsonIgnore
	private List<Personaje> personajes;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_genero", referencedColumnName = "id_genero")
	@JsonBackReference
	private Genero genero;
}
