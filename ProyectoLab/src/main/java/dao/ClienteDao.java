package dao;

import java.util.Collection;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import modelos.Cliente;
import modelos.Cuenta;

public interface ClienteDao extends Dao<Cliente> {
	
	public void save(Cliente cliente);

	public Cliente read(Long id);

	public void update(Cliente cliente);

	public void borrar(Cliente cliente);

	public Collection<Cliente> readAll();
	
	public Collection<Cliente> readAllbyName(String nombre);

}
