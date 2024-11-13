package controladores;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import configuracion.Configuracion;
import modelos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import controladores.ControladorCuentas;
import dao.ClienteDao;
import dao.CuentaDao;
import dao.DireccionDao;
import dao.MovimientoDao;
import servicios.ServicioCuentas;
import servicios.ServicioTransferencias;

@WebMvcTest(ControladorClientes.class)
@ContextConfiguration(classes = Configuracion.class)
@AutoConfigureMockMvc
@ComponentScan(basePackages = {"modelos", "dao", "controladores"})
public class ControladorCuentasTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ClienteDao clieDao;

	@MockBean
	private CuentaDao cueDao;

	@MockBean
	private MovimientoDao movDao;

	@MockBean
	private ServicioCuentas servicioCuentas;

	@MockBean
	private ServicioTransferencias servicioTransferencias;

	ObjectMapper mapper;

	@BeforeEach
	public void setup() {
		mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
	}

	@Test
	public void readAll() throws Exception {
		Direccion dir = new Direccion("Cordoba", "3456", "CABA", "1234", "CABA");
		Cliente cli = new Cliente("Juan", "Perez", dir);
		CuentaArs cue = new CuentaArs(LocalDate.now(), BigDecimal.valueOf(100.00), BigDecimal.valueOf(1000.0), cli);
		List<Cuenta> cuentas = new ArrayList<>();
		cuentas.add(cue);
		when(cueDao.readAll()).thenReturn(cuentas);

		mvc.perform(get("/cuentas")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(mapper.writeValueAsString(cuentas)));
	}

	@Test
	public void readCuentaById() throws Exception {
		CuentaArs cuenta = new CuentaArs();
		when(cueDao.read(1L)).thenReturn(cuenta);

		mvc.perform(get("/cuentas/id/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(mapper.writeValueAsString(cuenta)));
	}

	@Test
	public void addCuentaArs() throws Exception {
		Direccion dir = new Direccion("Cordoba", "3456", "CABA", "1234", "CABA");
		Cliente cli = new Cliente("Juan", "Perez", dir);
		CuentaArs cue = new CuentaArs(LocalDate.now(), BigDecimal.valueOf(100.00), BigDecimal.valueOf(1000.0), cli);

		when(servicioCuentas.altaDeCuenta(cue)).thenReturn(true);

		mvc.perform(post("/cuentas/insert/ARS")
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(cue)))
				.andExpect(status().isCreated())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json(mapper.writeValueAsString(cue)));
	}

	@Test
	public void addCuentaUsd() throws Exception {
		Direccion dir = new Direccion("Cordoba", "3456", "CABA", "1234", "CABA");
		Cliente cli = new Cliente("Juan", "Perez", dir);
		CuentaUsd cue = new CuentaUsd(LocalDate.now(), BigDecimal.valueOf(100.00), BigDecimal.valueOf(1000.0), cli);

		when(servicioCuentas.altaDeCuenta(cue)).thenReturn(true);

		mvc.perform(post("/cuentas/insert/USD")
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(cue)))
				.andExpect(status().isCreated())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json(mapper.writeValueAsString(cue)));
	}

	@Test
	public void addCuentaEur() throws Exception {
		Direccion dir = new Direccion("Cordoba", "3456", "CABA", "1234", "CABA");
		Cliente cli = new Cliente("Juan", "Perez", dir);
		CuentaEur cue = new CuentaEur(LocalDate.now(), BigDecimal.valueOf(100.00), BigDecimal.valueOf(1000.0), cli);

		when(servicioCuentas.altaDeCuenta(cue)).thenReturn(true);

		mvc.perform(post("/cuentas/insert/EUR")
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(cue)))
				.andExpect(status().isCreated())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json(mapper.writeValueAsString(cue)));
	}

	@Test
	public void addCotitular() throws Exception {
		Long idCuenta = 1L;
		Long idCliente = 2L;
		Cuenta cuenta = new CuentaArs();
		when(servicioCuentas.agregarCotitular(idCliente, idCuenta)).thenReturn(true);
		when(cueDao.read(idCuenta)).thenReturn(cuenta);

		mvc.perform(put("/cuentas/cotitular")
						.param("idCuenta", idCuenta.toString())
						.param("idCliente", idCliente.toString()))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(mapper.writeValueAsString(cuenta)));
	}

	@Test
	public void addMovimiento() throws Exception {
		Long idOrigen = 1L;
		Long idDestino = 2L;
		Double monto = 1.0;

		Direccion dir = new Direccion("Cordoba", "3456", "CABA", "1234", "CABA");
		Cliente cli = new Cliente("Juan", "Perez", dir);
		CuentaArs cue = new CuentaArs(LocalDate.now(), BigDecimal.valueOf(100.00), BigDecimal.valueOf(1000.0), cli);

		Direccion dir2 = new Direccion("Cordoba", "3456", "CABA", "1234", "CABA");
		Cliente cli2 = new Cliente("Juan", "Perez", dir2);
		CuentaArs cue2 = new CuentaArs(LocalDate.now(), BigDecimal.valueOf(100.00), BigDecimal.valueOf(1000.0), cli2);

		when(servicioTransferencias.hacerTransferencia(cue, cue2, BigDecimal.valueOf(monto))).thenReturn(true);
		when(cueDao.read(idOrigen)).thenReturn(cue);
		when(cueDao.read(idDestino)).thenReturn(cue2);

		mvc.perform(post("/cuentas/movimientos/transferencias")
						.param("idOrigen", idOrigen.toString())
						.param("idDestino", idDestino.toString())
						.param("monto", monto.toString()))
				.andExpect(status().isCreated())
				.andExpect(content().string("Transferencia Realizada"));
	}

	@Test
	public void movimientosById_() throws Exception {
		List<Movimiento> movimientos = new ArrayList<>();
		Extraccion extraccion = new Extraccion( LocalTime.now(), BigDecimal.valueOf(100.0), "Extraccion");
		movimientos.add(extraccion);
		when(movDao.readAllById(1L)).thenReturn(movimientos);

		mvc.perform(get("/cuentas/movimientos/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(mapper.writeValueAsString(movimientos)));
	}

}