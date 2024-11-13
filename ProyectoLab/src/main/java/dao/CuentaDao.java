package dao;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.Cleanup;
import modelos.Cuenta;
import modelos.CuentaArs;
import modelos.CuentaEur;
import modelos.CuentaUsd;

public interface CuentaDao extends Dao<Cuenta>{

	public void save(Cuenta cuenta);

	public Cuenta read(Long id);

	public void update(Cuenta cuenta);

	public void borrar(Cuenta cuenta);

	public Collection<Cuenta> readAll();
	
	public Collection<Cuenta> readAllbyMoneda(String moneda);

}
