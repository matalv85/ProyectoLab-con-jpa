package modelos;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Direccion {
	@Id
	@GeneratedValue
	private Long id;
	@NonNull
	@NotNull
	@JsonProperty("calle")
	private String calle;
	@NonNull
	@NotNull
	@JsonProperty("numero")
	private String numero;
	private String departamento;
	private String piso;
	@NonNull
	@NotNull
	@JsonProperty("ciudad")
	private String ciudad;
	@NonNull
	@NotNull
	@JsonProperty("codigoPostal")
	@Column(name = "codigo postal")
	private String codigoPostal;
	@NonNull
	@NotNull
	@JsonProperty("provincia")
	private String provincia;
	
}
