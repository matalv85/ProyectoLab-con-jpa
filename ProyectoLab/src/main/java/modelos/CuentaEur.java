package modelos;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@DiscriminatorValue("EUR")
public class CuentaEur extends Cuenta{
	public CuentaEur(LocalDate fechaCreacion, BigDecimal saldoInicial, BigDecimal descubierto, Cliente titular) {
		super(fechaCreacion, saldoInicial, descubierto, titular);
		setSaldoActual(saldoInicial);
	}

}
