package modelos;

import java.math.BigDecimal;
import java.time.LocalTime;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("Deposito")
public class Deposito extends ExtraccionYDeposito {

	public Deposito(LocalTime fechaYHora, BigDecimal monto, String descripcion) {
		super(fechaYHora, monto, descripcion);

	}

}
