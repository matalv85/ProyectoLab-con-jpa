package controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dao.ClienteDao;
import dao.DireccionDao;
import lombok.Setter;
import modelos.Cliente;
import modelos.Direccion;

@RestController
@Setter
@RequestMapping("/clientes")
public class ControladorClientes {
	
	@Autowired
	ClienteDao clieDao;
	
	@Autowired
	DireccionDao dirDao;
	
	@GetMapping("") 
	public List<Cliente> readAll() {
		return (List<Cliente>) clieDao.readAll();

	}
	
	@GetMapping("/id/{id}") 
	public ResponseEntity<Cliente> clienteByid (@PathVariable("id") long id) {
		Cliente cliente = clieDao.read(id);
		if (cliente != null)
			//return new ResponseEntity<>(cliente, HttpStatus.OK);
			return new ResponseEntity<>(cliente, HttpStatus.OK);
		else 
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/nombre/{nombre}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Cliente>> clienteByName (@PathVariable("nombre") String nombre) {
		List<Cliente> clientes = (List<Cliente>) clieDao.readAllbyName(nombre);
		if (clientes != null){
			return new ResponseEntity<>(clientes, HttpStatus.OK);
		}
		else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/direccion")
	public ResponseEntity<Direccion> add(@RequestBody Direccion direccion){
        dirDao.update(direccion);
        return new ResponseEntity<>(direccion, HttpStatus.OK);
    }
	
}
