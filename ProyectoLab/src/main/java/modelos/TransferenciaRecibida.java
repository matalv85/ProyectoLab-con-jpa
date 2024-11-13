package modelos;

import java.math.BigDecimal;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@NoArgsConstructor
@DiscriminatorValue("TransRecibida")
public class TransferenciaRecibida extends Movimiento{
	public TransferenciaRecibida(LocalTime fechaYHora, BigDecimal monto, String descripcion) {
		super(fechaYHora, monto, descripcion);

	}

	@NonNull
	@NotNull
	@Column(name = "cuenta origen")
	private String cuentaOrigen;
}
