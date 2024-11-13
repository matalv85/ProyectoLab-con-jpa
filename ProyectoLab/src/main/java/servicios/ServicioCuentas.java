package servicios;

import modelos.CuentaArs;
import modelos.CuentaEur;
import modelos.CuentaUsd;

public interface ServicioCuentas {
	boolean agregarCotitular(long idCliente, long idCuenta);
	
	boolean altaDeCuenta(CuentaArs cuenta);
	
	boolean altaDeCuenta(CuentaEur cuenta);
	
	boolean altaDeCuenta(CuentaUsd cuenta);

}
