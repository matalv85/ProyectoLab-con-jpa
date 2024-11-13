package modelos;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CuentaArsTest {
	private Cliente titular;
	private Cliente coTitular;
	private Cuenta cuenta;
	private CompraDolares compraDolares;
	private VentaDolares ventaDolares;
	private Extraccion extraccion;
	private Deposito deposito;
	private TransferenciaEnviada transEnviada;
	private TransferenciaRecibida transRecibida;
	
	@BeforeEach
	void setUp(@Mock Cliente titular,
			@Mock Cliente coTitular,
			@Mock CompraDolares compraDolares,
			@Mock VentaDolares ventaDolares,
			@Mock Extraccion extraccion,
			@Mock Deposito deposito,
			@Mock TransferenciaEnviada transEnviada,
			@Mock TransferenciaRecibida transRecibida) {
		
			this.titular = titular;
			this.coTitular = coTitular;
			this.compraDolares = compraDolares;
			this.ventaDolares = ventaDolares;
			this.extraccion = extraccion;
			this.deposito = deposito;
			this.transEnviada = transEnviada;
			this.transRecibida = transRecibida;
			cuenta = new CuentaArs(LocalDate.of(2020, 10, 10), BigDecimal.valueOf(10.00), BigDecimal.valueOf(10.00), titular);
	}
	@Test
	void testSaldoInicial() {
		assertEquals(BigDecimal.valueOf(10.00), cuenta.getSaldoInicial());
	}
	
	@Test
	void testTitular() {
		assertEquals(titular, cuenta.getTitular());
	}
	
	@Test
	void testFechaCreacion() {
		assertEquals(LocalDate.of(2020, 10, 10), cuenta.getFechaCreacion());
	}
	
	@Test
	void testFechaCierreNulo() {
		assertEquals(null, cuenta.getFechaCierre());
	}
	
	@Test
	void testAddCotitulares() {
		cuenta.addCotitulares(coTitular);
		assertEquals(1, cuenta.getCotitulares().size());

	}

	@Test
	void testAddMovimientos() throws Exception {
		cuenta.addMovimiento(compraDolares);
		cuenta.addMovimiento(ventaDolares);
		cuenta.addMovimiento(deposito);
		cuenta.addMovimiento(extraccion);
		cuenta.addMovimiento(transEnviada);
		cuenta.addMovimiento(transRecibida);
		assertEquals(6, cuenta.getMovimientos().size());
	}

	@Test
	void testActualizoSaldo() {
		cuenta.acreditoSaldo(BigDecimal.valueOf(10.00));
		assertEquals(BigDecimal.valueOf(20.00), cuenta.getSaldoActual());
	}

	@Test
	void testCierroCuenta() {
		cuenta.cierroCuenta(LocalDate.of(2024, 10, 10));
		assertEquals(LocalDate.of(2024, 10, 10), cuenta.getFechaCierre());
	}

}
