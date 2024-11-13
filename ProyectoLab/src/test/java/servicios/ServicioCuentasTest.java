package servicios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dao.ClienteDao;
import dao.CuentaDao;
import dao.DireccionDao;
import modelos.Cliente;
import modelos.CuentaArs;
import modelos.Direccion;

@ExtendWith(MockitoExtension.class)
public class ServicioCuentasTest {
		
		@Mock
	    private CuentaDao cuentaDao;

	    @Mock
	    private ClienteDao clienteDao;
	    
	    @Mock
	    private DireccionDao dirDao;
	    
	    @Mock
	    private CuentaArs cuenta;
	    
	    @Mock
	    private Cliente clienteTitular;
	    
	    @Mock
	    private Cliente clienteCotitular;
	    
	    @Mock Direccion direccion;

	    @InjectMocks
	    private ServicioCuentasImpl servicioCuentas;

		@Test
		void agregarCotitularCorrectamente() {	
	        
	        when(cuentaDao.read(1L)).thenReturn(cuenta);
	        when(clienteDao.read(2L)).thenReturn(clienteCotitular);
	        when(cuenta.getFechaCierre()).thenReturn(null);
	        when(cuenta.getTitular()).thenReturn(clienteTitular);
			
			assertTrue(servicioCuentas.agregarCotitular(2L, 1L));
		}
		
		@Test
		void agregarCotitularQueEsTitular() {	
	        
	        when(cuentaDao.read(1L)).thenReturn(cuenta);
	        when(clienteDao.read(1L)).thenReturn(clienteTitular);
	        when(cuenta.getFechaCierre()).thenReturn(null);
	        when(cuenta.getTitular()).thenReturn(clienteTitular);
			
			assertFalse(servicioCuentas.agregarCotitular(1L, 1L));
		}
		
		@Test
		void agregarCotitularQueEsCotitular() {	
	        
			when(cuentaDao.read(1L)).thenReturn(cuenta);
			when(clienteDao.read(2L)).thenReturn(clienteCotitular);
	        when(cuenta.getFechaCierre()).thenReturn(null);
	        when(cuenta.getTitular()).thenReturn(clienteTitular);
	        
	        HashSet<Cliente> cotitulares = new HashSet<>();
	        cotitulares.add(clienteCotitular);
	        
	        when(cuenta.getCotitulares()).thenReturn(cotitulares);
			
			assertFalse(servicioCuentas.agregarCotitular(2L, 1L));
		}
		
		@Test
		void agregarCuenta() {
			when(clienteTitular.getDireccion()).thenReturn(direccion);
			when(cuenta.getTitular()).thenReturn(clienteTitular);
			assertTrue(servicioCuentas.altaDeCuenta(cuenta));

		}
	   
	}