package dao;

import java.util.Collection;

import modelos.Cliente;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import modelos.Movimiento;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class MovimientoDaoImpl implements MovimientoDao {

	@PersistenceContext
	private EntityManager em;
		
	public void save(Movimiento movimiento) {
		em.persist(movimiento);
		
	}

	public Movimiento read(Long id) {
		return em.find(Movimiento.class, id);
	}

	public void update(Movimiento movimiento) {
		em.merge(movimiento);
		
	}

	public void borrar(Movimiento movimiento) {
		em.remove(movimiento);
		
	}

	public Collection<Movimiento> readAll() {
		TypedQuery<Movimiento> q = em.createQuery("SELECT c FROM Movimiento c", Movimiento.class);
		return q.getResultList();
	}

	public Collection<Movimiento> readAllById(long id) {
		TypedQuery<Movimiento> q = em.createQuery("SELECT c FROM Movimiento c WHERE c.cuenta.id = :id", Movimiento.class);
		q.setParameter("id", id);
		return q.getResultList();
	}

}