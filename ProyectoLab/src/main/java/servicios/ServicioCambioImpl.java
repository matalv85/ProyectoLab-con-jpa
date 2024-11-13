package servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import modelos.Moneda;

@Service
public class ServicioCambioImpl implements ServicioCambio {

	private Double tasaUsdArs = 1000.00;
	private Double tasaEurArs = 1200.00;
	
	@Autowired
	private ResultadoCambioImpl resultado;
	
	@Override
	public ResultadoCambio cambiar(Moneda de, Moneda a, Double monto) {
		 
		/*
		 * Realizar una operación de venta de moneda extranjera, dada la id de un cliente, la id de una cuenta en moneda extranjera y la id de una cuenta de moneda en pesos del cliente y el monto a vender. 
		 * Para realizar la conversión, se cuenta con un servicio que implementa la siguiente interface.  Notar que se deben crear y asociar los movimientos correspondientes en las cuentas.
		 * vender(idcliente, idcuentaExtranjera, idCuentaPesos, monto),
		 * Daos: cuenta, cliente, movimientos.
		 */
		
		if(de == Moneda.USD && a == Moneda.ARS) {
			resultado.setTasa(tasaUsdArs);
			resultado.setResultado(monto * tasaUsdArs);
		}
		
		if(de == Moneda.ARS && a == Moneda.USD) {
			resultado.setTasa(tasaUsdArs);
			resultado.setResultado(monto / tasaUsdArs);
			
		}
		
		if(de == Moneda.EUR && a == Moneda.ARS) {
			resultado.setTasa(tasaEurArs);
			resultado.setResultado(monto * tasaEurArs);
			
		}
		
		if(de == Moneda.ARS && a == Moneda.EUR) {
			resultado.setTasa(tasaEurArs);
			resultado.setResultado(monto / tasaEurArs);;
			
		}
		
		return resultado;
	}
	
	

}
