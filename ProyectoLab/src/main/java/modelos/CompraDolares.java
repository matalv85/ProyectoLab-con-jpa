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
@DiscriminatorValue("CompraUSD")
public class CompraDolares extends CompraVentaDolares {
	
	@NonNull
	@Column(name = "Origen")
	private Cuenta cuentaOrigen;
	@NonNull
	@Column(name = "Destino")
	private Cuenta cuentaDestino;

	public CompraDolares(LocalTime fechaYHora, BigDecimal monto, String descripcion , CuentaArs cuentaOrigen, CuentaUsd cuentaDestino) {
		super(fechaYHora, monto, descripcion);
		this.cuentaOrigen = cuentaOrigen;
		this.cuentaDestino = cuentaDestino;
		
		if(cuentaOrigen.getSaldoActual().compareTo(monto) == 1) {
			cuentaOrigen.debitoSaldo(monto);
			cuentaDestino.acreditoSaldo(monto.divide(cotizacion));
		} else {
			throw new IllegalArgumentException();
		}

	}	

}
