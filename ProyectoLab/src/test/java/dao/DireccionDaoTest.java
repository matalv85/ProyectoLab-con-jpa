package dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import configuracion.Configuracion;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import modelos.Direccion;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Configuracion.class})
@Transactional
class DireccionDaoTest {
	
	private static Direccion dir;
	
	@Autowired
	DireccionDao dirDao;
	
	@PersistenceContext
	EntityManager em;
	
	@BeforeEach
	void entityManagerConfig() throws Exception {
		dir = new Direccion("Cordoba", "3456", "CABA", "1234", "CABA");
	
	}
	 
	@Test
	void insertarDireccion() {		
		dirDao.save(dir);
		Direccion direccionRead = dirDao.read(dir.getId());
		assertEquals(dir.getCalle(), direccionRead.getCalle());
		assertEquals(dir.getNumero(), direccionRead.getNumero());
		assertEquals(dir.getCiudad(), direccionRead.getCiudad());
		assertEquals(dir.getCodigoPostal(), direccionRead.getCodigoPostal());
		assertEquals(dir.getProvincia(), direccionRead.getProvincia());
	}
	
	@Test
	void modificarDireccion() {		
		dirDao.save(dir);
		
		dir.setProvincia("Corrientes");
		
		dirDao.update(dir);
		
		em.flush();
		em.clear();	
		
		
		Direccion direccionRead = dirDao.read(dir.getId());
		assertEquals(dir.getCalle(), direccionRead.getCalle());
		assertEquals(dir.getNumero(), direccionRead.getNumero());
		assertEquals(dir.getCiudad(), direccionRead.getCiudad());
		assertEquals(dir.getCodigoPostal(), direccionRead.getCodigoPostal());
		assertEquals(dir.getProvincia(), direccionRead.getProvincia());
	}
	
	
	@Test
	void eliminarDireccion() {	
		dirDao.save(dir);
		
		dirDao.borrar(dir);
		em.flush();
		em.clear();	
		Direccion direccionRead = dirDao.read(dir.getId());
		assertEquals(null, dirDao.read(dir.getId()));
	}

}
