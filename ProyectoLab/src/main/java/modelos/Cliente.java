package modelos;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.Hibernate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name ="Clientes")
public class Cliente {
	@Id
	@GeneratedValue
	private Long id;
	
	@NonNull
	@NotNull
	@JsonProperty("nombre")
	private String nombre;
	
	@NonNull
	@NotNull
	@JsonProperty("apellido")
	private String apellido;
	
	@NonNull
    @NotNull
    @OneToOne(optional = false, orphanRemoval = true, cascade = CascadeType.MERGE)
    @JoinColumn(name = "direccion_id")
	@JsonProperty("direccion")
    private Direccion direccion;

	private String telefono;
	
	@Email
	private String email;
	
	@OneToMany(mappedBy = "titular")
	@JsonManagedReference("cliente-cuentas")
	private Set<Cuenta> cuentas = new HashSet<>();

	@ManyToMany(mappedBy = "cotitulares")
    private Set<Cuenta> cotitulares = new HashSet<>();
	
	public void addCuentaTitular(Cuenta cuenta) {
		cuentas.add(cuenta);
        cuenta.setTitular(this);
	}
	
	public void addCuentaCotitular(Cuenta cuenta) {
		cotitulares.add(cuenta);
		cuenta.getCotitulares().add(this);
        
	}

	public Set<Cuenta> getTitular() {
		return cuentas;
	}
	
	public Set<Cuenta> getCotitular() {
		return cotitulares;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Cliente cliente = (Cliente) o;
        return id != null && Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
	
}
