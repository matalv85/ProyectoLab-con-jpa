package servicios;

import modelos.Moneda;

public interface ServicioCambio {
	
	public ResultadoCambio cambiar(Moneda de, Moneda a, Double monto);


}
