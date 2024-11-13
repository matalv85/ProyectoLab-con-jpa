package controladores;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dao.ClienteDao;
import dao.CuentaDao;
import dao.DireccionDao;
import dao.MovimientoDao;
import lombok.Setter;
import modelos.Cliente;
import modelos.Cuenta;
import modelos.CuentaArs;
import modelos.CuentaEur;
import modelos.CuentaUsd;
import modelos.Movimiento;
import modelos.TransferenciaEnviada;
import modelos.TransferenciaRecibida;
import servicios.ServicioCuentas;
import servicios.ServicioCuentasImpl;
import servicios.ServicioTransferencias;

@RestController
@Setter
@RequestMapping("/cuentas")
public class ControladorCuentas {
	
	@Autowired
	ClienteDao clieDao;
	
	@Autowired
	DireccionDao dirDao;
	
	@Autowired
	CuentaDao cueDao;
	
	@Autowired
	MovimientoDao movDao;
	
	@Autowired
	ServicioTransferencias servicioTransferencias;
	
	@Autowired
	ServicioCuentas servicioCuentas;
	
	@GetMapping("") 
	public List<Cuenta> all() {
		return (List<Cuenta>) cueDao.readAll();

	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Cuenta> cuentaById (@PathVariable("id") long id) {
		Cuenta cuenta = cueDao.read(id);
		if (cuenta != null) {
			return new ResponseEntity<>(cuenta, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
			
	}

	@PostMapping(value = "/insert/ARS", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CuentaArs> addCuenta(@RequestBody CuentaArs cuenta) {
		if(servicioCuentas.altaDeCuenta(cuenta)) {
			return new ResponseEntity<>(cuenta, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }
	
	@PostMapping("/insert/USD")
    public ResponseEntity<CuentaUsd> addCuenta(@RequestBody CuentaUsd cuenta) {
		if(servicioCuentas.altaDeCuenta(cuenta)) {
			return new ResponseEntity<>(cuenta, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
        
    }
	
	@PostMapping("/insert/EUR")
    public ResponseEntity<CuentaEur> addCuenta(@RequestBody CuentaEur cuenta) {
		if(servicioCuentas.altaDeCuenta(cuenta)) {
			return new ResponseEntity<>(cuenta, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }
	
	@PutMapping("/cotitular")
    public ResponseEntity<Cuenta> addCotitular(
			@RequestParam Long idCuenta,
			@RequestParam Long idCliente) {


        if(servicioCuentas.agregarCotitular(idCliente, idCuenta)) {
        	return new ResponseEntity<>(cueDao.read(idCuenta), HttpStatus.CREATED);
        	
        } else {
        	return new ResponseEntity<>(cueDao.read(idCuenta), HttpStatus.NOT_FOUND);
        }
        
    }
	
	@PostMapping("/movimientos/transferencias")
	public ResponseEntity<String> addMovimiento(
	    @RequestParam Long idOrigen,
	    @RequestParam Long idDestino,
	    @RequestParam Double monto) {
		
		if(servicioTransferencias.hacerTransferencia(cueDao.read(idOrigen), cueDao.read(idDestino), BigDecimal.valueOf(monto))) {
			return new ResponseEntity<>("Transferencia Realizada", HttpStatus.CREATED);

		} else {
			return new ResponseEntity<>("Transferencia No Realizada", HttpStatus.NOT_FOUND);

		}
	    
	}
	
	@GetMapping("/movimientos/{id}") 
	public List<Movimiento> movimientosById (@PathVariable("id") long id) {
		return (List<Movimiento>) movDao.readAllById(id);
	}

}
