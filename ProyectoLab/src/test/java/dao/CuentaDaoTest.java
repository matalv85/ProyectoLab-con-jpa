package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import configuracion.Configuracion;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import modelos.Cliente;
import modelos.Cuenta;
import modelos.CuentaArs;
import modelos.CuentaEur;
import modelos.CuentaUsd;
import modelos.Deposito;
import modelos.Direccion;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Configuracion.class})
@Transactional
class CuentaDaoTest {
	private static Direccion dir;
	private static Direccion dir2;
	private static Direccion dir3;
	private static Cliente cliTit;
	private static Cliente cliCot;
	private static Cliente cliCot2;
	private static Deposito mov;
	private static CuentaArs cue;
	
	@Autowired
	ClienteDao cliDao;
	@Autowired
	DireccionDao dirDao;
	@Autowired
	CuentaDao cueDao;
	@Autowired
	MovimientoDao movDao;
	
	@PersistenceContext
	EntityManager em;
	
	@BeforeEach
	void entityManagerConfig() throws Exception {
		dir = new Direccion("Cordoba", "1234", "CABA", "1234", "CABA");
		dir2 = new Direccion("Cordoba", "3456", "CABA", "1234", "CABA");
		dir3 = new Direccion("Cordoba", "3556", "CABA", "1234", "CABA");
		cliTit = new Cliente("Juan", "Perez", dir);
		cliCot = new Cliente("Pedro", "Gomez", dir2);
		cliCot2 = new Cliente("Jose", "Garcia", dir3);
		cue = new CuentaArs(LocalDate.of(2020, 10, 10), BigDecimal.valueOf(1000.0),BigDecimal.valueOf(1000.0) , cliTit);
		mov = new Deposito(LocalTime.now(), BigDecimal.valueOf(1000.0), "Transferencia recibida");
		cue.addCotitulares(cliCot);
	
	}
	
	
	@Test
	@Rollback(false)
	void insertarCuenta() {	
		
		dirDao.save(dir);
		dirDao.save(dir2);
		dirDao.save(dir3);
		cliDao.save(cliCot2);
		cliDao.save(cliTit);
		cliDao.save(cliCot);
		cliDao.save(cliCot2);
		cueDao.save(cue);
		
		em.flush();
		em.clear();
		
		Cuenta cuentaRead = cueDao.read(cue.getNumeroCuenta());
		
		assertEquals(cuentaRead, cue);

	}
	
	
	@Test
	@Rollback(false)
	void actualizarSaldo() {

		dirDao.save(dir);
		dirDao.save(dir2);
		dirDao.save(dir3);
		cliDao.save(cliTit);
		cliDao.save(cliCot);
		cueDao.save(cue);
		
		cue.acreditoSaldo(BigDecimal.valueOf(2000.00));
		cueDao.update(cue);
		
		em.flush();
		em.clear();	
		
		assertEquals(0, cueDao.read(cue.getNumeroCuenta()).getSaldoActual().compareTo(BigDecimal.valueOf(3000.00)));
	}
	
	@Test
	@Rollback(false)
	void addCotitular() {

		dirDao.save(dir);
	    dirDao.save(dir2);
	    dirDao.save(dir3);
	    cliDao.save(cliCot2);
	    cliDao.save(cliTit);
	    cliDao.save(cliCot);

	    // Add cotitulares
	    cue.addCotitulares(cliCot2);
	    cueDao.save(cue);

	    em.flush();
	    em.clear();

	    Cuenta cuentaRead = cueDao.read(cue.getNumeroCuenta());
	    Set<Cliente> cotitularesRead = cuentaRead.getCotitulares();


		System.out.print(cuentaRead.getCotitulares());
	    

	    assertTrue(cuentaRead.getCotitulares().contains(cliCot2));
	}
	
	@Test
	@Rollback(false)
	void addMovimiento() throws Exception {

		cue.addMovimiento(mov);
		movDao.save(mov);
		dirDao.save(dir);
		dirDao.save(dir2);
		dirDao.save(dir3);
		cliDao.save(cliCot2);
		cliDao.save(cliTit);
		cliDao.save(cliCot);
		cliDao.save(cliCot2);
		dirDao.save(dir3);
		cueDao.save(cue);
		em.flush();
		em.clear();	
		assertTrue(!cueDao.read(cue.getNumeroCuenta()).getMovimientos().isEmpty());
	}
	
	@Test
	@Rollback(false)
	void eliminarCuenta() {	

		dirDao.save(dir);
		dirDao.save(dir2);
		dirDao.save(dir3);
		cliDao.save(cliCot2);
		cliDao.save(cliTit);
		cliDao.save(cliCot);
		cliDao.save(cliCot2);
		dirDao.save(dir3);
		cueDao.save(cue);
		
		em.flush();
		em.clear();
		
		Cuenta cuenta = cueDao.read(cue.getNumeroCuenta());
	    cueDao.borrar(cuenta);
	    
	    em.flush();
		em.clear();
	    
		Optional<Cuenta> cuentaOpt = Optional.ofNullable(cueDao.read(cue.getNumeroCuenta()));
		assertTrue(cuentaOpt.isEmpty());

	}
	
	@Test
	@Rollback(false)
	void readAllByMoneda() {
		CuentaArs cueArs = new CuentaArs(LocalDate.of(2020, 10, 10), BigDecimal.valueOf(1000.0), BigDecimal.valueOf(1000.0), cliTit);
		CuentaUsd cueUsd = new CuentaUsd(LocalDate.of(2020, 10, 10), BigDecimal.valueOf(1000.0),BigDecimal.valueOf(1000.0) , cliTit);
		CuentaUsd cueUsd2 = new CuentaUsd(LocalDate.of(2020, 10, 10), BigDecimal.valueOf(1000.0),BigDecimal.valueOf(1000.0) , cliTit);
		CuentaUsd cueUsd3 = new CuentaUsd(LocalDate.of(2020, 10, 10), BigDecimal.valueOf(1000.0),BigDecimal.valueOf(1000.0) , cliTit);
		CuentaEur cueEur = new CuentaEur(LocalDate.of(2020, 10, 10), BigDecimal.valueOf(1000.0),BigDecimal.valueOf(1000.0) , cliTit);
		CuentaEur cueEur2 = new CuentaEur(LocalDate.of(2020, 10, 10), BigDecimal.valueOf(1000.0),BigDecimal.valueOf(1000.0) , cliTit);
		CuentaEur cueEur3 = new CuentaEur(LocalDate.of(2020, 10, 10), BigDecimal.valueOf(1000.0),BigDecimal.valueOf(1000.0) , cliTit);
		
		dirDao.save(dir);
		dirDao.save(dir2);
		dirDao.save(dir3);
		cliDao.save(cliCot2);
		cliDao.save(cliTit);
		cliDao.save(cliCot);
		cliDao.save(cliCot2);
		dirDao.save(dir3);
		cueDao.save(cue);
		cueDao.save(cueArs);
		cueDao.save(cueUsd);
		cueDao.save(cueUsd2);
		cueDao.save(cueUsd3);
		cueDao.save(cueEur);
		cueDao.save(cueEur2);
		cueDao.save(cueEur3);

		List<Cuenta> cuentasEnUsd = (List<Cuenta>) cueDao.readAllbyMoneda("USD");
		
		assertEquals(3, cuentasEnUsd.size());
	}
	

}