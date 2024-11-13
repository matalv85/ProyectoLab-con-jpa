package servicios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dao.ClienteDao;
import dao.CuentaDao;
import dao.MovimientoDao;
import modelos.Cliente;
import modelos.CuentaArs;
import modelos.CuentaUsd;
import modelos.Moneda;
import modelos.VentaDolares;

@ExtendWith(MockitoExtension.class)
class ServicioCompraVentaTest {

	@Mock
    private CuentaDao cuentaDao;

    @Mock
    private ClienteDao clienteDao;
    
    @Mock
    private MovimientoDao movDao;
    
    @Mock
    private ServicioCambioImpl servicioCambio;
    
    @Mock
    private ResultadoCambioImpl resultadoCambio;
    
    @Mock
    private CuentaArs cuentaArs;
    
    @Mock
    private CuentaArs cuentaArs2;
    
    @Mock
    private CuentaUsd cuentaUsd;
    
    @Mock
    private Cliente cliente;
    
    @Mock
    private Cliente cliente2;

    @InjectMocks
    private ServicioCompraVentaImpl servicioCompraVenta;

	@Test
	void ventaMonedaExtranjeraCorrecta() {	
        
		when(cuentaArs.getTitular()).thenReturn(cliente);
		when(cuentaUsd.getTitular()).thenReturn(cliente);
		when(cuentaUsd.getSaldoActual()).thenReturn(BigDecimal.valueOf(100000.00));
		when(resultadoCambio.getTasa()).thenReturn(1000.0);
        when(resultadoCambio.getResultado()).thenReturn(10000.0);
        when(servicioCambio.cambiar(Moneda.USD, Moneda.ARS, 10.0)).thenReturn(resultadoCambio);
        
        when(cuentaDao.read(1L)).thenReturn(cuentaArs);
        when(cuentaDao.read(2L)).thenReturn(cuentaUsd);
        
		assertTrue(servicioCompraVenta.vender(cliente.getId(), 2L, 1L, BigDecimal.valueOf(10.00)));
	}
	
	@Test
	void ventaMonedaExtranjeraClienteIncorrecto() {	
        
		when(cuentaArs.getTitular()).thenReturn(cliente);
		when(cuentaUsd.getTitular()).thenReturn(cliente2);
        
        when(cuentaDao.read(1L)).thenReturn(cuentaArs);
        when(cuentaDao.read(2L)).thenReturn(cuentaUsd);
        
		assertFalse(servicioCompraVenta.vender(cliente.getId(), 2L, 1L, BigDecimal.valueOf(1000.00)));
	}
	
	@Test
	void ventaMonedaExtranjeraSinSaldo() {	
        
		when(cuentaArs.getTitular()).thenReturn(cliente);
		when(cuentaUsd.getTitular()).thenReturn(cliente);
		
		when(cuentaUsd.getSaldoActual()).thenReturn(BigDecimal.valueOf(1.00));
		
        when(cuentaDao.read(1L)).thenReturn(cuentaArs);
        when(cuentaDao.read(2L)).thenReturn(cuentaUsd);
        
		assertFalse(servicioCompraVenta.vender(cliente.getId(), 2L, 1L, BigDecimal.valueOf(1000.00)));
	}
	
	@Test
	void ventaMonedaExtranjeraCuentaUsdCerrada() {	
        
		when(cuentaArs.getTitular()).thenReturn(cliente);
		when(cuentaUsd.getTitular()).thenReturn(cliente);
		
		when(cuentaUsd.getSaldoActual()).thenReturn(BigDecimal.valueOf(100000.00));
		
		when(cuentaUsd.getFechaCierre()).thenReturn(LocalDate.now());
		
        when(cuentaDao.read(1L)).thenReturn(cuentaArs);
        when(cuentaDao.read(2L)).thenReturn(cuentaUsd);
        
		assertFalse(servicioCompraVenta.vender(cliente.getId(), 2L, 1L, BigDecimal.valueOf(1000.00)));
	}
	
	@Test
	void ventaMonedaExtranjeraCuentaArsCerrada() {	
        
		when(cuentaArs.getTitular()).thenReturn(cliente);
		when(cuentaUsd.getTitular()).thenReturn(cliente);
		
		when(cuentaUsd.getSaldoActual()).thenReturn(BigDecimal.valueOf(100000.00));
		
		when(cuentaArs.getFechaCierre()).thenReturn(LocalDate.now());
		
        when(cuentaDao.read(1L)).thenReturn(cuentaArs);
        when(cuentaDao.read(2L)).thenReturn(cuentaUsd);
        
		assertFalse(servicioCompraVenta.vender(cliente.getId(), 2L, 1L, BigDecimal.valueOf(1000.00)));
	}
	
	@Test
	void ventaMonedaExtranjeraTipoCuentaIncorrecto() {	
        
		when(cuentaArs.getTitular()).thenReturn(cliente);
		when(cuentaArs2.getTitular()).thenReturn(cliente);
		
		when(cuentaArs2.getSaldoActual()).thenReturn(BigDecimal.valueOf(100000.00));
		
        when(cuentaDao.read(1L)).thenReturn(cuentaArs);
        when(cuentaDao.read(2L)).thenReturn(cuentaArs2);
        
		assertFalse(servicioCompraVenta.vender(cliente.getId(), 2L, 1L, BigDecimal.valueOf(1000.00)));
	}
	
	
}
