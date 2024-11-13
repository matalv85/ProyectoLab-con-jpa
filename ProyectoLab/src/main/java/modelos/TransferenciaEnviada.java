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
@DiscriminatorValue("TransEnviada")
public class TransferenciaEnviada extends Movimiento{
	public TransferenciaEnviada(LocalTime fechaYHora, BigDecimal monto, String descripcion) {
		super(fechaYHora, monto, descripcion);

	}

	@NonNull
	@NotNull
	@Column(name = "cuenta destino")
	private String cuentaDestino;
	
}