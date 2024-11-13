package dao;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import configuracion.Configuracion;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import modelos.Cliente;
import modelos.Direccion;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Configuracion.class})
@Transactional
class ClienteDaoTest {
	private static Direccion dir;
	private static Cliente cli;
	
	@Autowired
	ClienteDao cliDao;
	@Autowired
	DireccionDao dirDao;
	
	@PersistenceContext
	EntityManager em;
	
	@BeforeEach
	void entityManagerConfig() throws Exception {
		dir = new Direccion("Cordoba", "3456", "CABA", "1234", "CABA");
		cli = new Cliente("Juan", "Perez", dir);

	
	}
	
	@Test
	@Rollback(false)
	void insertarCliente() {

		
		
		dirDao.save(dir);
		cliDao.save(cli);
		
		em.flush();
		em.clear();
		
		Cliente clienteRead = cliDao.read(cli.getId());
		assertEquals(cli, clienteRead);

		
	}

	
	@Test
	@Rollback(false)
	void eliminarCliente() {

		dirDao.save(dir);
		cliDao.save(cli);
		
		cliDao.borrar(cli);
		
		em.flush();
		em.clear();
		
		assertEquals(null, cliDao.read(cli.getId()));

	}
	
}
