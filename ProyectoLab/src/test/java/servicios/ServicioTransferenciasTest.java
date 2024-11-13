package servicios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dao.CuentaDao;
import dao.MovimientoDao;

import modelos.CuentaArs;

@ExtendWith(MockitoExtension.class)
public class ServicioTransferenciasTest {
	
	@Mock
    private CuentaDao cuentaDao;

    @Mock
    private MovimientoDao movDao;
    
    @Mock
    private CuentaArs cuentaOrigen;
    
    @Mock
    private CuentaArs cuentaDestino;

    @InjectMocks
    private ServicioTransferenciasImpl servicioTransferencias;

	@Test
	void hacerTransferenciaCorrecta() {	

		when(cuentaOrigen.getSaldoActual()).thenReturn(new BigDecimal("100.00"));
		assertTrue(servicioTransferencias.hacerTransferencia(cuentaOrigen, cuentaDestino, new BigDecimal("10.00")));
	}
	
	@Test
	void hacerTransferenciaMontoMayor() {	

		when(cuentaOrigen.getSaldoActual()).thenReturn(new BigDecimal("100.00"));
		assertFalse(servicioTransferencias.hacerTransferencia(cuentaOrigen, cuentaDestino, new BigDecimal("1000000.00")));
	}
	
	@Test
	void hacerTransferenciaCuentaCerradaOrigen() {	

		when(cuentaOrigen.getFechaCierre()).thenReturn(LocalDate.now());
		assertFalse(servicioTransferencias.hacerTransferencia(cuentaOrigen, cuentaDestino, new BigDecimal("1000000.00")));
	}
	
	@Test
	void hacerTransferenciaCuentaCerradaDestino() {	

		when(cuentaDestino.getFechaCierre()).thenReturn(LocalDate.now());
		assertFalse(servicioTransferencias.hacerTransferencia(cuentaOrigen, cuentaDestino, new BigDecimal("1000000.00")));
	}
   
}