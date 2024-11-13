package modelos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClienteTest {

	private Cliente titular;
	private Cuenta cuentaTitular;
	private	Cuenta cuentaCotitular;
	private Direccion direccion;
	
	
	@BeforeEach
	void setUp(@Mock Cuenta cuentaTitular,
			@Mock Cuenta cuentaCotitular,
			@Mock Direccion direccion) {
		this.titular = titular;
		this.cuentaTitular = cuentaTitular;
		this.cuentaCotitular = cuentaCotitular;
		titular = new Cliente("Jose", "lopez", direccion);

	}

	@Test
	void testTitular() {

		cuentaTitular.setTitular(titular);
		titular.addCuentaTitular(cuentaTitular);
		
		when(cuentaTitular.getTitular()).thenReturn(titular);
		assertEquals(titular, cuentaTitular.getTitular());
	}
	
	@Test
	void testCotitular() {

		cuentaTitular.setTitular(titular);
		titular.addCuentaTitular(cuentaTitular);
		titular.addCuentaCotitular(cuentaCotitular);
		
		assertTrue(titular.getCotitular().contains(cuentaCotitular));
	}

}
