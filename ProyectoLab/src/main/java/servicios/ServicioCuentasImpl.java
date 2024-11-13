package servicios;

import modelos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.ClienteDao;
import dao.CuentaDao;
import dao.DireccionDao;


@Service
public class ServicioCuentasImpl implements ServicioCuentas {
	
	@Autowired
	CuentaDao cueDao;
	
	@Autowired
	ClienteDao cliDao;
	
	@Autowired
	DireccionDao dirDao;

	
	@Override
	public boolean agregarCotitular(long idCliente, long idCuenta){
	
		Cuenta cuenta = cueDao.read(idCuenta);
		Cliente cliente = cliDao.read(idCliente);
		
		/*
		 * Agregar a un cliente como cotitular de una cuenta, dadas la id del cliente y la id de la cuenta. 
		 * La operación no se puede realizar si el cliente ya figura como titular o cotitular de la cuenta ni si la cuenta está cerrada. 
		 */
		
		if(cuenta == null || cliente == null) {
			return false;
		}
		
		if(cuenta.getFechaCierre() != null) {
			return false;
		}
		
		if(cuenta.getTitular() == cliente) {
			return false;
		}
		
		if(cuenta.getCotitulares().contains(cliente)) {
			return false;
		}
		
		cuenta.addCotitulares(cliente);
		cueDao.update(cuenta);
		
		return true;
		
	}

	@Override
	public boolean altaDeCuenta(CuentaArs cuenta) {
		dirDao.save(cuenta.getTitular().getDireccion());
		cliDao.save(cuenta.getTitular());
        cueDao.save(cuenta);
		return true;
	}

	@Override
	public boolean altaDeCuenta(CuentaEur cuenta) {
		if (cuenta.getTitular() != null) {
			Direccion direccion = cuenta.getTitular().getDireccion();
			if (direccion != null) {
				dirDao.save(direccion);
			} else {
				return false;
			}
			cliDao.save(cuenta.getTitular());
		} else {
			return false;
		}

        cueDao.save(cuenta);
		return true;
	}

	@Override
	public boolean altaDeCuenta(CuentaUsd cuenta) {
		dirDao.save(cuenta.getTitular().getDireccion());
		cliDao.save(cuenta.getTitular());
        cueDao.save(cuenta);
		return true;
	}
}
