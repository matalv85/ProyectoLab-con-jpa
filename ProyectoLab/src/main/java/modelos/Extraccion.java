package modelos;

import java.math.BigDecimal;
import java.time.LocalTime;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@DiscriminatorValue("Extraccion")
public class Extraccion extends ExtraccionYDeposito {

	public Extraccion(LocalTime fechaYHora, BigDecimal monto, String descripcion) {
		super(fechaYHora, monto, descripcion);

	}

}
