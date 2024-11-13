package modelos;

import java.math.BigDecimal;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@DiscriminatorValue("VentaUSD")
public class VentaDolares extends CompraVentaDolares {
	
	@NonNull
	@Column(name = "Origen")
	private Cuenta cuentaOrigen;
	@NonNull
	@Column(name = "destino")
	private Cuenta cuentaDestino;

	public VentaDolares(LocalTime fechaYHora, BigDecimal monto, String descripcion, CuentaUsd cuentaOrigen, CuentaArs cuentaDestino) {
		super(fechaYHora, monto, descripcion);
		this.cuentaOrigen = cuentaOrigen;
		this.cuentaDestino = cuentaDestino;

		if(cuentaOrigen.getSaldoActual().compareTo(monto) == 1) {
			cuentaOrigen.debitoSaldo(monto);
			cuentaDestino.acreditoSaldo(monto.multiply(cotizacion));
		} else {
			throw new IllegalArgumentException();
		}
	}

}
