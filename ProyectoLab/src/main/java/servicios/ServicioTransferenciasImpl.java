package servicios;

import java.math.BigDecimal;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.CuentaDao;
import dao.MovimientoDao;
import modelos.Cuenta;
import modelos.TransferenciaEnviada;
import modelos.TransferenciaRecibida;

@Service
public class ServicioTransferenciasImpl implements ServicioTransferencias{

	@Autowired
	private CuentaDao cuentaDao;

	@Autowired
	private MovimientoDao movDao;
	
	
	/*
	 * Realizar una transferencia a una cuenta del mismo banco a partir de la id de la cuenta originante, el monto a transferir y la cuenta destino. 
	 * Solo se puede realizar si las cuentas no están cerradas y el saldo de la cuenta originante lo permiten. Notar que se deben crear y asociar los movimientos correspondientes en las cuentas. 
	 */
	@Override
	public boolean hacerTransferencia(Cuenta cuentaOrigen, Cuenta cuentaDestino, BigDecimal monto) {
		
		if(cuentaOrigen == null || cuentaDestino == null) {
			return false;
		}
		
		if(cuentaOrigen.getFechaCierre() != null || cuentaDestino.getFechaCierre() != null) {
			return false;
			
		}
		
		if(cuentaOrigen.getSaldoActual().compareTo(monto) < 0) {
			return false;
			
		}

		if(!cuentaOrigen.getClass().equals(cuentaDestino.getClass())){
			return false;
		}
		
		TransferenciaEnviada transEnviada = new TransferenciaEnviada(LocalTime.now(), monto, "Transferencia enviada");
		TransferenciaRecibida transRecibida = new TransferenciaRecibida(LocalTime.now(), monto, "Transferencia Recibida");
		transEnviada.setCuentaDestino(cuentaDestino.getNumeroCuenta().toString());
		transRecibida.setCuentaOrigen(cuentaOrigen.getNumeroCuenta().toString());

		cuentaOrigen.debitoSaldo(monto);
		cuentaDestino.acreditoSaldo(monto);
		
		movDao.save(transRecibida);
		movDao.save(transEnviada);

		try {
			cuentaOrigen.addMovimiento(transEnviada);
			cuentaDestino.addMovimiento(transRecibida);

		} catch (Exception e) {
			throw new RuntimeException(e);

		}

		
		cuentaDao.update(cuentaOrigen);
		cuentaDao.update(cuentaDestino);
		
		return true;
	}

}
