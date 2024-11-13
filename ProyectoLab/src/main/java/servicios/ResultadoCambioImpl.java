package servicios;

import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Service
public class ResultadoCambioImpl implements ResultadoCambio {
	
	private Double tasa;
	private Double resultado;

}
