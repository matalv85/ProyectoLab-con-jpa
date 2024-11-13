package modelos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name ="Movimientos")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "Tipo")
public abstract class Movimiento {
	@Id
	@GeneratedValue
	private Long id;
	@NonNull
	@NotNull
	@Column(name = "fecha")
	private LocalTime fechaYHora;
	@NonNull
	@NotNull
	@Column(scale = 2)
	protected BigDecimal monto;
	@NonNull
	@NotNull
	private String descripcion;
	
	@ManyToOne
    @JoinColumn(name = "cuenta_id")
	@JsonBackReference("cuenta-movimientos")
    private Cuenta cuenta;

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
    
}
