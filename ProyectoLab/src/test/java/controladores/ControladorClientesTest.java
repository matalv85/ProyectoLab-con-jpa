package controladores;

import com.fasterxml.jackson.databind.ObjectMapper;
import configuracion.Configuracion;
import controladores.ControladorClientes;
import dao.ClienteDao;
import modelos.Cliente;
import modelos.Direccion;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ControladorClientes.class)
@ContextConfiguration(classes = Configuracion.class)
@AutoConfigureMockMvc
@ComponentScan(basePackages = {"modelos", "dao", "controladores"})
public class ControladorClientesTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ClienteDao clieDao;

	ObjectMapper mapper = new ObjectMapper();

	@Test
	public void readAll() throws Exception {
		Direccion dir = new Direccion("Cordoba", "3456", "CABA", "1234", "CABA");
		Cliente cli = new Cliente("Juan", "Perez", dir);
		List<Cliente> clientes = new ArrayList<>();
		clientes.add(cli);
		when(clieDao.readAll()).thenReturn(clientes);

		mvc.perform(get("/clientes")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(mapper.writeValueAsString(clientes)));
	}

	@Test
	public void readClienteById() throws Exception {
		Cliente cliente = new Cliente();
		when(clieDao.read(1L)).thenReturn(cliente);

		mvc.perform(get("/clientes/id/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(mapper.writeValueAsString(cliente)));
	}

	@Test
	public void readClienteByName() throws Exception {
		Direccion dir = new Direccion("Cordoba", "3456", "CABA", "1234", "CABA");
		Cliente cli = new Cliente("Juan", "Perez", dir);
		List<Cliente> clientes = new ArrayList<>();
		clientes.add(cli);
		when(clieDao.readAllbyName("Juan")).thenReturn(clientes);

		mvc.perform(get("/clientes/nombre/Juan")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(mapper.writeValueAsString(clientes)));
	}

	@Test
	public void readClienteByDireccion() throws Exception {
		Direccion dir = new Direccion("Cordoba", "3456", "CABA", "1234", "CABA");

		mvc.perform(put("/clientes/direccion")
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(dir)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(mapper.writeValueAsString(dir)));
	}

}