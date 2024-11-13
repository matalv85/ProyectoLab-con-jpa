package modelos;

import java.math.BigDecimal;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Getter
@NoArgsConstructor
public abstract class ExtraccionYDeposito extends Movimiento {
	@NonNull
	@Column(name = "caja / cajero")
	private String cajero;
	
	public ExtraccionYDeposito(LocalTime fechaYHora, BigDecimal monto, String descripcion) {
		super(fechaYHora, monto, descripcion);

	}	
}
