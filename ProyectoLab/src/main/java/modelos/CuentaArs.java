package modelos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@DiscriminatorValue("ARS")
public class CuentaArs extends Cuenta {

	public CuentaArs() {
		super();
	}

	public CuentaArs(LocalDate fechaCreacion, BigDecimal saldoInicial, BigDecimal descubierto, Cliente titular) {
		super(fechaCreacion, saldoInicial, descubierto, titular);
		setSaldoActual(saldoInicial);
	}

}
