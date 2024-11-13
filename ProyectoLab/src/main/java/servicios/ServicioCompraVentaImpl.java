package servicios;

import java.math.BigDecimal;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.ClienteDao;
import dao.CuentaDao;
import dao.MovimientoDao;
import modelos.Cuenta;
import modelos.CuentaArs;
import modelos.CuentaEur;
import modelos.CuentaUsd;
import modelos.Moneda;
import modelos.VentaDolares;

@Service
public class ServicioCompraVentaImpl implements ServicioCompraVenta {
	
	@Autowired
	CuentaDao cuentaDao;	
	ClienteDao clienteDao;	
	MovimientoDao movDao;
	ServicioCambio servicioCambio;
	ResultadoCambio resultadoCambio;

	@Override
	public boolean vender(Long idcliente, Long idcuentaExtranjera, Long idCuentaPesos, BigDecimal monto) {
		
		Cuenta cuentaArs = cuentaDao.read(idCuentaPesos);
		Cuenta cuentaExt = cuentaDao.read(idcuentaExtranjera);
		
		
		if(cuentaArs.getTitular() != cuentaExt.getTitular()) {
			return false;
			
		}
		if(cuentaExt.getSaldoActual().compareTo(monto) < 0) {
			return false;
			
		}
		
		if(cuentaArs.getFechaCierre() != null || cuentaExt.getFechaCierre() != null) {
			return false;
			
		}
		
		Moneda monedaDeCuenta;
		if(cuentaExt instanceof CuentaUsd) {
			monedaDeCuenta = Moneda.USD;
			
		} else if(cuentaExt instanceof CuentaEur){
			monedaDeCuenta = Moneda.EUR;
			
		} else {
			return false;
			
		}
		resultadoCambio = servicioCambio.cambiar(monedaDeCuenta, Moneda.ARS, monto.doubleValue());
		
		if(monedaDeCuenta == Moneda.USD) {
			VentaDolares vendedor = new VentaDolares(LocalTime.now(), monto, "Venta Dolares", (CuentaUsd) cuentaExt, (CuentaArs) cuentaArs);
			VentaDolares comprador = new VentaDolares(LocalTime.now(), BigDecimal.valueOf(resultadoCambio.getResultado()), "Venta Dolares", (CuentaUsd) cuentaExt, (CuentaArs) cuentaArs);
			vendedor.setCotizacion(BigDecimal.valueOf(resultadoCambio.getTasa()));
			comprador.setCotizacion(BigDecimal.valueOf(resultadoCambio.getTasa()));
			
			try {
				cuentaExt.addMovimiento(vendedor);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			try {
				cuentaArs.addMovimiento(comprador);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			movDao.save(vendedor);
			movDao.save(comprador);
		}
		
		cuentaExt.debitoSaldo(monto);
		cuentaArs.acreditoSaldo(BigDecimal.valueOf(resultadoCambio.getResultado()));
		
		cuentaDao.update(cuentaArs);
		cuentaDao.update(cuentaExt);
		
		return true;
	}

}
