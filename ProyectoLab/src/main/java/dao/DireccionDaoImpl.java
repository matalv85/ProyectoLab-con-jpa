package dao;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import modelos.Direccion;

@Repository
@Transactional
public class DireccionDaoImpl implements DireccionDao{

	@PersistenceContext
	private EntityManager em;
	
	public void save(Direccion direccion) {
		em.persist(direccion);
		
	}

	public Direccion read(Long id) {
		return em.find(Direccion.class, id);
	}

	public void update(Direccion direccion) {
		em.merge(direccion);
		
	}

	public void borrar(Direccion direccion) {
		em.remove(direccion);
		
	}

	public Collection<Direccion> readAll() {
		// TODO Auto-generated method stub
		TypedQuery<Direccion> q = em.createQuery("SELECT c FROM Cuenta c", Direccion.class);
		return q.getResultList();
	}

}
