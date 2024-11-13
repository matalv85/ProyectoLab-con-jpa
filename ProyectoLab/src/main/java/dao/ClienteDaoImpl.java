package dao;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import modelos.Cliente;

@Repository
@Transactional
public class ClienteDaoImpl implements ClienteDao {
	
	@PersistenceContext
	private EntityManager em;
	
	public void save(Cliente cliente) {
		em.persist(cliente);
		
	}

	public Cliente read(Long id) {
		return em.find(Cliente.class, id);
	}

	public void update(Cliente cliente) {
		em.merge(cliente);
		
	}

	public void borrar(Cliente cliente) {
		em.remove(cliente);
		
	}

	public Collection<Cliente> readAll() {
		TypedQuery<Cliente> q = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
		return q.getResultList();
	}
	
	public Collection<Cliente> readAllbyName(String nombre) {
		TypedQuery<Cliente> q = em.createQuery("SELECT c FROM Cliente c WHERE c.nombre = :nombre", Cliente.class);
		q.setParameter("nombre", nombre);
		return q.getResultList();
	}

}