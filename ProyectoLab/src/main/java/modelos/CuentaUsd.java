package modelos;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@DiscriminatorValue("USD")
public class CuentaUsd extends Cuenta {
	
	public CuentaUsd(LocalDate fechaCreacion, BigDecimal saldoInicial, BigDecimal descubierto, Cliente titular) {
		super(fechaCreacion, saldoInicial, descubierto, titular);
		setSaldoActual(saldoInicial);
	}

}
