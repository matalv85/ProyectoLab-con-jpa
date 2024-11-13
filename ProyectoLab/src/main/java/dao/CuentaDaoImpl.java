package dao;

import java.util.*;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import modelos.Cuenta;
import modelos.CuentaArs;
import modelos.CuentaEur;
import modelos.CuentaUsd;

@Repository
@Transactional
public class CuentaDaoImpl implements CuentaDao{


	@PersistenceContext
	private EntityManager em;
	
	
	public void save(Cuenta cuenta) {
		em.persist(cuenta);
		
	}

	public Cuenta read(Long id) {
		return em.find(Cuenta.class, id);
	}

	public void update(Cuenta cuenta) {
		em.merge(cuenta);
		
	}

	public void borrar(Cuenta cuenta) {
		em.remove(cuenta);
		
	}

	public Collection<Cuenta> readAll() {
		TypedQuery<Cuenta> q = em.createQuery("SELECT c FROM Cuenta c", Cuenta.class);
		return q.getResultList();
	}
	
	public List<Cuenta> readAllbyMoneda(String moneda) {
	    Class<? extends Cuenta> tipo = getTipoPorMoneda(moneda);
	    if (tipo == null) {
	        return new ArrayList<>() ;
	    }
	    TypedQuery<Cuenta> q = em.createQuery("SELECT c FROM Cuenta c WHERE TYPE(c) = :tipo", Cuenta.class);
	    q.setParameter("tipo", tipo);
	    return q.getResultList();
	}

	private Class<? extends Cuenta> getTipoPorMoneda(String moneda) {
	    switch (moneda) {
	        case "ARS":
	            return CuentaArs.class;
	            
	        case "USD":
	            return CuentaUsd.class;
	            
	        case "EUR":
	            return CuentaEur.class;
	            
	        default:
	            return null;
	            
	    }
	}
	

}