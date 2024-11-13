package modelos;

import java.math.BigDecimal;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NonNull;

@Data
@Entity
public abstract class CompraVentaDolares extends Movimiento {
	public CompraVentaDolares(LocalTime fechaYHora, BigDecimal monto, String descripcion) {
		super(fechaYHora, monto, descripcion);
		
	}

	@NonNull
	@Column(scale = 2)
	private BigDecimal comision = BigDecimal.valueOf(0.00);
	@NonNull
	@Column(scale = 2)
	protected BigDecimal cotizacion = BigDecimal.valueOf(10.00);
	
}
