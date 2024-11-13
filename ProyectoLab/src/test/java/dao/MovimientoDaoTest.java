package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import configuracion.Configuracion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import modelos.Cliente;
import modelos.CuentaArs;
import modelos.Deposito;
import modelos.Direccion;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Configuracion.class})
@Transactional
class MovimientoDaoTest {
	
	private static Deposito deposito;
	
	@Autowired
	MovimientoDao movDao;
	
	@PersistenceContext
	EntityManager em;

	@BeforeEach
	void entityManagerConfig() throws Exception {
		deposito = new Deposito(LocalTime.now(), BigDecimal.valueOf(1000.0), "Transferencia recibida");
	
	}

	@Test
	void test() {
		movDao.save(deposito);
		em.flush();
		em.clear();	
	}

}
