package modelos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MovimientoTest {

	private Cliente titular;
	private	CuentaArs cuentaTitularPesos;
	private	CuentaUsd cuentaTitularDolares;
	private Direccion direccion;
	private Set<Movimiento> movimientos = new HashSet<Movimiento>();
	
	
	@BeforeEach
	void setUp(@Mock CuentaArs cuentaTitularPesos,
			@Mock CuentaUsd cuentaTitularDolares,
			@Mock Direccion direccion,
			@Mock Cliente cliente) {
		this.titular = titular;
		this.cuentaTitularPesos = cuentaTitularPesos;
		this.cuentaTitularDolares = cuentaTitularDolares;
		this.titular = titular;

	}


	@Test
	void testMovimientos() throws Exception {
		when(cuentaTitularPesos.getSaldoActual()).thenReturn(BigDecimal.valueOf(10000.00));
		when(cuentaTitularDolares.getSaldoActual()).thenReturn(BigDecimal.valueOf(10000.00));
		Deposito deposito = new Deposito(LocalTime.now(), BigDecimal.valueOf(100.00), "Deposito nuevo");
		Extraccion extraccion = new Extraccion(LocalTime.now(), BigDecimal.valueOf(100.00), "Extraccion nueva");
		TransferenciaRecibida transferenciaRecibida = new TransferenciaRecibida(LocalTime.now(), BigDecimal.valueOf(100.00), "Transferencia Recibida");
		TransferenciaEnviada transferenciaEnviada = new TransferenciaEnviada(LocalTime.now(), BigDecimal.valueOf(100.00), "Transferencia Enviada");
		CompraDolares compraDolares = new CompraDolares(LocalTime.now(), BigDecimal.valueOf(10.00), "Compra Dolares", cuentaTitularPesos, cuentaTitularDolares);
		VentaDolares ventaDolares = new VentaDolares(LocalTime.now(), BigDecimal.valueOf(10.00), "Venta Dolares", cuentaTitularDolares, cuentaTitularPesos);

		movimientos.add(deposito);
		movimientos.add(extraccion);
		movimientos.add(transferenciaRecibida);
		movimientos.add(transferenciaEnviada);
		movimientos.add(compraDolares);
		movimientos.add(ventaDolares);
		
		when(cuentaTitularPesos.getMovimientos()).thenReturn(movimientos);
		
		assertTrue(cuentaTitularPesos.getMovimientos().contains(deposito));
		assertTrue(cuentaTitularPesos.getMovimientos().contains(extraccion));
		assertTrue(cuentaTitularPesos.getMovimientos().contains(transferenciaRecibida));
		assertTrue(cuentaTitularPesos.getMovimientos().contains(transferenciaEnviada));
		assertTrue(cuentaTitularPesos.getMovimientos().contains(compraDolares));
		assertTrue(cuentaTitularPesos.getMovimientos().contains(ventaDolares));

		
	}

}
